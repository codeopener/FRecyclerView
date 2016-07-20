package com.jian.frecyclerview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjian on 2016/6/22.
 */
public class HeaderFooterHolder extends FRecyclerView.FViewHolder<Object>{

    private LinearLayout mContainer;

    public HeaderFooterHolder(ViewGroup parent, Integer layoutId)
    {
        super(parent,R.layout.header_footer_container);
        mContainer = findView(R.id.f_header_footer_container);
    }

    @Override
    public void bindData(Object data, int position) {
    }

    public void addView(List<FRecyclerView.HeaderFooterItem> childs)
    {
        mContainer.removeAllViews();
        for (FRecyclerView.HeaderFooterItem item : childs)
        {
            mContainer.addView(item.v, item.index, item.params);
        }
    }

    public int getChildCount()
    {
        return  mContainer.getChildCount();
    }
}
