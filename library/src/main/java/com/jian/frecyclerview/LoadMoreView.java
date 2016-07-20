package com.jian.frecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by wangjian on 2016/6/23.
 */
public class LoadMoreView extends LinearLayout{
    public static final int LOADSTATE_IDLE = 0;
    public static final int LOADSTATE_LOADING = 1;
    public static final int LOADSTATE_ERROR = 2;
    public static final int LOADSTATE_END = 3;

    private int mState = LOADSTATE_LOADING;

    private ProgressBar mProgressBar;

    private TextView mTv;

    private int mPageSize;

    private OnLoadMoreViewListener mListener;

    public LoadMoreView(Context context) {
        this(context, null);
    }

    public LoadMoreView(Context context, int pageSize) {
        this(context);
        this.mPageSize = pageSize;
    }

    public LoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init()
    {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.f_loadmoreview, this, true);
        mProgressBar = (ProgressBar) findViewById(R.id.pb);
        mTv = (TextView) findViewById(R.id.tv);

        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mState == LOADSTATE_ERROR)
                {
                    mState = LOADSTATE_LOADING;
                    if(mListener != null)
                    {
                        mListener.onErrorClick(v);
                    }
                }
            }
        });
    }

    public void setState(int state, int dataSize)
    {
        if(dataSize < mPageSize )
        {
            setVisibility(View.GONE);
            return;
        }
        mState = state;

        if(state == LOADSTATE_LOADING)
        {
            mProgressBar.setVisibility(View.VISIBLE);
            mTv.setText("更多数据加载中...");
        } else if(state == LOADSTATE_ERROR)
        {
            mProgressBar.setVisibility(View.GONE);
            mTv.setText("加载出错,点击重试");
        } else if(state == LOADSTATE_END)
        {
            mProgressBar.setVisibility(View.GONE);
            mTv.setText("已经到底了!");
        }
    }

    public void setOnLoadMoreViewListener(OnLoadMoreViewListener listener)
    {
        this.mListener = listener;
    }

    public interface OnLoadMoreViewListener
    {
        void onErrorClick(View v);
    }

    public int currentState()
    {
        return mState;
    }

    public boolean canLoadMore()
    {
        return mState == LOADSTATE_LOADING || mState == LOADSTATE_IDLE;
    }
}
