package com.qxy.evoandroid;

import android.app.Application;

import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.DouYinOpenConfig;

public class EvoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //抖音开发平台的key
        DouYinOpenApiFactory.init(new DouYinOpenConfig(Constant.CLIENT_KEY));
    }
}
