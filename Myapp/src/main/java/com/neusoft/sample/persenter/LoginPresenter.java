package com.neusoft.sample.persenter;

import com.neusoft.sample.module.ImpLoginUser;
import com.neusoft.sample.persenter.base.WBasePresenter;

/**
 * on 2016/8/22.
 * Created by WangHuanyu 外包
 */
public class LoginPresenter extends WBasePresenter<ImpLoginUser> {

    public void login(String name){
        tModule.login(name);
        tModule.setUserMsg();
    }

    public void unlogin(String name){
        tModule.unLogin(name);
    }
}
