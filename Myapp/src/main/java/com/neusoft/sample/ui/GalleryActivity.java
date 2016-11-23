package com.neusoft.sample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.neusoft.sample.R;
import com.neusoft.sample.WApiCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * on 2016/8/31.
 * Created by WangHuanyu
 */
public class GalleryActivity extends WApiCompatActivity {
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    Spinner spinner2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        data_list = new ArrayList<String>();
        data_list.add("辽宁");
        data_list.add("上海");
        data_list.add("深圳");
        data_list.add("广东");

        arr_adapter= new ArrayAdapter<String>(this, R.layout.spinner_default_item, data_list);
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(arr_adapter);
    }

}
