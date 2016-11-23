package com.why.library.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.why.library.R;

/**
 * @ClassName WLoadingDialog
 * @author  WangHuanyu
 * @todo 默认dialog
 * @date 2016/11/8 16:05
 */
public class WLoadingDialog extends AlertDialog
{
    protected WLoadingDialog(Context context)
    {
        super(context);
    }

    protected WLoadingDialog(Context context, int theme)
    {
        super(context, theme);
    }

    protected WLoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener)
    {
        super(context, cancelable, cancelListener);
    }

    public static WLoadingDialog show(Context context)
    {
        return show(context, false);
    }

    public static WLoadingDialog show(Context context, boolean cancelable)
    {
        return show(context, cancelable, null);
    }

    public static WLoadingDialog show(Context context, boolean cancelable, OnCancelListener cancelListener) {
        WLoadingDialog dialog = new WLoadingDialog(context, cancelable, cancelListener);
        dialog.setOnKeyListener(new OnKeyListener()
        {
            @Override public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
            {
                if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    dialog.cancel();
                }
                return false;
            }
        });
        dialog.show();
        return dialog;
    }

    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.w_dialog_loading);
        WindowManager.LayoutParams lp;
        lp = getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.dimAmount = 0; // 去背景遮盖
        lp.alpha = 1.0f;
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        getWindow().setAttributes(lp);
    }
}
