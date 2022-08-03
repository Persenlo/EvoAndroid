package com.qxy.evoandroid.douyinapi;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bytedance.sdk.open.aweme.CommonConstants;
import com.bytedance.sdk.open.aweme.authorize.model.Authorization;
import com.bytedance.sdk.open.aweme.common.handler.IApiEventHandler;
import com.bytedance.sdk.open.aweme.common.model.BaseReq;
import com.bytedance.sdk.open.aweme.common.model.BaseResp;
import com.bytedance.sdk.open.aweme.share.Share;
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi;
import com.qxy.evoandroid.APIService;
import com.qxy.evoandroid.BaseActivity;
import com.qxy.evoandroid.Constant;
import com.qxy.evoandroid.MainActivity;
import com.qxy.evoandroid.R;
import com.qxy.evoandroid.cookieStore.CookieJarImpl;
import com.qxy.evoandroid.cookieStore.PersistentCookieStore;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 授权登录回调类
 * @author Persenlo
 */

public class DouYinEntryActivity extends BaseActivity implements IApiEventHandler {

    DouYinOpenApi douYinOpenApi;
    private String authCode;
    private String accessToken;

    private Retrofit retrofit;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dou_yin_entry);

        douYinOpenApi = DouYinOpenApiFactory.create(this);
        douYinOpenApi.handleIntent(getIntent(), this);



    }

    @Override
    public void onReq(BaseReq baseReq) { }

    @Override
    public void onResp(BaseResp resp) {
        initRetrofit();
        //成功获取权限
        if (resp.getType() == CommonConstants.ModeType.SEND_AUTH_RESPONSE) {
            Authorization.Response response = (Authorization.Response) resp;
            if (resp.isSuccess()) {
                Toast.makeText(this, "授权成功",Toast.LENGTH_SHORT).show();
                authCode = response.authCode;
                getToken();
            }
        }
    }

    @Override
    public void onErrorIntent(Intent intent) {
        //网络错误
        Toast.makeText(this, "请检查网络连接", Toast.LENGTH_SHORT).show();
    }

    private void initRetrofit() {
        //初始化Retrofit
        OkHttpClient client = new OkHttpClient.Builder().cookieJar(new CookieJarImpl(new PersistentCookieStore(this))).build();
        retrofit = new Retrofit.Builder().
                baseUrl(Constant.DOUYIN_OPENAPI).
                client(client).
                build();
        apiService = retrofit.create(APIService.class);
    }


    //获取access_token
    private void getToken() {
        Call<ResponseBody> call = apiService.getToken(Constant.CLIENT_SECRET,authCode,Constant.GRANT_TYPE,Constant.CLIENT_KEY);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody body = response.body();
                try {
                    Log.e("test","msg:"+body.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}