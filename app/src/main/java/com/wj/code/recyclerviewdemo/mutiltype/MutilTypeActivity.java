package com.wj.code.recyclerviewdemo.mutiltype;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jian.frecyclerview.FRecyclerView;
import com.jian.frecyclerview.LoadMoreView;
import com.wj.code.recyclerviewdemo.HorizontalHolder;
import com.wj.code.recyclerviewdemo.R;
import com.wj.code.recyclerviewdemo.TextViewHodler;
import com.wj.code.recyclerviewdemo.TextViewHodler2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjian on 2016/6/24.
 */
public class MutilTypeActivity extends AppCompatActivity{
    FRecyclerView recyclerView;

    private int s;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);

        recyclerView = (FRecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setOnIntemClickListener(new FRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FRecyclerView recyclerview, View v, int position, long id) {
                Toast.makeText(MutilTypeActivity.this,"aaaa" + position, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setColorSchemeResources(R.color.swipe_color_1, R.color.swipe_color_2, R.color.swipe_color_3, R.color.swipe_color_4);

        addHeaderFooter();

        recyclerView.setOnRefreshListener(new FRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.dataClear();
                        initData(20);
                        recyclerView.dataCommit();
                        recyclerView.setRefreshComplete();
                    }
                },2000);
            }
        });
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
        List<String> list = new ArrayList<>();
        recyclerView.dataAdd(HorizontalHolder.class, list);
        initData(20);
        recyclerView.dataCommit();
    }

    private void initData(int size)
    {

        for (int i = 0; i < size; i++)
        {
            if(i%2==0)
            {
                recyclerView.dataAdd(TextViewHodler2.class, i + "===test");
            }else
            {
            recyclerView.dataAdd(TextViewHodler.class, i + "===test");
            }

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
