package com.example.admin.myapplication;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.module.TestModule;
import com.example.admin.myapplication.presenter.TestPresenter;
import com.why.library.common.banner.Banner;
import com.why.library.rxandroid.annotation.AcceptElement;
import com.why.library.tool.WLogTool;
import com.why.library.ui.WApiCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends WApiCompatActivity<TestPresenter,TestModule> {
    @Bind(R.id.text) TextView text;
    @Bind(R.id.bt) Button bt;
    @Bind(R.id.banner2) Banner banner2;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTodo(Bundle savedInstanceState) {
//        presenter.test();
        String[] urls = getResources().getStringArray(R.array.url4);
        List list = Arrays.asList(urls);
        List<?> images = new ArrayList(list);
        banner2.setImages(images);
        banner2.isAutoPlay(false);
        banner2.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(MainActivity.this,"点击" + position,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        banner2.start();

    }

    @Override
    protected void setListeners() {
        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt:
                Intent intent = new Intent();
                intent.setClass(this,TestActivity.class);
                startActivity(intent);
                break;
        }
    }
    @AcceptElement()
    public void test(Object tag,String test){
        text.setText(test);
    }
}
