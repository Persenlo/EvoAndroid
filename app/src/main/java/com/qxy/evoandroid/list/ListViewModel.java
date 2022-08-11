package com.qxy.evoandroid.list;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.qxy.evoandroid.Constant;
import com.qxy.evoandroid.R;
import com.qxy.evoandroid.StartActivity;
import com.qxy.evoandroid.douyinapi.TokenUtil;
import com.qxy.evoandroid.hall.HallActivity;
import com.qxy.evoandroid.http.RetrofitManager;
import com.qxy.evoandroid.http.RetrofitUtil;
import com.qxy.evoandroid.http.callback.ResponseCallback;
import com.qxy.evoandroid.model.UserInfo;
import com.qxy.evoandroid.model.VideoRank;
import com.qxy.evoandroid.model.VideoRank.DataDTO.ListDTO;
import com.qxy.evoandroid.request.ApiService;
import com.qxy.evoandroid.userLogin.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class ListViewModel extends AndroidViewModel {
    private final MutableLiveData<List<ListDTO>> listLiveData=new MutableLiveData<>();
    private Retrofit retrofit;
    private ApiService apiService;
    private String cToken;
    private SharedPreferences sp;

    public MutableLiveData<List<ListDTO>> getLiveData() {
        return listLiveData;
    }

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public void getListData(String cToken,int type) {
        sp = getApplication().getSharedPreferences("userToken", 0);
        retrofit = RetrofitManager.getInstance().getRetrofit(Constant.DOUYIN_OPENAPI);
        apiService = retrofit.create(ApiService.class);
        this.cToken=cToken;
        Call<VideoRank> videoRank = apiService.getVideoRank(cToken, type, 18);
        RetrofitUtil.enqueue(videoRank, new ResponseCallback<>() {
            @Override
            public void onSuccess(VideoRank videoRank) {
                System.out.println(videoRank.getData().getDescription());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
