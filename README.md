# Rxjava

### 定义

RxJava 有四个基本概念：Observable (可观察者，即被观察者)、 Observer (观察者)、 subscribe (订阅)、事件。Observable 和 Observer 通过 subscribe() 方法实现订阅关系，从而 Observable 可以在需要的时候发出事件来通知 Observer。

### 回调方法

与传统观察者模式不同， RxJava 的事件回调方法除了普通事件 onNext() （相当于 onClick() / onEvent()）之外，还定义了两个特殊的事件：onCompleted() 和 onError()。  

onCompleted(): 事件队列完结。RxJava 不仅把每个事件单独处理，还会把它们看做一个队列。RxJava 规定，当不会再有新的 onNext() 发出时，需要触发 onCompleted() 方法作为标志。  

onError(): 事件队列异常。在事件处理过程中出异常时，onError() 会被触发，同时队列自动终止，不允许再有事件发出。 

在一个正确运行的事件序列中, onCompleted() 和 onError() 有且只有一个，并且是事件序列中的最后一个。需要注意的是，onCompleted() 和 onError() 二者也是互斥的，即在队列中调用了其中一个，就不应该再调用另一个。

### 基本实现

##### 1、创建 Observer

`Observer 即观察者，它决定事件触发的时候将有怎样的行为。 RxJava 中的 Observer 接口的实现方式：`
    
    //创建一个下游  观察者Observer
    Observer<Integer> observer = new Observer<Integer>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.d(TAG, "subscribe");
        }

        @Override
        public void onNext(Integer value) {
            Log.d(TAG, "" + value);
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "error");
        }

        @Override
        public void onComplete() {
            Log.d(TAG, "complete");
        }
    };
    
`除了 Observer 接口之外，RxJava 还内置了一个实现了 Observer 的抽象类：Subscriber。 Subscriber 对 Observer 接口进行了一些扩展，但他们的基本使用方式是完全一样的：`  
    
    Subscriber<String> subscriber = new Subscriber<String>() {
    @Override
    public void onNext(String s) {
        Log.d(tag, "Item: " + s);
    }

    @Override
    public void onCompleted() {
        Log.d(tag, "Completed!");
    }

    @Override
    public void onError(Throwable e) {
        Log.d(tag, "Error!");
    }
};

##### 2、创建 Observable

`Observable 即被观察者，它决定什么时候触发事件以及触发怎样的事件。 RxJava 使用 create() 方法来创建一个 Observable ，并为它定义事件触发规则：`  

    //创建一个上游  被观察者 Observable：
    Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
        @Override
        public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
            emitter.onComplete();
        }
    });
    
##### 3、Subscribe (订阅)

`创建了 Observable 和 Observer 之后，再用 subscribe() 方法将它们联结起来，整条链子就可以工作了。代码形式很简单：`  

  //建立连接  
  observable.subscribe(observer);
  
##### 4、简洁表达式

`(一)由于是建造者模式，上面的三个步骤 可以整合成更简洁的表达语句。`
  
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onError(new Throwable("错误传递"));
                emitter.onComplete();
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onNext(String s) {}

            @Override
            public void onError(Throwable e) {
                showLog(e.getMessage());
            }

            @Override
            public void onComplete() {}
        });
    }
 
`(二) 观察者封装按需的回调`

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onError(new Throwable("错误传递"));
                emitter.onComplete();
            }
        }).subscribe(new Action1() {
            @Override
            void call(Throwable o) {
                showLog(o.getMessage());
            }
        });
    }
    
----------------------------------------------------------------

   abstract class Action1 implements Observer {
    abstract void call(Throwable o);
    @Override
    public void onSubscribe(Disposable d) {}

    @Override
    public void onNext(Object o) {}

    @Override
    public void onError(Throwable e) {
        call(e);
    }

    @Override
    public void onComplete() {}
}

_以外发现抽象方法还可以像接口一样回调_
    
### 使用场景
    
    
    

