package com.example.admin.myapplication.adapter;

import android.support.v7.widget.RecyclerView;

import com.why.library.common.adapter.recycler.WRecyclerViewAdapter;
import com.why.library.common.adapter.recycler.WViewHolderHelper;

/**
 * Created by admin on 2016/11/14.
 */

public class TestAdapter extends WRecyclerViewAdapter<String>{

    public TestAdapter(RecyclerView recyclerView, int itemLayoutId) {
        super(recyclerView, itemLayoutId);
    }

    @Override
    protected void fillData(WViewHolderHelper viewHolderHelper, int position, String model) {

    }
}
