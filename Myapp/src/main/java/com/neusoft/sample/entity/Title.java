package com.neusoft.sample.entity;

/**
 * Created by admin on 2016/9/9.
 */
public class Title {
    private String name;
    private boolean isShow;

    public Title(String name) {
        this.name = name;
    }

    public Title(String name, boolean isShow) {
        this.name = name;
        this.isShow = isShow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
