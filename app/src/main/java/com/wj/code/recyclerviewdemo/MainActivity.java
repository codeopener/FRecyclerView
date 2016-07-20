package com.wj.code.recyclerviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jian.frecyclerview.FRecyclerView;
import com.jian.frecyclerview.LoadMoreView;
import com.jian.frecyclerview.layoutmanager.ExStaggeredGridLayoutManager;
import com.jian.frecyclerview.layoutmanager.HeaderSpanSizeLookup;
import com.wj.code.recyclerviewdemo.gridview.GridViewActivity;
import com.wj.code.recyclerviewdemo.listview.ListViewActivity;
import com.wj.code.recyclerviewdemo.mutiltype.MutilTypeActivity;
import com.wj.code.recyclerviewdemo.waterfall.WaterFallActivity;

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);
    }

    public void go2List(View v)
    {
        startActivity(ListViewActivity.class);
    }

    public void go2Grid(View v)
    {
        startActivity(GridViewActivity.class);
    }

    public void go2Mutil(View v)
    {
        startActivity(MutilTypeActivity.class);
    }

    public void go2WaterFall(View v)
    {
        startActivity(WaterFallActivity.class);
    }

    public  void startActivity(Class<? extends Activity> cls)
    {
        startActivity(new Intent(this, cls));
    }
}
