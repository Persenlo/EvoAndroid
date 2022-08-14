package com.qxy.evoandroid.douyinapi;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;

import com.qxy.evoandroid.StartActivity;
import com.qxy.evoandroid.userLogin.MainActivity;


/**
 * 用于检查Token是否有效和获取Token
 * @author Persenlo
 */
public class TokenUtil {

    Context context;
    SharedPreferences sp;
    String token;
    String openId;
    String cToken;

    public TokenUtil(Context context) {
        this.context = context;
    }

    //获取accessToken
    public String getToken(){
        sp = context.getSharedPreferences("userToken.xml",0);
        token = sp.getString("access_token","empty");
        if (token.equals("empty")){
            //判断是否在登陆界面请求
            if(getActivityByContext(context) instanceof MainActivity){

            }else if (getActivityByContext(context) instanceof StartActivity){

            }else {
                //跳转到登录界面
                Intent intent = new Intent(context,MainActivity.class);
                context.startActivity(intent);
            }
        }
        return token;
    }

    //获取openId
    public String getOpenId(){
        sp = context.getSharedPreferences("userToken.xml",0);
        openId = sp.getString("open_id","empty");
        if (openId.equals("empty")){
            //判断是否在登陆界面请求
            if(getActivityByContext(context) instanceof MainActivity){

            }else {
                //跳转到登录界面
                Intent intent = new Intent(context,MainActivity.class);
                context.startActivity(intent);
            }
        }
        return openId;
    }



    //获取clientToken
    public String getClientToken(){
        sp = context.getSharedPreferences("userToken.xml",0);
        cToken = sp.getString("client_token","empty");

        return cToken;
    }




    //获取Activity
    public static Activity getActivityByContext(Context context){
        while(context instanceof ContextWrapper){
            if(context instanceof Activity){
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

}
