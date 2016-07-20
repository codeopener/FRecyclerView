package com.wj.code.recyclerviewdemo.waterfall;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jian.frecyclerview.FRecyclerView;
import com.jian.frecyclerview.LoadMoreView;
import com.jian.frecyclerview.layoutmanager.ExStaggeredGridLayoutManager;
import com.jian.frecyclerview.layoutmanager.HeaderSpanSizeLookup;
import com.wj.code.recyclerviewdemo.R;
import com.wj.code.recyclerviewdemo.TextViewHodler;

/**
 * Created by wangjian on 2016/6/24.
 */
public class WaterFallActivity extends AppCompatActivity{
    FRecyclerView recyclerView;

    private int s;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);

        recyclerView = (FRecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setOnIntemClickListener(new FRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FRecyclerView recyclerview, View v, int position, long id) {
                Toast.makeText(WaterFallActivity.this,"aaaa" + position, Toast.LENGTH_SHORT).show();
            }
        });

        ExStaggeredGridLayoutManager manager = new ExStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup(recyclerView.getAdapter(), manager.getSpanCount()));

        recyclerView.setLayoutManager(manager);

        addHeaderFooter();

        recyclerView.setOnLoadMoreListener(new FRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        s++;
                        if(s == 2)
                        {
                            recyclerView.setLoadState(LoadMoreView.LOADSTATE_ERROR);
                        } else if(s == 4)
                        {
                            recyclerView.setLoadState(LoadMoreView.LOADSTATE_END);
                        } else
                        {
                            initData(20);
                            recyclerView.dataCommit();
                        }
                    }
                },1000);
            }
        },20);

        initData(20);
        recyclerView.dataCommit();
    }

    private void initData(int size)
    {
        for (int i = 0; i < size; i++)
        {
            recyclerView.dataAdd(WaterFallHolder.class, i + "===test");
        }
    }

    private void addHeaderFooter()
    {
        Button header = new Button(this);
        header.setText("Header");

        Button header2 = new Button(this);
        header2.setText("Header2");

        Button footer = new Button(this);
        footer.setText("Footer");

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);

        recyclerView.addHeaderView(header, params);
        recyclerView.addHeaderView(header2);
        recyclerView.addFooterView(footer, params);
    }
}
