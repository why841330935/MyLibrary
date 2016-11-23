package com.why.library.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.why.library.rxandroid.annotation.AcceptElement;
import com.why.library.rxandroid.rxbus.RxBusAnnotationManager;
import com.why.library.tool.WEventCache;
import com.why.library.ui.dialog.WLoadingDialog;

import java.lang.reflect.Method;

import butterknife.ButterKnife;

/**
 * @ClassName WBaseFragment
 * @author  WangHuanyu
 * @todo
 * @date 2016/11/14 10:59
 */
public abstract class WBaseFragment extends Fragment implements IWBaseActivity,View.OnClickListener{
    protected View mRootView;
    private WLoadingDialog loadingDialog;//dialog
    protected RxBusAnnotationManager rxBusAnnotationManager;//rxjava管理者
    public boolean isTrueEventBus = true;//默认注册eventBus方便熟悉eventBus小伙伴使用

    /**
     * onCreate中操作
     */
    protected abstract void initTodo(Bundle savedInstanceState);
    /**
     * 布局文件
     * @return
     */
    protected abstract int getLayoutResource();

    @Nullable
    @Override public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                                             Bundle savedInstanceState) {
        if (mRootView == null)
        {
            mRootView = inflater.inflate(getLayoutResource(), container, false);
        }
        ButterKnife.bind(this, mRootView);
        ViewGroup parentView = (ViewGroup) mRootView.getParent();
        if (parentView != null)
        {
            parentView.removeView(mRootView);
        }
        registerRxBus();
        if(isTrueEventBus){
            WEventCache.eventBus.register(this);
        }
        initTodo(savedInstanceState);
        return mRootView;
    }


    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        unregisterRxBus();
        if(isTrueEventBus){
            WEventCache.eventBus.unregister(this);
        }
    }
    @Override
    public void dismissDialog() {
        if (null != loadingDialog && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showBaseDialog() {
        boolean dialogStatus = false;
        if (null != loadingDialog) {
            dialogStatus = loadingDialog.isShowing();
        }
        if (!dialogStatus) {
            loadingDialog = WLoadingDialog.show(getContext(), false, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {

                }
            });
        }
    }
    /**
     * 基于反射method的注释注册rxjava
     */
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

    /**
     * 解绑
     */
    protected void unregisterRxBus() {
        if (null != rxBusAnnotationManager) {
            rxBusAnnotationManager.clear();
        }
    }
}
