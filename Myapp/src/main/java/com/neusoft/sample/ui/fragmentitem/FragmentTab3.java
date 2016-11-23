package com.neusoft.sample.ui.fragmentitem;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neusoft.sample.R;

import butterknife.ButterKnife;

/**
 * Created by admin on 2016/9/9.
 */
public class FragmentTab3 extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmenttab1, container, false);
        ButterKnife.bind(this,view);
        return view;
    }
}
