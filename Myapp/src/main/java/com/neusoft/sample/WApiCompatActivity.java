package com.neusoft.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.neusoft.sample.module.base.WBaseModule;
import com.neusoft.sample.persenter.base.WBasePresenter;
import com.neusoft.sample.utils.TUtil;

/**
 * on 2016/8/23.
 * Created by WangHuanyu 外包
 */
public abstract class WApiCompatActivity<T extends WBasePresenter,T1 extends WBaseModule> extends WBaseActivity{
    public T presenter;
    private T1 baseModule;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = TUtil.getT(this,0);
        baseModule = TUtil.getT(this,1);
        if(presenter != null){
            presenter.setModule(baseModule);
        }
    }
}
