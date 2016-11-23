package com.example.admin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.myapplication.module.TestModule;
import com.example.admin.myapplication.presenter.TestPresenter;
import com.why.library.rxandroid.annotation.AcceptElement;
import com.why.library.tool.WLogTool;
import com.why.library.ui.WApiCompatActivity;

import butterknife.Bind;

public class MainActivity extends WApiCompatActivity<TestPresenter,TestModule> {
    @Bind(R.id.text) TextView text;
    @Bind(R.id.bt) Button bt;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTodo(Bundle savedInstanceState) {
//        presenter.test();
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
