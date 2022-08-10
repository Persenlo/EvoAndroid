package com.qxy.evoandroid.list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.qxy.evoandroid.Constant;
import com.qxy.evoandroid.http.RetrofitManager;
import com.qxy.evoandroid.http.RetrofitUtil;
import com.qxy.evoandroid.http.callback.ResponseCallback;
import com.qxy.evoandroid.model.VideoRank;
import com.qxy.evoandroid.model.VideoRank.DataDTO.ListDTO;
import com.qxy.evoandroid.request.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ListViewModel extends AndroidViewModel {
    private MutableLiveData<List<ListDTO>> listLiveData=new MutableLiveData<>();

    public MutableLiveData<List<ListDTO>> getLiveData() {
        return listLiveData;
    }

    public ListViewModel(@NonNull Application application) {
        super(application);
    }

    public void getListData(String cToken,int type){
        Retrofit retrofit= RetrofitManager.getInstance().getRetrofit(Constant.DOUYIN_OPENAPI);
        ApiService apiService=retrofit.create(ApiService.class);

        if(cToken.equals("empty")){

        }else {
            Call<VideoRank> videoRank=apiService.getVideoRank(cToken,type,18);
            RetrofitUtil.enqueue(videoRank, new ResponseCallback<VideoRank>() {
                @Override
                public void onSuccess(VideoRank videoRank) {
                    if(videoRank.getData().getErrorCode().equals("0")){
                        listLiveData.setValue(videoRank.getData().getList());
                    }
                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }


}
