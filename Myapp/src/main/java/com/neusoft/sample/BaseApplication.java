package com.neusoft.sample;

import android.app.Application;

/**
 * on 2016/8/25.
 * Created by WangHuanyu 外包
 */
public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        getApplicationContext();
    }
}
