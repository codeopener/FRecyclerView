package com.wj.code.recyclerviewdemo.waterfall;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jian.frecyclerview.FRecyclerView;
import com.wj.code.recyclerviewdemo.R;

/**
 * Created by wangjian on 2016/6/24.
 */
public class WaterFallHolder extends FRecyclerView.FViewHolder<String> {
    private TextView tv;

    public WaterFallHolder(ViewGroup parent, Integer layoutId)
    {
        super(parent, R.layout.item);
        tv = findView(R.id.tv);
    }

    @Override
    public void bindData(String data, int position) {
        ViewGroup.LayoutParams params = tv.getLayoutParams();
        params.height = position % 3 * 100;
        tv.setLayoutParams(params);
        tv.setText(data);
    }
}
