package com.neusoft.sample.module;

import android.util.Log;

import com.neusoft.sample.platform.HttpResultCallBack;
import com.neusoft.sample.platform.OkHttpClientManager;
import com.squareup.okhttp.Request;

/**
 * on 2016/8/22.
 * Created by WangHuanyu 外包
 */
public class ImpLoginUser implements LoginUser,LoginOp {
    @Override
    public void unLogin(String name) {
        Log.e(this.getClass().getName(), "unLogin =" + name);
    }

    @Override
    public void login(String name) {
//        List<Test> list = new ArrayList<>();
        Log.e("ImpLoginUser", "login =" + name);
//        list.add(test);
        OkHttpClientManager.getInstance().getAsyn("http://221.180.144.122/app/custom/checkversion.do", new HttpResultCallBack<String>("RX") {

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void sendResponse(String result) {
                Log.e("onResponse",result);
            }
        });
    }

    @Override
    public void setUserMsg() {
        Log.e("ImpLoginUser","setUserMsg");
    }
}
