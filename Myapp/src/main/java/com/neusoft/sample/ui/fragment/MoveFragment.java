package com.neusoft.sample.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neusoft.sample.R;

/**
 * on 2016/8/30.
 * Created by WangHuanyu 外包
 */
public class MoveFragment extends Fragment implements View.OnClickListener {

    private String curFlag;
    public static MoveFragment newInstance(String flag){
        MoveFragment fragment = new MoveFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Flag", flag);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            curFlag = args.getString("Flag");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_move, container,false);
        initViews(view);
        return view;
    }

    public void initViews(View view){
        TextView tv=(TextView)view.findViewById(R.id.tv);
        tv.setText(curFlag);
    }
    @Override
    public void onClick(View v) {

    }

}
