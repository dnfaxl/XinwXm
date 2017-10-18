package com.edu.nbl.xinwxm.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.edu.nbl.xinwxm.R;
import com.edu.nbl.xinwxm.activity.MessageEvent;
import com.edu.nbl.xinwxm.adapter.NewsListAdapter;
import com.edu.nbl.xinwxm.biz.NewsDBUtils;
import com.edu.nbl.xinwxm.entity.News;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Collection extends Fragment {
    @BindView(R.id.rc)
    RecyclerView mRc;
    Unbinder unbinder;
    List<News> mList;
    private NewsListAdapter mNewsListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View mView = inflater.inflate(R.layout.fragment_collection, container, false);
        unbinder = ButterKnife.bind(this, mView);
        //设置布局管理器
        mRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        mList = new ArrayList<>();
        mList = NewsDBUtils.query(getActivity());
        mNewsListAdapter = new NewsListAdapter(getActivity(), mList);
        mRc.setAdapter(mNewsListAdapter);
        //更新适配器
        mNewsListAdapter.notifyDataSetChanged();
        EventBus.getDefault().register(this);
        return mView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        mList.clear();
        mList = NewsDBUtils.query(getActivity());
        //创建适配器
        mNewsListAdapter = new NewsListAdapter(getActivity(), mList);
        mRc.setAdapter(mNewsListAdapter);
        //更新适配器
        mNewsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
