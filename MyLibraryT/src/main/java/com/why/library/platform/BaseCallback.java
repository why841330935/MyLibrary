package com.why.library.platform;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.why.library.tool.WLogTool;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author ymy
 * @// TODO: 2016/9/6  http回调封装
 */
public abstract class BaseCallback<T> implements Callback{

    private static final int SYSTEM_REQUEST_ERROR = 0;
    private static final int CRASH_WHEN_ON_DATA_SUCCESS = 1;
    protected static final int DATA_RESULT_NULL = 2;
    private static final int SERVER_RESPONSE_ERROR = 3;
    //Settings -- > Editor-File and Code Templates -->Files-Class --> Includes-File Header ；


    @Override
    public void onResponse(Call call, Response response) {
        int code = response.raw().code();
        WLogTool.e("code--------", code + "");
        if (code == 200) {
            /**
             * 这里我只实现了成功和失败的回调，还可以根据接口返回的状态信息实现相应的回调
             * */
            WLogTool.e(response.body().toString());

            onFinish();
            call.cancel();
        } else {
            onFail(SYSTEM_REQUEST_ERROR, "服务器网络异常");
            onFinish();
        }
    }

    @Override
    public void onFailure(Call call, Throwable t) {
        Log.e("OnFailure ++", t.getMessage() == null ? "" : t.getMessage());
        call.cancel();
        onFail(SYSTEM_REQUEST_ERROR, "网络异常!");
        onFinish();
    }

    /**
     * 请求成功的回调
     */
    protected abstract void onSuccess(T s);

    /**
     * 请求完的回调，可以在里面停止刷新控件，可以不实现
     */
    protected void onFinish() {
    }

    /**
     * 请求失败的回调
     */
    protected abstract void onFail(int status, String msg);


}
