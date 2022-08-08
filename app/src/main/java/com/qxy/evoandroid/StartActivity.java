package com.qxy.evoandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.qxy.evoandroid.douyinapi.TokenUtil;
import com.qxy.evoandroid.hall.HallActivity;
import com.qxy.evoandroid.http.RetrofitManager;
import com.qxy.evoandroid.http.RetrofitUtil;
import com.qxy.evoandroid.http.callback.ResponseCallback;
import com.qxy.evoandroid.model.UserInfo;
import com.qxy.evoandroid.request.ApiService;
import com.qxy.evoandroid.userLogin.MainActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * 负责检测Token是否可用和投放广告
 * @author Persenlo
 */
public class StartActivity extends BaseActivity {

    TokenUtil tokenUtil;
    String userToken;
    String userOpenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        init();
    }

    private void init(){
        //获取令牌
        tokenUtil = new TokenUtil(this);
        userToken = tokenUtil.getToken();
        userOpenId = tokenUtil.getOpenId();

        //测试获取用户信息
        Retrofit checkRetrofit = RetrofitManager.getInstance().getRetrofit(Constant.DOUYIN_OPENAPI);
        ApiService apiService = checkRetrofit.create(ApiService.class);
        Call<UserInfo> userInfo = apiService.getUserInfo(userToken,userToken,userOpenId);
        RetrofitUtil.enqueue(userInfo, new ResponseCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                if (userInfo.getData().getErrorCode() == 0){
                    //已登录
                    Intent intent = new Intent(StartActivity.this, HallActivity.class);
                    startActivity(intent);
                }else {
                    //未登录
                    Toast.makeText(StartActivity.this,R.string.login_login_first,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}