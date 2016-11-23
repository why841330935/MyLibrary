package com.why.library.ui;

import com.why.library.entity.WEventBase;

/**
 * @ClassName IWBaseActivity
 * @author  WangHuanyu
 * @todo
 * @date 2016/11/8 16:12
 */
public interface IWBaseActivity{
    void showBaseDialog();//显示dialog
    void dismissDialog();//关闭dialog
    void beforeSetContentView();//setContentView之前操作，空方法需要可以自己实现
    void afterSetContentView();//setContentView之后操作，空方法需要可以自己实现
    public void onEvent(WEventBase wEventBase);//eventBus使用(回调在主线程) WEventBase为传递类

}
