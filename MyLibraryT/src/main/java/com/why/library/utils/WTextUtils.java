package com.why.library.utils;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;

/**
 * @ClassName WTextUtils
 * @author  WangHuanyu
 * @todo
 * @date 2016/11/8 11:04
 */
public class WTextUtils {
    public static boolean isEmpty(Collection collection) {
        return null == collection || collection.isEmpty();
    }
    public static boolean isEmpty(CharSequence charSequence) {
        return null == charSequence || charSequence.length() <= 0;
    }

    /**
     * 获取super类型
     * @param o
     * @param i
     * @param <T>
     * @return
     */
    public static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param className
     * @return
     */
    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
