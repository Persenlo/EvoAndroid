package com.qxy.evoandroid.personalInfoActivity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.qxy.evoandroid.Constant;
import com.qxy.evoandroid.http.RetrofitManager;
import com.qxy.evoandroid.http.RetrofitUtil;
import com.qxy.evoandroid.http.callback.ResponseCallback;
import com.qxy.evoandroid.model.UserInfo;
import com.qxy.evoandroid.request.ApiService;

import retrofit2.Call;
import retrofit2.Retrofit;

public class PIViewModel extends AndroidViewModel {

    private MutableLiveData<String> userName = new MutableLiveData<>();
    private MutableLiveData<String> userIcon = new MutableLiveData<>();
    private MutableLiveData<String> userLocate = new MutableLiveData<>();
    private MutableLiveData<String> userDesc = new MutableLiveData<>();
    private MutableLiveData<String> usergender = new MutableLiveData<>();


    public PIViewModel(@NonNull Application application) {
        super(application);
    }

    void getUserInfo(String userToken,String userOpenId){
        //获取Retrofit
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit(Constant.DOUYIN_OPENAPI);
        ApiService apiService = retrofit.create(ApiService.class);
        Call<UserInfo> userInfo = apiService.getUserInfo(userToken,userToken,userOpenId);
        RetrofitUtil.enqueue(userInfo, new ResponseCallback<>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                if (userInfo.getData().getErrorCode() == 0) {
                    //将信息绘制
                    userName.setValue(userInfo.getData().getNickname());
                    userIcon.setValue(userInfo.getData().getAvatarLarger());
                    userLocate.setValue(userInfo.getData().getCountry()+"-"+userInfo.getData().getCity());
                    userDesc.setValue(userInfo.getData().getDescription());
                    if(userInfo.getData().getGender()==0) usergender.setValue("男");
                    else usergender.setValue("女");
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

    public MutableLiveData<String> getUserLocate() {
        return userLocate;
    }

    public MutableLiveData<String> getUserDesc() {
        return userDesc;
    }

    public MutableLiveData<String> getUsergender() {
        return usergender;
    }
}
