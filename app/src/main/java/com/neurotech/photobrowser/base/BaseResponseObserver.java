package com.neurotech.photobrowser.base;

import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.exception.StorageException;
import com.lzy.okgo.model.Response;
import com.neurotech.photobrowser.exception.ApiException;
import com.neurotech.photobrowser.utils.L;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by NeuroAndroid on 2017/10/16.
 */

public abstract class BaseResponseObserver<T> implements Observer<Response<T>> {
    private static final String TAG = "BaseResponseObserver";

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull Response<T> response) {
        boolean successful = response.isSuccessful();
        if (successful) {
            T body = response.body();
            if (body != null) {
                onHandleResponse(response);
            } else {
                onError(new ApiException("数据为空"));
            }
        } else {
            onError(new ApiException("网络请求失败"));
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        String errorMessage = e.getMessage();
        L.e(TAG + " onError --> " + errorMessage);
        if (e instanceof ApiException) {
            onHandleError(errorMessage);
        } else if (e instanceof UnknownHostException || e instanceof ConnectException) {
            onHandleError("网络连接失败，请连接网络");
        } else if (e instanceof SocketTimeoutException) {
            onHandleError("网络请求超时");
        } else if (e instanceof HttpException) {
            onHandleError("网络请求出现错误");
        } else if (e instanceof SecurityException || e instanceof StorageException) {
            onHandleError("sd卡不存在或者没有权限");
        } else {
            onHandleError("未知错误");
        }
        e.printStackTrace();
    }

    @Override
    public void onComplete() {
        L.e(TAG + " network request complete");
    }

    protected abstract void onHandleResponse(Response<T> response);

    protected abstract void onHandleError(String tip);
}
