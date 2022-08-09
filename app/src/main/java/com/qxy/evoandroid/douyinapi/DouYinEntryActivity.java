package com.qxy.evoandroid.douyinapi;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bytedance.sdk.open.aweme.CommonConstants;
import com.bytedance.sdk.open.aweme.authorize.model.Authorization;
import com.bytedance.sdk.open.aweme.common.handler.IApiEventHandler;
import com.bytedance.sdk.open.aweme.common.model.BaseReq;
import com.bytedance.sdk.open.aweme.common.model.BaseResp;
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi;

import com.qxy.evoandroid.BaseActivity;
import com.qxy.evoandroid.Constant;
import com.qxy.evoandroid.R;
import com.qxy.evoandroid.cookieStore.CookieJarImpl;
import com.qxy.evoandroid.cookieStore.PersistentCookieStore;
import com.qxy.evoandroid.request.ApiService;

import org.json.JSONException;
import org.json.JSONObject;

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
    private String refresh_token;
    private String open_id;

    private Retrofit retrofit;
    private ApiService apiService;

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
                authCode = response.authCode;
                getToken();
            }else {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
                finish();
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
        apiService = retrofit.create(ApiService.class);
    }


    //获取access_token
    private void getToken() {
        Call<ResponseBody> call = apiService.getToken(Constant.CLIENT_SECRET,authCode,Constant.GRANT_TYPE,Constant.CLIENT_KEY);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody body = response.body();
                try {
                    if (body != null) {
                        getData(body.string());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void getData(String body) {
        try {
            JSONObject jsonObject = new JSONObject(body);
            String data = jsonObject.getString("data");
            JSONObject dataObj = new JSONObject(data);
            String errCode = dataObj.getString("error_code");
            if (errCode.equals("0")){
                //获取access_token成功，将token通过SP保存
                accessToken = dataObj.getString("access_token");
                refresh_token = dataObj.getString("refresh_token");
                open_id = dataObj.getString("open_id");
                SharedPreferences sp = this.getSharedPreferences("userToken.xml",0);
                SharedPreferences.Editor spEdit = sp.edit();
                spEdit.putString("access_token",accessToken);
                spEdit.putString("refresh_token",refresh_token);
                spEdit.putString("open_id",open_id);
                spEdit.commit();
                //发送消息
                Message msg = new Message();
                msg.what = Constant.TOKEN_GET_COMPLETE;
                handler.sendMessage(msg);
            }else {
                //请求失败，清除过期Token
                SharedPreferences sp = this.getSharedPreferences("userToken.xml",0);
                SharedPreferences.Editor spEdit = sp.edit();
                spEdit.clear();
                //发送消息
                Message msg = new Message();
                msg.what = Constant.TOKEN_GET_FAIL;
                handler.sendMessage(msg);
            }
        } catch (JSONException e) {
            Log.e("DouYinEntityActivity","err:"+e);;
        }
    }


    //消息处理
    private final Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Constant.TOKEN_GET_COMPLETE:
                    //请求成功，返回
                    Toast.makeText(DouYinEntryActivity.this,R.string.login_token_success,Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case Constant.TOKEN_GET_FAIL:
                    //请求失败，后返回
                    Toast.makeText(DouYinEntryActivity.this,R.string.login_token_fail,Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
    };
}