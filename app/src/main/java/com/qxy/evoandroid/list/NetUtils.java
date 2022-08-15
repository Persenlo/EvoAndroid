package com.qxy.evoandroid.list;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtils {
    //判断是否联网
    public static boolean isNet(Context context){
        if(context!=null){
            //获取连接管理器
            ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取网络状态
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if(networkInfo!=null) return networkInfo.isAvailable();
        }
        return false;
    }
}
