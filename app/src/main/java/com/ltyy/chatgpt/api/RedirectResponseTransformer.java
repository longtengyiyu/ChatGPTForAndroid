package com.ltyy.chatgpt.api;

import com.google.gson.Gson;
import com.ltyy.chatgpt.app.AppConstants;
import com.ltyy.chatgpt.utils.JsonUtils;
import com.ltyy.chatgpt.utils.LogUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RedirectResponseTransformer<T> implements ObservableTransformer<T, T> {

    private static final String TAG = RedirectResponseTransformer.class.getSimpleName();

    @NonNull
    @Override
    public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
        return upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .lift(observer -> new Observer<T>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        observer.onSubscribe(d);
                    }

                    @Override
                    public void onNext(@NonNull T httpResult) {
                        LogUtils.d(TAG, JsonUtils.get().toJson(httpResult));
                        if (httpResult != null) {
                            observer.onNext(httpResult);
                            observer.onComplete();
                        }else{
                            observer.onError(new Throwable(AppConstants.MSG_EMPTY));
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
