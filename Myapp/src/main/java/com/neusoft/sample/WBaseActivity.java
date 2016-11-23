package com.neusoft.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.neusoft.sample.annotation.AcceptElement;
import com.neusoft.sample.rxbus.RxBusAnnotationManager;
import com.zhy.autolayout.AutoLayoutActivity;

import java.lang.reflect.Method;

/**
 * on 2016/8/23.
 * Created by WangHuanyu 外包
 */
public class WBaseActivity extends AutoLayoutActivity{
    protected RxBusAnnotationManager rxBusAnnotationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerRxBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterRxBus();
    }

    protected void registerRxBus() {
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(AcceptElement.class)) {
                try {
                    if(rxBusAnnotationManager == null){
                        rxBusAnnotationManager = new RxBusAnnotationManager(this);
                    }
                    rxBusAnnotationManager.parserObservableEventAnnotations(method);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void unregisterRxBus() {
        if (null != rxBusAnnotationManager) {
            rxBusAnnotationManager.clear();
        }
    }
}
