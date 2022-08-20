package com.qxy.evoandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.qxy.evoandroid.douyinapi.TokenUtil;
import com.qxy.evoandroid.hall.HallActivity;
import com.qxy.evoandroid.http.RetrofitManager;
import com.qxy.evoandroid.http.RetrofitUtil;
import com.qxy.evoandroid.http.callback.ResponseCallback;
import com.qxy.evoandroid.model.UserInfo;
import com.qxy.evoandroid.model.VideoRank;
import com.qxy.evoandroid.request.ApiService;
import com.qxy.evoandroid.userLogin.MainActivity;
import com.qxy.evoandroid.utils.DialogP;

import org.json.JSONException;
import org.json.JSONObject;

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
    String cToken;

    SharedPreferences sp;
    SharedPreferences.Editor spEdit;

    Retrofit checkRetrofit;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        setDarkStatusBar();

        checkUserPermission();
    }

    private void checkUserPermission() {

        SharedPreferences checkSP = getSharedPreferences("setting",0);
        SharedPreferences.Editor checkSPEdit = checkSP.edit();

        boolean isAllow = checkSP.getBoolean("allow_permission",false);

        if(!isAllow){
            //向用户申请弹窗
            DialogP dialogP = new DialogP(this,DialogP.DIALOG_P_NORMAL);
            dialogP.setMessage("使用此应用程序需要联网权限和存储权限")
                    .setPosBtnText("同意")
                    .setNegBtnText("拒绝")
                    .setTitle("用户须知")
                    .setCanCancel(false)
                    .setOnButtonClickListener(new DialogP.onButtonClickListener() {
                        @Override
                        public void onPositiveButtonClick() {
                            checkSPEdit.putBoolean("allow_permission",true);
                            checkSPEdit.commit();
                            dialogP.dismiss();
                            init();
                        }

                        @Override
                        public void onNegativeButtonClick() {
                            checkSPEdit.putBoolean("allow_permission",false);
                            checkSPEdit.commit();
                            dialogP.dismiss();
                            finish();
                        }
                    }).show();
        }else {
            //用户同意后开始初始化
            init();
        }

    }

    private void init(){
        sp = this.getSharedPreferences("userToken.xml",0);
        //获取令牌
        tokenUtil = new TokenUtil(this);
        userToken = tokenUtil.getToken();
        userOpenId = tokenUtil.getOpenId();
        cToken = tokenUtil.getClientToken();

        //初始化Retrofit
        checkRetrofit = RetrofitManager.getInstance().getRetrofit(Constant.DOUYIN_OPENAPI);
        apiService = checkRetrofit.create(ApiService.class);

        //测试ClientToken
        if (cToken.equals("empty")){
            getClientTokenFromReq();
        }else {
            Call<VideoRank> videoRank = apiService.getVideoRank(cToken,Constant.TYPE_MOVIE,0);
            RetrofitUtil.enqueue(videoRank, new ResponseCallback<VideoRank>() {
                @Override
                public void onSuccess(VideoRank videoRank) {
                    if (videoRank.getData().getErrorCode().equals("0")){
                        //token有效，进行下一项检测
                        Message message = new Message();
                        message.what = Constant.TOKEN_GET_COMPLETE;
                        handler.sendMessage(message);
                    }else {
                        //token无效，尝试获取
                        getClientTokenFromReq();
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }



    }

    //测试获取用户信息
    private void checkUserInfo(){
        Call<UserInfo> userInfo = apiService.getUserInfo(userToken,userToken,userOpenId);
        RetrofitUtil.enqueue(userInfo, new ResponseCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                if (userInfo.getData().getErrorCode() == 0){
                    //已登录
                    Intent intent = new Intent(StartActivity.this, HallActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    //未登录
                    Toast.makeText(StartActivity.this,R.string.login_login_first,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    //通过发送请求获取Token
    private void getClientTokenFromReq(){
        //获取Retrofit
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit(Constant.DOUYIN_OPENAPI);
        ApiService apiService = retrofit.create(ApiService.class);
        //请求ClientToken
        Call<ResponseBody> call = apiService.getClientToken(Constant.CLIENT_KEY,Constant.CLIENT_SECRET,Constant.CLIENT_TYPE);
        RetrofitUtil.enqueue(call, new ResponseCallback<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody body) {
                //请求成功，开始解析
                try {
                    getCTokenFromJson(body.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
    //解析JSON
    private void getCTokenFromJson(String body) {
        try {
            JSONObject jsonObject = new JSONObject(body);
            JSONObject data = jsonObject.getJSONObject("data");
            cToken = data.getString("access_token");
            //保存ClientToken
            spEdit = sp.edit();
            spEdit.putString("client_token",cToken);
            spEdit.commit();
            //进行下一项检测
            Message message = new Message();
            message.what = Constant.TOKEN_GET_COMPLETE;
            handler.sendMessage(message);

        } catch (JSONException e) {
            Log.e("TokenUtil","JsonErr:"+e);
        }
    }



    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Constant.TOKEN_GET_COMPLETE:
                    checkUserInfo();
                    break;
                case Constant.TOKEN_GET_FAIL:
                    break;
            }
        }
    };
}