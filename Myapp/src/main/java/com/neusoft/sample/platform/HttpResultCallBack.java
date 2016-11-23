package com.neusoft.sample.platform;

import com.neusoft.sample.rxbus.RxBus;

/**
 * on 2016/8/30.
 * Created by WangHuanyu 外包
 */
public abstract class HttpResultCallBack<T> extends OkHttpClientManager.ResultCallback {
    private String tag;

    public HttpResultCallBack(String tag) {
        this.tag = tag;
    }

    public HttpResultCallBack() {
    }

    public abstract void sendResponse(T result);

    @Override
    public void onResponse(Object response) {
        this.sendResponse((T) response);
        if (tag != null) {
            RxBus.get().post(tag, response);
        } else {
            RxBus.get().post(response);
        }
    }
}
