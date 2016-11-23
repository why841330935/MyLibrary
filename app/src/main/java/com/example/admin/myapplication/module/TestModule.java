package com.example.admin.myapplication.module;

import com.example.admin.myapplication.http.IApiService;
import com.why.library.platform.BaseCallback;
import com.why.library.platform.RetrofitUtils;
import com.why.library.rxandroid.rxbus.RxBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @ClassName TestModule
 * @author  WangHuanyu
 * @todo
 * @date 2016/11/9 13:45
 */
public class TestModule implements ITestModule{

    @Override
    public void login() {
        RetrofitUtils.getInstance().create(IApiService.class).getTeachList()
                .enqueue(new BaseCallback<Object>() {

                    @Override
                    protected void onSuccess(Object s) {

                    }

                    @Override
                    protected void onFail(int status, String msg) {

                    }
                });
    }
}
