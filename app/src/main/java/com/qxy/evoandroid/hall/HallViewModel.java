package com.qxy.evoandroid.hall;

import android.app.Application;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.qxy.evoandroid.Constant;
import com.qxy.evoandroid.R;
import com.qxy.evoandroid.StartActivity;
import com.qxy.evoandroid.douyinapi.TokenUtil;
import com.qxy.evoandroid.http.RetrofitManager;
import com.qxy.evoandroid.http.RetrofitUtil;
import com.qxy.evoandroid.http.callback.ResponseCallback;
import com.qxy.evoandroid.model.UserInfo;
import com.qxy.evoandroid.request.ApiService;
import com.qxy.evoandroid.userLogin.MainActivity;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * 大厅ViewModel
 * @author Persenlo
 */
public class HallViewModel extends AndroidViewModel {

    private MutableLiveData<String> userName = new MutableLiveData<>();
    private MutableLiveData<String> userIcon = new MutableLiveData<>();

    public HallViewModel(@NonNull Application application) {
        super(application);
    }


    void getUserInfo(String userToken,String userOpenId){
        //获取Retrofit
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit(Constant.DOUYIN_OPENAPI);
        ApiService apiService = retrofit.create(ApiService.class);
        Call<UserInfo> userInfo = apiService.getUserInfo(userToken,userToken,userOpenId);
        RetrofitUtil.enqueue(userInfo, new ResponseCallback<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                if (userInfo.getData().getErrorCode() == 0){
                    userName.setValue("欢迎，"+userInfo.getData().getNickname());
                    userIcon.setValue(userInfo.getData().getAvatar());
                }else {

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public MutableLiveData<String> getUserName() {
        return userName;
    }

    public void setUserName(MutableLiveData<String> userName) {
        this.userName = userName;
    }

    public MutableLiveData<String> getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(MutableLiveData<String> userIcon) {
        this.userIcon = userIcon;
    }
}
