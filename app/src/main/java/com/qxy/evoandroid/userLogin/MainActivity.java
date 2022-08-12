package com.qxy.evoandroid.userLogin;


import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bytedance.sdk.open.aweme.authorize.model.Authorization;
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi;
import com.qxy.evoandroid.BaseActivity;
import com.qxy.evoandroid.R;
import com.qxy.evoandroid.databinding.ActivityMainBinding;
import com.qxy.evoandroid.utils.SingleClickListener;

public class MainActivity extends BaseActivity {

    LoginViewModel loginViewModel;
    ActivityMainBinding binding;


    //设置所需权限
    DouYinOpenApi douYinOpenApi;
    String scopes = "trial.whitelist," +
            "video.data," +//视频权限
            "video.list," +
            "user_info," +//用户信息权限
            "following.list," +
            "fans.list," +
            "fans.check," +
            "renew_refresh_token," +
            "discovery.ent";//抖音电影榜

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }



    private void initView() {
        loginViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setLoginViewModel(loginViewModel);
        binding.setLifecycleOwner(this);
    }

    private void initListener() {
        //登录按钮
        binding.btLoginLogin.setOnClickListener(new SingleClickListener() {
            @Override
            protected void onSignalClick(View v) {
                login();
            }
        });
        //排行榜按钮
        binding.btLoginRank.setOnClickListener(new SingleClickListener() {
            @Override
            protected void onSignalClick(View v) {

            }
        });
    }

    //调出授权页面进行授权
    public void login(){
        douYinOpenApi = DouYinOpenApiFactory.create(this);
        Authorization.Request request = new Authorization.Request();
        request.scope = scopes;                                // 用户授权时必选权限
        request.optionalScope0 = "";                           // 用户授权时可选权限（默认选择）
        request.state = "ww";                                  // 用于保持请求和回调的状态，授权请求后原样带回给第三方。
        douYinOpenApi.authorize(request);                       // 优先使用抖音app进行授权，如果抖音app因版本或者其他原因无法授权，则使用wap页授权
        finish();
    }
}