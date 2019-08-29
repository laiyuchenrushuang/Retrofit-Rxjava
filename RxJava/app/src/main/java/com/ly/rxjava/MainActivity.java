package com.ly.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * RxJava核心是异步，哪些场景使用，怎么使用，然后如何封装，这里使用的是rxjava2.0
 */
public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //观察者
        Observer<List<String>> o =new Observer<List<String>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<String> s) {
                showLog(s.toString());
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        };

        //被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("1");
                emitter.onNext("2");
                emitter.onNext("3");
                emitter.onComplete();
            }
        });

        //订阅
//        observable.subscribe(o);


        //折合代码

        List<String> list = new ArrayList<String>();
        for(int i =0;i<10;i++){
            list.add("Hello"+i);
        }

        Observable<List<String>> observable1 = Observable.just(list);
        observable.subscribe(new Action1() {
            @Override
            void call(Object o) {
                showLog(o.toString());
            }
        });

    }

    private void showLog(String s) {
        Log.d("lylog","result = "+s);
    }


}
