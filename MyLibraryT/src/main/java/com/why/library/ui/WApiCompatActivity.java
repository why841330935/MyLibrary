package com.why.library.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.why.library.mvp.module.WBaseModule;
import com.why.library.mvp.persenter.WBasePresenter;
import com.why.library.utils.WTextUtils;

/**
 * @ClassName WApiCompatActivity
 * @author  WangHuanyu
 * @todo 兼容activity基类 T为对应presenter  T1为对应module
 * @date 2016/11/9 10:14
 */
public abstract class WApiCompatActivity<T extends WBasePresenter,T1 extends WBaseModule> extends WBaseActivity{
    public T presenter;
    private T1 baseModule;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = WTextUtils.getT(this,0);
        baseModule = WTextUtils.getT(this,1);
        if(presenter != null){
            presenter.settModule(baseModule);
        }
        super.onCreate(savedInstanceState);
    }
}
