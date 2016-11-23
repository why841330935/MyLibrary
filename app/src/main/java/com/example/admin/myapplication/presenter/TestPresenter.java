package com.example.admin.myapplication.presenter;

import com.example.admin.myapplication.module.TestModule;
import com.why.library.mvp.persenter.WBasePresenter;

/**
 * @ClassName TestPresenter
 * @author  WangHuanyu
 * @todo
 * @date 2016/11/9 13:40
 */
public class TestPresenter extends WBasePresenter<TestModule>{

    public void test(){
        tModule.login();
    }
}
