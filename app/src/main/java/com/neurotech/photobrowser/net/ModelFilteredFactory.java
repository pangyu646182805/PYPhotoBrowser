package com.neurotech.photobrowser.net;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by NeuroAndroid on 2017/6/14.
 */

public class ModelFilteredFactory<T> {
    private final ObservableTransformer TRANSFORMER = new SimpleTransformer();

    @SuppressWarnings("unchecked")
    public Observable<T> compose(Observable<T> observable) {
        return observable.compose(TRANSFORMER);
    }

    private class SimpleTransformer implements ObservableTransformer<T, T> {
        @Override
        public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            // .timeout(3, TimeUnit.SECONDS)  // 重连间隔时间
            // .retry(3);  // 重连次数
        }
    }
}
