package com.qxy.evoandroid.http;

import com.qxy.evoandroid.http.callback.ResponseCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 封装Retrofit的请求
 * @author Persenlo
 */
public class RetrofitUtil {

    //发送请求，返回对应类型
    public static <T> void enqueue(Call<T> call, ResponseCallback<T> callback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful() && callback != null) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (callback != null) {
                    callback.onFailure(t);
                }
            }
        });
    }
}
