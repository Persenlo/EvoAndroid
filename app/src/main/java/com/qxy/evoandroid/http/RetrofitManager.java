package com.qxy.evoandroid.http;


import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Retrofit工厂
 * 负责管理各Url下的Retrofit
 * 可获取不同返回值类型的Retrofit
 * @author Persenlo
 */

public class RetrofitManager {

    private static RetrofitManager instance;

    private static Map<String, Retrofit> retrofitMap = new HashMap<>();

    public RetrofitManager() {
    }


    //获取实例
    public static RetrofitManager getInstance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                if (instance == null) {
                    instance = new RetrofitManager();
                }
            }
        }
        return instance;
    }


    //获取对应url的Retrofit对象
    public Retrofit getRetrofit(String url){
        //判断url是否为空
        if (TextUtils.isEmpty(url)){
            throw new NullPointerException("url is null");
        }
        //获取Retrofit对象并返回
        Retrofit retrofit = retrofitMap.get(url);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            retrofitMap.put(url, retrofit);
        }
        return retrofit;
    }

    //获取对应的Retrofit对象（String类型，无gson）
    public Retrofit getStringRetrofit(String url){
        //判断url是否为空
        if (TextUtils.isEmpty(url)){
            throw new NullPointerException("url is null");
        }
        //获取Retrofit对象并返回
        Retrofit retrofit = retrofitMap.get(url);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            retrofitMap.put(url, retrofit);
        }
        return retrofit;
    }
}
