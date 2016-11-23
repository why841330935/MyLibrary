package com.why.library.common.tablayout.listener;

import android.support.annotation.DrawableRes;

public interface WCustomTabEntity {
    String getTabTitle();

    @DrawableRes
    int getTabSelectedIcon();

    @DrawableRes
    int getTabUnselectedIcon();
}