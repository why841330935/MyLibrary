package com.why.library.entity;

import java.io.Serializable;

/**
 * @ClassName WEventBase
 * @author  WangHuanyu
 * @todo eventBus传递基类
 * @date 2016/11/8 17:00
 */
public class WEventBase implements Serializable{
    public String tag;//用于区分tag
    public Object obj;//接收值

    public WEventBase(String tag, Object obj) {
        this.tag = tag;
        this.obj = obj;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
