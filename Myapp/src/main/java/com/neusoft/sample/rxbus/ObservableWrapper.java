package com.neusoft.sample.rxbus;

import rx.Observable;

/**
 *
 * @param <T>
 */
public class ObservableWrapper<T> {
    private Observable<T> observable;
    private Object tag;

    public ObservableWrapper(Object tag, Observable<T> observable) {
        this.tag = tag;
        this.observable = observable;
    }

    public Observable<T> getObservable() {
        return observable;
    }

    public void setObservable(Observable<T> observable) {
        this.observable = observable;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }
}
