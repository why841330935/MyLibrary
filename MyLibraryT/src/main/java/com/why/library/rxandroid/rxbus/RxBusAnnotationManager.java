package com.why.library.rxandroid.rxbus;

import android.content.Context;

import com.why.library.rxandroid.annotation.AcceptElement;
import com.why.library.rxandroid.annotation.AcceptScheduler;
import com.why.library.rxandroid.annotation.AcceptType;
import com.why.library.tool.WLogTool;
import com.why.library.utils.WTextUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @ClassName RxBusAnnotationManager
 * @author  WangHuanyu
 * @todo
 * @date 2016/11/8 11:04
 */
public class RxBusAnnotationManager {
    private static final String TAG = RxBusAnnotationManager.class.getName();
    private Object object;

    public RxBusAnnotationManager(Object object) {
        this.object = object;
    }

    private List<ObservableWrapper> registeredObservable;

    public <T> void parserObservableEventAnnotations(Method method) throws Exception {
        if (null == method || !method.isAnnotationPresent(AcceptElement.class)) {
            return;
        }
        Class[] params = method.getParameterTypes();
        // 参数必须是两个，第1个必须是Object类型的tag
        if (null == params || 2 != params.length || !Object.class.isAssignableFrom(params[0])) {
            throw new Exception("the method[" + method.getName() + "] must defined xxx(Object tag, T object)");
        }

        AcceptElement accept = method.getAnnotation(AcceptElement.class);
        AcceptType[] acceptTypes = accept.value();

        // 默认clazz参数类型
        Class<T> targetClazz = params[1];
        // 默认clazz参数类型的全类名
        String targetTag = targetClazz.getName();
        Class<T> specClazz;
        String specTag;
        int acceptTypeLength = null == acceptTypes ? 0 : acceptTypes.length;
        switch (acceptTypeLength) {
            case 0: // 如果acceptType是空，则说明具体的类型是params[1]，所以params[1]不能为Object类型
                if (Object.class.equals(targetClazz)) {
                    throw new Exception("the method[" + method.getName() + "] must defined xxx(Object tag, T object)");
                }
                registerObservable(method, targetTag, targetClazz, accept.acceptScheduler());
                break;
            case 1: // 如果只有一个，如果acceptType中tag不为空，则使用默认clazz参数类型，acceptType中指定clazz优先
                specClazz = acceptTypes[0].clazz();
                if (!Object.class.equals(specClazz)) {
                    targetClazz = specClazz;
                }
                if (Object.class.equals(targetClazz)) {
                    throw new Exception("the method[" + method.getName() + "] must defined xxx(Object tag, T object) OR clazz of @AcceptType");
                }
                targetTag = targetClazz.getName();
                // 默认tag参数类型的全类名，acceptType中指定tag优先
                specTag = acceptTypes[0].tag();
                if (!WTextUtils.isEmpty(specTag)) {
                    targetTag = specTag;
                }
                registerObservable(method, targetTag, targetClazz, accept.acceptScheduler());
                break;
            default: // 如果有多个，则params[1]必须是Object
                if (!Object.class.equals(targetClazz)) {
                    throw new Exception("the method[" + method.getName() + "] must defined xxx(Object tag, Object object)");
                }
                for (AcceptType acceptType : acceptTypes) {
                    specClazz = acceptType.clazz();
                    specTag = acceptType.tag();
                    // 默认tag参数类型的全名，acceptType中指定tag优先
                    registerObservable(method, WTextUtils.isEmpty(specTag) ? specClazz.getName() : specTag, specClazz, accept.acceptScheduler());
                }
                break;
        }


    }


    private void ensureRegisteredObservable() {
        if (null == registeredObservable) {
            registeredObservable = new ArrayList<>();
        }
    }

    private <T> void registerObservable(final Method method,final String tag, Class<T> clazz, AcceptScheduler acceptScheduler) {
        if (null == tag || null == clazz) {
            return;
        }
        ensureRegisteredObservable();
        Observable<T> observable = RxBus.get().register(tag, clazz);
        registeredObservable.add(new ObservableWrapper(tag, observable));

        Observable<T> schedulerObservable;
        switch (acceptScheduler) {
            case NEW_THREAD:
                schedulerObservable = observable.observeOn(Schedulers.newThread());
                break;
            case IO:
                schedulerObservable = observable.observeOn(Schedulers.io());
                break;
            case IMMEDIATE:
                schedulerObservable = observable.observeOn(Schedulers.immediate());
                break;
            case COMPUTATION:
                schedulerObservable = observable.observeOn(Schedulers.computation());
                break;
            case TRAMPOLINE:
                schedulerObservable = observable.observeOn(Schedulers.trampoline());
                break;
            default: // MAIN_THREAD default
                schedulerObservable = observable.observeOn(AndroidSchedulers.mainThread());
                break;
        }

//        schedulerObservable.subscribe(o -> {
//            try {
//                Log.e("event","post");
//                method.invoke(object, tag, o);
//            } catch (Exception e) {
//                Log.e(TAG, e.getMessage());
//            }
//        });
        schedulerObservable.subscribe(new Action1<T>() {
            @Override
            public void call(T t) {
                try {
                    method.invoke(object, tag, t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void clear() {
        if (!WTextUtils.isEmpty(registeredObservable)) {
            for (ObservableWrapper observableWrapper : registeredObservable) {
                RxBus.get().unregister(observableWrapper.getTag(), observableWrapper.getObservable());
            }
        }
    }

}
