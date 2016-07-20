package com.wj.code.recyclerviewdemo;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jian.frecyclerview.FRecyclerView;

/**
 * Created by wangjian on 2016/5/27.
 */
public class TextViewHodler2 extends FRecyclerView.FViewHolder<String>
{
    private TextView tv;

    public TextViewHodler2(ViewGroup parent, Integer layoutId)
    {
        super(parent, R.layout.item);
        tv = findView(R.id.tv);
        itemView.setBackgroundColor(0xffcccccc);
    }

    @Override
    public void bindData(String data, int position) {
        tv.setText(data+"aaaaaaaaaaaaaaaaaaaaaaaaaaa");

    }
}
