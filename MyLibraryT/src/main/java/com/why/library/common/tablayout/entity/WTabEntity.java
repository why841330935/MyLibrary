package com.why.library.common.tablayout.entity;


import com.why.library.common.tablayout.listener.WCustomTabEntity;

/**
 * @ClassName WTabEntity
 * @author  WangHuanyu
 * @todo title实体
 * @date 2016/9/19 13:41
 */
public class WTabEntity implements WCustomTabEntity {
    public String title;
    public int selectedIcon;
    public int unSelectedIcon;

    public WTabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    public WTabEntity(String title) {
        this.title = title;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
