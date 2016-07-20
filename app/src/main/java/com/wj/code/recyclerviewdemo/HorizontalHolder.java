package com.wj.code.recyclerviewdemo;

import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;

import com.jian.frecyclerview.FRecyclerView;

import java.util.List;

/**
 * Created by wangjian on 2016/6/22.
 */
public class HorizontalHolder extends FRecyclerView.FViewHolder<List<String>>{
    private FRecyclerView recyclerView;
    public HorizontalHolder(ViewGroup parent, Integer layoutId) {
        super(parent, R.layout.hori);
        recyclerView = findView(R.id.recyclerview);
        LinearLayoutManager ma = new LinearLayoutManager(parent.getContext());
        ma.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(ma);
    }

    @Override
    public void bindData(List<String> data, int position) {
        for (int i = 0; i < 100; i++)
        {
            if(i%2==0)
            {
                recyclerView.dataAdd(TextViewHodler2.class, i + "===HorizontalHolder");
            }else
            {
                recyclerView.dataAdd(TextViewHodler.class, i + "===HorizontalHolder");
            }

        }
    }
}
