package com.edu.nbl.xinwxm.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.edu.nbl.xinwxm.R;
import com.edu.nbl.xinwxm.adapter.NewsListAdapter;
import com.edu.nbl.xinwxm.adapter.NewsTypeAdapter;
import com.edu.nbl.xinwxm.entity.News;
import com.edu.nbl.xinwxm.entity.NewsType;
import com.edu.nbl.xinwxm.network.EasyShopApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ydy on 2017/8/29.
 */
public class NewsFragment extends Fragment {
    @BindView(R.id.rv)
    RecyclerView mRv;
    RecyclerView mRvlist;
    SwipeRefreshLayout msrl;
    Unbinder unbinder;
    List<NewsType> mNewsTypeList=new ArrayList<NewsType>();
    List<News> mNewsList = new ArrayList<News>();
    List<News> mNewsList2= new ArrayList<News>();
    Context mContext;
    private NewsTypeAdapter mNewsTypeAdapter;
    private NewsListAdapter mNewsListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_news, container, false);
        mRvlist= (RecyclerView) mView.findViewById(R.id.mRvlist);
        msrl= (SwipeRefreshLayout) mView.findViewById(R.id.srl);
        unbinder = ButterKnife.bind(this,mView);
        msrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mNewsListAdapter.notifyDataSetChanged();
                msrl.setRefreshing(false);
            }
        });
        mContext= getActivity();
        mNewsTypeAdapter = new NewsTypeAdapter(mNewsTypeList, getActivity(), new NewsTypeAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(NewsType mNewsType, int position) {
                getData(mNewsType.subid,1,0);
                mNewsTypeAdapter.CurrentPosition=position;
                mNewsTypeAdapter.notifyDataSetChanged();
            }
        });

        mRv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL,false));
        mRv.setAdapter(mNewsTypeAdapter);
        initData();
        mNewsListAdapter=new NewsListAdapter(getContext(),mNewsList);
        mRvlist.setLayoutManager(new LinearLayoutManager(mContext,LinearLayout.VERTICAL,false));
        return mView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new LoadMoreForRecyclerView(mRvlist, new LoadMoreForRecyclerView.LoadMoreListener() {
            @Override
            public void loadMore() {
                //
                getData(mNewsTypeAdapter.CurrentPosition,1,mNewsList.get(mRvlist.getAdapter().getItemCount()-1).nid);
            }
        },getActivity());

    }
    private void initData() {
        //1.创建OkHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody mFormBody = new FormBody.Builder().add("ver", "2").add("imei", "2").build();
        //2.创建Request对象
        Request mRequest = new Request.Builder()
                .url(EasyShopApi.APP_URL + "news_sort")
                .post(mFormBody)
                .build();
        //3.创建Call对象
        Call mCall = mOkHttpClient.newCall(mRequest);
        //4.把Call对象加入请求队列
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e("8848",result);
                //创建集合，存放NewsType对象
                //解析json数据,Gson,JsonObject
                try {
                    //创建JSONObject对象
                    JSONObject mJSONObject = new JSONObject(result);
                    if (mJSONObject.getInt("status") == 0) {
                        JSONArray mJSONArray = mJSONObject.getJSONArray("data");
                        for (int i = 0; i < mJSONArray.length(); i++) {
                            mJSONObject = mJSONArray.getJSONObject(i);
                            JSONArray mArray = mJSONObject.getJSONArray("subgrp");
                            for (int j = 0; j < mArray.length(); j++) {
                                JSONObject mObject = mArray.getJSONObject(j);
                                String mSubgroup = mObject.getString("subgroup");
                                int mSubid = mObject.getInt("subid");
                                //封装实体类
                                NewsType mNewsType = new NewsType(mSubgroup, mSubid);
                                //将实体类添加到集合
                                mNewsTypeList.add(mNewsType);
                                //创建新闻列表fragment，存放在集合中
                                Log.e("onResponse: ", mNewsTypeList.toString());
                            }
                        }
                        //更新ui
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRv.setAdapter(mNewsTypeAdapter);
                                getData(mNewsTypeAdapter.getItem(0).subid,1,0);
                                //TODO:请求具体新闻

                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void getData(int subid,int dir,int nid) {

        OkHttpClient mOkHttpClient = new OkHttpClient();

        Request mRequest = new Request.Builder()
                .url(EasyShopApi.APP_URL + "news_list?ver=2&subid=" + subid +"&dir=" + dir + "&nid=" + nid + "&stamp=null&cnt=2")
                .build();

        Call mCall = mOkHttpClient.newCall(mRequest);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                mNewsListAdapter.clear();
                //解析数据
                try {
                    JSONObject mJSONObject = new JSONObject(result);
                    if (mJSONObject.getInt("status") == 0) {
                        JSONArray mJSONArray = mJSONObject.getJSONArray("data");
                        for (int i = 0; i < mJSONArray.length(); i++) {
                            mJSONObject = mJSONArray.getJSONObject(i);
                            String msummary = mJSONObject.getString("summary");
                            String mIcon = mJSONObject.getString("icon");
                            String mStamp = mJSONObject.getString("stamp");
                            String mtitle = mJSONObject.getString("title");
                            int mNid = mJSONObject.getInt("nid");
                            String mlink = mJSONObject.getString("link");
                            int mtype = mJSONObject.getInt("type");
                            //创建news对象
                            News mNews = new News(msummary, mIcon, mStamp, mtitle, mlink, mNid, mtype);
                            //保存到集合
                            mNewsList.add(mNews);
                            //更新ui
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mRvlist.setAdapter(mNewsListAdapter);
                                    mNewsListAdapter.notifyDataSetChanged();

                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.rv)
    public void onViewClicked() {
    }
}