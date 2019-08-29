package com.ly.rxjava;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by ly on 2019/8/28 16:16
 * <p>
 * Copyright is owned by chengdu haicheng technology
 * co., LTD. The code is only for learning and sharing.
 * It is forbidden to make profits by spreading the code.
 */
abstract class Action1 implements Observer {
abstract void call(Object e);
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Object o) {
        call(o);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
