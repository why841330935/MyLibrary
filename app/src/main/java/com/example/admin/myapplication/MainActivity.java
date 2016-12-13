package com.example.admin.myapplication;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.fragment.TestFragment;
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

    public List<Fragment> fragments = new ArrayList<Fragment>();
    public LinearLayout ll_tab1, ll_tab2, ll_tab3, ll_tab4, ll_tab5;
    public List<LinearLayout> listLayout = new ArrayList<LinearLayout>();// 点击区域

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
        test();
    }
    private void test(){
        fragments.add(new TestFragment());
        fragments.add(new TestFragment());
        fragments.add(new TestFragment());
        fragments.add(new TestFragment());
        fragments.add(new TestFragment());

        ll_tab1 = (LinearLayout) findViewById(R.id.ll_tab1);
        ll_tab2 = (LinearLayout) findViewById(R.id.ll_tab2);
        ll_tab3 = (LinearLayout) findViewById(R.id.ll_tab3);
        ll_tab4 = (LinearLayout) findViewById(R.id.ll_tab4);
        ll_tab5 = (LinearLayout) findViewById(R.id.ll_tab5);
        listLayout.add(ll_tab1);
        listLayout.add(ll_tab2);
        listLayout.add(ll_tab3);
        listLayout.add(ll_tab4);
        listLayout.add(ll_tab5);
        FragmentTabAdapter tabAdapter = new FragmentTabAdapter(this, fragments, R.id.realtabcontent, listLayout);
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
