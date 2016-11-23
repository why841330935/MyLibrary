package com.why.library.mvp.persenter;

/**
 * @ClassName WBasePresenter
 * @author  WangHuanyu
 * @todo
 * @date 2016/11/8 11:06
 */
public abstract class WBasePresenter<T> {
    protected T tModule;

    public T gettModule() {
        return tModule;
    }

    public void settModule(T tModule) {
        this.tModule = tModule;
    }
}
