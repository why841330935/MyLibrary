package com.why.library.common.shswiperefresh.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * @ClassName DipUtils
 * @author  WangHuanyu
 * @todo
 * @date 2016/11/9 13:38
 */
public abstract class DipUtils {

    public static float dipToPx(Context context, float value) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, metrics);
    }
}
