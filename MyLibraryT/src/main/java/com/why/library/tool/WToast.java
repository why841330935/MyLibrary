package com.why.library.tool;

import android.content.Context;
import android.widget.Toast;

/**
 * @ClassName WToast
 * @author  WangHuanyu
 * @todo 覆盖Toast
 * @date 2016/11/8 16:33
 */
public class WToast {
    public static Toast toast;

    /**
     *
     * @Title: showTextToast
     * @Description: TODO 消除Toast显示重复
     * @param @param context
     * @param @param msg 设定文件
     * @return void 返回类型
     * @throws
     */
    public static void showTextToast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
