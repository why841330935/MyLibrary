package com.why.library;

import android.app.Application;
<<<<<<< HEAD
import android.support.multidex.MultiDex;
=======
>>>>>>> origin/master

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
<<<<<<< HEAD
        MultiDex.install(this);
=======
>>>>>>> origin/master
    }

    private void init() {
        WUnCeHandler catchExcep = new WUnCeHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);
    }
}
