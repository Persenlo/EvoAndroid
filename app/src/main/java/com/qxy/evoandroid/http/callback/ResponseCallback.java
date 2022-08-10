package com.qxy.evoandroid.http.callback;

public interface ResponseCallback<T> {

    void onSuccess(T t);

    void onFailure(Throwable t);
}
