package com.example.admin.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.myapplication.adapter.TestAdapter;
import com.example.admin.myapplication.module.TestModule;
import com.example.admin.myapplication.presenter.TestPresenter;
import com.why.library.common.adapter.recycler.WOnRVItemClickListener;
import com.why.library.common.shswiperefresh.core.SHSwipeRefreshLayout;
import com.why.library.tool.WToast;
import com.why.library.ui.WApiCompatActivity;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * @author WangHuanyu
 * @ClassName TestActivity
 * @todo
 * @date 2016/11/15 9:06
 */
public class TestActivity extends WApiCompatActivity<TestPresenter, TestModule> {
    @Bind(R.id.recycler) RecyclerView recycler;
    @Bind(R.id.refresh) SHSwipeRefreshLayout refresh;
    TestAdapter wRecyclerViewAdapter;
    ArrayList<String> arrayList = new ArrayList<String>() {
        {
            add("111");
            add("111");
            add("111");
            add("111");
            add("111");
            add("111");
            add("111");
            add("111");
            add("111");
            add("111");
            add("111");
            add("111");
            add("111");
            add("111");
            add("111");
            add("111");
            add("111");
        }
    };

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_test;
    }

    @Override
    protected void initTodo(Bundle savedInstanceState) {
        wRecyclerViewAdapter = new TestAdapter(recycler, R.layout.activity_test_item);
        recycler.setAdapter(wRecyclerViewAdapter);
        wRecyclerViewAdapter.setData(null);
        refresh.setOnRefreshListener(new SHSwipeRefreshLayout.SHSOnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wRecyclerViewAdapter.setData(arrayList);
                        refresh.finishRefresh();
                    }
                }, 2000);
            }

            @Override
            public void onLoading() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh.finishLoadmore();
                    }
                }, 2000);
            }

            @Override
            public void onRefreshPulStateChange(float percent, int state) {

            }

            @Override
            public void onLoadmorePullStateChange(float percent, int state) {

            }
        });
    }

    @Override
    protected void setListeners() {
        wRecyclerViewAdapter.setOnRVItemClickListener(new WOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                WToast.showTextToast(TestActivity.this,"点击" + position);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

}
