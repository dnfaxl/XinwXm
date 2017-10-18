package com.edu.nbl.xinwxm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.edu.nbl.xinwxm.R;
import com.edu.nbl.xinwxm.entity.NewsType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by ydy on 2017/10/11.
 */

public class NewsTypeAdapter extends RecyclerView.Adapter<NewsTypeAdapter.ViewHolder> {
List<NewsType> mNewsTypeList;
    Context mContext;
private OnItemClickListener mListener;
    public int CurrentPosition=0;

    public NewsTypeAdapter(List<NewsType> newsTypeList, Context context ,OnItemClickListener mListener) {
        this.mNewsTypeList = newsTypeList;
        this.mContext = context;
        this.mListener=mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newstype, parent,false);
        ViewHolder mViewHolder=new ViewHolder(mView);
        return mViewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTv.setText(mNewsTypeList.get(position).subgroup);
        holder.mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
                    mListener.setOnItemClickListener(mNewsTypeList.get(position),position);
                }
            }
        });
        if (CurrentPosition==position){
            holder.mTv.setTextColor(Color.RED);
        }else {
            holder.mTv.setTextColor(Color.BLACK);
        }

    }

public NewsType getItem(int position){
    return mNewsTypeList.get(position);
}
    @Override
    public int getItemCount() {
        return mNewsTypeList.size();
    }

    @OnClick(R.id.tv)
    public void onViewClicked() {
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView mTv;
        View mView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.mView=itemView;

        }
    }
    public interface OnItemClickListener{
        void setOnItemClickListener(NewsType mNewsType,int position);
    }
}
