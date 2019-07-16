package com.example.waz.Base;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;


import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class BaseModel {
    public static <T, R> void observer(RxAppCompatActivity activity,
                                       Observable<T> observable, Function<T, ObservableSource<R>> function, final BaseCallBack<R> callBack) {
        observable.flatMap(function)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(activity.<R>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<R>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(R r) {
                        callBack.onSuccese(r);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static <T, R> void observer(RxFragment fragment
            , Observable<T> observable, Function<T, ObservableSource<R>> function, final BaseCallBack<R> callBack) {
        observable.flatMap(function)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(fragment.<R>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new Observer<R>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(R r) {
                        callBack.onSuccese(r);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFailed(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}
