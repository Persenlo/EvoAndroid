package com.qxy.evoandroid;

import android.app.Application;
import android.util.Log;

import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.DouYinOpenConfig;

public class EvoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //抖音开发平台的key
        String clientKey = "aw5666jyfx5cx2ki";
        DouYinOpenApiFactory.init(new DouYinOpenConfig(clientKey));
    }
}
