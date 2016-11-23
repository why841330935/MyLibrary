package com.neusoft.sample.utils;

import java.util.Collection;

/**
 * on 2016/8/23.
 * Created by WangHuanyu 外包
 */
public class WTextUtils {
    public static boolean isEmpty(Collection collection) {
        return null == collection || collection.isEmpty();
    }
    public static boolean isEmpty(CharSequence charSequence) {
        return null == charSequence || charSequence.length() <= 0;
    }
}
