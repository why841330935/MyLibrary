package com.neusoft.sample.persenter.base;

/**
 * on 2016/8/23.
 * Created by WangHuanyu 外包
 */
public abstract class WBasePresenter<T> {
    public T tModule;

    public void setModule(T module) {
        this.tModule = module;
    }

    public void onDestory() {

    }
}
