package com.qxy.evoandroid.list;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.qxy.evoandroid.Constant;
import com.qxy.evoandroid.http.RetrofitManager;
import com.qxy.evoandroid.http.RetrofitUtil;
import com.qxy.evoandroid.http.callback.ResponseCallback;
import com.qxy.evoandroid.list.RankRoom.Rank;
import com.qxy.evoandroid.list.RankRoom.RankDao;
import com.qxy.evoandroid.list.RankRoom.RankDataBase;
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
    private CacheRepository cacheRepository;

    public MutableLiveData<VersionData> getVersionLiveData() {
        return versionLiveData;
    }

    public MutableLiveData<DataDTO> getDataLiveData() {
        return dataLiveData;
    }

    public ListViewModel(@NonNull Application application) {
        super(application);
        cacheRepository=new CacheRepository(getApplication());
        cacheRepository.cleanAllCache();
    }

    //获取指定榜单指定版本数据
    public void getListData(String cToken,int type,int version) {
        //调用缓存
        if(cacheRepository.isExistRankCache(type, version)){
            cacheRepository.UpdateRankLastTime(type,version);//更新榜单最后一次访问时间
            dataLiveData.setValue(cacheRepository.getPointRankData(type,version).getVideoRank().getData());
        }
        //没有缓存->网络请求
        else {
            Retrofit retrofit = RetrofitManager.getInstance().getRetrofit(Constant.DOUYIN_OPENAPI);
            ApiService apiService = retrofit.create(ApiService.class);
            Call<VideoRank> videoRank = apiService.getVideoRank(cToken, type, version);
            RetrofitUtil.enqueue(videoRank, new ResponseCallback<>() {
                @Override
                public void onSuccess(VideoRank videoRank) {

                    if (videoRank.getData().getErrorCode().equals("0")) {
                        //本地缓存
                        cacheRepository.SaveCache(videoRank, version, type);

                        dataLiveData.setValue(videoRank.getData());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    System.out.println("onFailure");
                }
            });
        }
    }

    //获取指定榜单历史版本信息
    public void getVersion(String cToken,int type){
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit(Constant.DOUYIN_OPENAPI);
        ApiService apiService = retrofit.create(ApiService.class);
        Call<RankVersion> rankVersion=apiService.getRankVersion(cToken,type,10);
        RetrofitUtil.enqueue(rankVersion, new ResponseCallback<>() {
            @Override
            public void onSuccess(RankVersion rankVersion) {

                if (rankVersion.getData().getErrorCode().equals("0")) {
                    versionLiveData.setValue(rankVersion.getData());
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

}
