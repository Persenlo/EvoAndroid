package com.qxy.evoandroid.list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.qxy.evoandroid.Constant;
import com.qxy.evoandroid.http.RetrofitManager;
import com.qxy.evoandroid.http.RetrofitUtil;
import com.qxy.evoandroid.http.callback.ResponseCallback;
import com.qxy.evoandroid.model.RankVersion;
import com.qxy.evoandroid.model.VideoRank;
import com.qxy.evoandroid.model.VideoRank.DataDTO;
import com.qxy.evoandroid.request.ApiService;
import com.qxy.evoandroid.model.RankVersion.VersionData;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ListViewModel extends AndroidViewModel {
    private final MutableLiveData<DataDTO> dataLiveData=new MutableLiveData<>();
    private final MutableLiveData<VersionData> versionLiveData=new MutableLiveData<>();

    public MutableLiveData<VersionData> getVersionLiveData() {
        return versionLiveData;
    }

    public MutableLiveData<DataDTO> getDataLiveData() {
        return dataLiveData;
    }

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public void getListData(String cToken,int type,int version) {
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit(Constant.DOUYIN_OPENAPI);
        ApiService apiService = retrofit.create(ApiService.class);
        Call<VideoRank> videoRank = apiService.getVideoRank(cToken, type, version);
        RetrofitUtil.enqueue(videoRank, new ResponseCallback<>() {
            @Override
            public void onSuccess(VideoRank videoRank) {
                System.out.println(videoRank.getData().getErrorCode()+":"+videoRank.getData().getDescription()+" type:"+type);
                if(videoRank.getData().getErrorCode().equals("0")){
                    dataLiveData.setValue(videoRank.getData());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("onFailure");
            }
        });
    }

    public void getVersion(String cToken,int type){
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit(Constant.DOUYIN_OPENAPI);
        ApiService apiService = retrofit.create(ApiService.class);
        Call<RankVersion> rankVersion=apiService.getRankVersion(cToken,type,10);
        RetrofitUtil.enqueue(rankVersion, new ResponseCallback<RankVersion>() {
            @Override
            public void onSuccess(RankVersion rankVersion) {
                System.out.println(rankVersion.getData().getErrorCode()+":"+rankVersion.getData().getDescription()+" type:"+type);
                if(rankVersion.getData().getErrorCode().equals("0")){
                    versionLiveData.setValue(rankVersion.getData());
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
