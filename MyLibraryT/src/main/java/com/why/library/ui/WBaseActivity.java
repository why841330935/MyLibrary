package com.why.library.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.why.library.R;
import com.why.library.entity.WEventBase;
import com.why.library.rxandroid.annotation.AcceptElement;
import com.why.library.rxandroid.rxbus.RxBusAnnotationManager;
import com.why.library.tool.WEventCache;
import com.why.library.ui.dialog.WLoadingDialog;
import com.zhy.autolayout.AutoLayoutActivity;

import java.lang.reflect.Method;

import butterknife.ButterKnife;

/**
 * @ClassName WBaseActivity
 * @author  WangHuanyu
 * @todo 
 * @date 2016/11/8 10:55
 */
public abstract class WBaseActivity extends AutoLayoutActivity implements IWBaseActivity,View.OnClickListener{
    protected RxBusAnnotationManager rxBusAnnotationManager;//rxjava管理者
    private SystemBarTintManager mTintManager;//沉浸式状态
    private WLoadingDialog loadingDialog;//dialog
    public View contextView;//布局
    /**
     * 开关
     */
    public boolean isInmmersiveMode = true;//4.4以上沉浸式开关默认开启   对应Activity根布局需要添加次属性android:fitsSystemWindows="true"
    public boolean isTrueEventBus = true;//默认注册eventBus方便熟悉eventBus小伙伴使用
    public boolean RequestedOrientation = true;//是否禁止横屏

    /**
     * 布局文件
     * @return
     */
    protected abstract int getLayoutResource();
    /**
     * onCreate中操作
     */
    protected abstract void initTodo(Bundle savedInstanceState);

    /**
     * 监听设置（若使用ButterKnife请忽略）
     */
    protected abstract void setListeners();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(RequestedOrientation){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//禁横屏
        }
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        if(getLayoutResource() != 0){
            contextView = LayoutInflater.from(this).inflate(getLayoutResource(),null);
            setContentView(contextView);
        }
        if (isInmmersiveMode && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        afterSetContentView();
        initSystemBarTint(contextView);//4.4兼容
        setLopStatBar(this, R.color.f_transparent_1a);//5.0+兼容设置statusBar 颜色
        ButterKnife.bind(this);//注入初始化
        registerRxBus();
        if(isTrueEventBus){
            WEventCache.eventBus.register(this);
        }
        initTodo(savedInstanceState);
        setListeners();
    }

    @Override
    public void beforeSetContentView() {

    }

    @Override
    public void afterSetContentView() {

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
            loadingDialog = WLoadingDialog.show(this, false, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {

                }
            });
        }
    }

    @Override
    public void onEvent(WEventBase wEventBase) {

    }

    @TargetApi(19)
    private void initSystemBarTint(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // create our manager instance after the content view is set
            mTintManager = new SystemBarTintManager(this);
            // enable status bar tint
            mTintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint
            mTintManager.setNavigationBarTintEnabled(true);
            mTintManager.setStatusBarAlpha(0.5f);
//            SystemBarTintManager.SystemBarConfig config = mTintManager.getConfig();
//            view.setPadding(0,config.getStatusBarHeight(),0,0);
//            this.getWindow().getDecorView().findViewById(android.R.id.content);
//            view.setPadding(0, config.getStatusBarHeight(), config.getPixelInsetRight(), config.getPixelInsetBottom());

        }
    }
    @TargetApi(21)
    public void setLopStatBar(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN //Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住。
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,color));
//            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        unregisterRxBus();
        if(isTrueEventBus){
            WEventCache.eventBus.unregister(this);
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
