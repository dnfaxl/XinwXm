package com.edu.nbl.xinwxm.fragment;

/**
 * Created by ydy on 2017/8/31.
 */

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/8/31.
 */

public class LoadMoreForRecyclerView {
    private int state;
    private LinearLayoutManager mLayoutManager;
    private int mLastVisibleItemPosition;
    private int offsetY;
    private float moveY;
    private float oldY;
    private Context mContext;
    public LoadMoreForRecyclerView(RecyclerView recyclerView, LoadMoreListener loadMoreListener, Context context) {
       this.mContext=context;
        width(recyclerView,loadMoreListener);
    }
    private void width(final RecyclerView recyclerView, LoadMoreListener loadMoreListener) {
       mLoadMoreListener=loadMoreListener;
        mLayoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                state = newState;
                mLastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                offsetY = dy;
            }
        });
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        moveY = event.getY()-oldY;
                        oldY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (state==RecyclerView.SCROLL_STATE_SETTLING||state==RecyclerView.SCROLL_STATE_DRAGGING&&recyclerView.getAdapter().getItemCount()-1==mLastVisibleItemPosition){
                            if (mLoadMoreListener!=null){
                                if (offsetY>0){
                                    mLoadMoreListener.loadMore();
                                    Toast.makeText(mContext,"加载更多",Toast.LENGTH_SHORT).show();
                                }
                                if (offsetY==0){
                                    Toast.makeText(mContext,"加载成功",Toast.LENGTH_SHORT).show();
                                    if (moveY<0){
                                        if (mLoadMoreListener!=null){
                                            mLoadMoreListener.loadMore();
                                            Toast.makeText(mContext,"没有新数据",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        }
                        break;
                }
                return false;
            }
        });


    }

    public LoadMoreListener mLoadMoreListener;
    public interface LoadMoreListener{
        void loadMore();
    }
}