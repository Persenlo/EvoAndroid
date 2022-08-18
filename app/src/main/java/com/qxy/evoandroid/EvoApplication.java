package com.qxy.evoandroid;

import android.app.Application;
import android.util.Log;

import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.DouYinOpenConfig;

import xyz.doikki.videoplayer.ijk.IjkPlayerFactory;
import xyz.doikki.videoplayer.player.VideoViewConfig;
import xyz.doikki.videoplayer.player.VideoViewManager;

public class EvoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //抖音开发平台的key
        String clientKey = Constant.CLIENT_KEY;
        DouYinOpenApiFactory.init(new DouYinOpenConfig(clientKey));

        VideoViewManager.setConfig(VideoViewConfig.newBuilder()
                //使用使用IjkPlayer解码
                .setPlayerFactory(IjkPlayerFactory.create())
                .build());
    }
}
