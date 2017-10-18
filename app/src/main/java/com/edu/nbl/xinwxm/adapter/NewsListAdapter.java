package com.edu.nbl.xinwxm.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.nbl.xinwxm.R;
import com.edu.nbl.xinwxm.activity.NewsActivity;
import com.edu.nbl.xinwxm.biz.LoadImage;
import com.edu.nbl.xinwxm.entity.News;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by ydy on 2017/10/11.
 */
public class NewsListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<News> mList;
    private static final int TextView = 0;
    private static final int ImageView = 1;

    public NewsListAdapter(Context context, List<News> list) {
        mContext = context;
        mList = list;
    }

    public void add(List<News> data) {
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public int getItemViewType(int position) {
        if (position % 3 == 0) {
            return ImageView;
        } else {
            return TextView;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ImageView) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news_iv, parent, false);
            return new ImageViewHolder(mView);
        } else {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news_tv, parent, false);
            return new TextViewHolder(mView);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final News mNews = mList.get(position);
        if (holder instanceof TextViewHolder) {
            LoadImage.LoadImage(mContext, mList.get(position).icon, ((TextViewHolder) holder).mMIv);
            ((TextViewHolder) holder).mMTitle.setText(mList.get(position).title);
            ((TextViewHolder) holder).mMSummary.setText(mList.get(position).summary);
            ((TextViewHolder) holder).mMStamp.setText(mList.get(position).stamp);
        } else if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).mTv.setText(mList.get(position).title);
            LoadImage.LoadImage(mContext, mList.get(position).icon, ((ImageViewHolder) holder).mIv1);
            LoadImage.LoadImage(mContext, mList.get(position).icon, ((ImageViewHolder) holder).mIv2);
            LoadImage.LoadImage(mContext, mList.get(position).icon, ((ImageViewHolder) holder).mIv3);
        }
        //
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转新闻界面
                Intent mIntent = new Intent(mContext, NewsActivity.class);
                Bundle mBundle=new Bundle();
                mBundle.putSerializable("NewsInfo",mNews);
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void clear(){


        mList.clear();
    }
    static class ImageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.mTv)
        TextView mTv;
        @BindView(R.id.news_iv_iv1)
        ImageView mIv1;
        @BindView(R.id.news_iv_iv2)
        ImageView mIv2;
        @BindView(R.id.news_iv_iv3)
        ImageView mIv3;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class TextViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.mIv)
        ImageView mMIv;
        @BindView(R.id.mTitle)
        TextView mMTitle;
        @BindView(R.id.mSummary)
        TextView mMSummary;
        @BindView(R.id.mStamp)
        TextView mMStamp;

        public TextViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public ItemLongClicListener mItemLongClicListener;
    public void setItemLongClicListener(ItemLongClicListener itemLongClicListener) {
        mItemLongClicListener = itemLongClicListener;
    }
    public interface ItemLongClicListener{
        void itemLongClic(int position);
    }
}

