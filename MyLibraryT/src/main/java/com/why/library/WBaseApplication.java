package com.why.library;

import android.app.Application;

import com.why.library.helper.WUnCeHandler;

/**
 * @ClassName WBaseApplication
 * @author  WangHuanyu
 * @todo
 * @date 2016/11/8 16:25
 */
public class WBaseApplication extends Application{
    private static WBaseApplication instance = new WBaseApplication();

    public static WBaseApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        init();
    }

    private void init() {
        WUnCeHandler catchExcep = new WUnCeHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);
    }
}
