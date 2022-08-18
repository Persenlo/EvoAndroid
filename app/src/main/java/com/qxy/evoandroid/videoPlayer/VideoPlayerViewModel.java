package com.qxy.evoandroid.videoPlayer;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.qxy.evoandroid.Constant;
import com.qxy.evoandroid.http.RetrofitManager;
import com.qxy.evoandroid.http.RetrofitUtil;
import com.qxy.evoandroid.http.callback.ResponseCallback;
import com.qxy.evoandroid.model.VideoData;
import com.qxy.evoandroid.request.ApiService;

import org.json.JSONArray;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;


public class VideoPlayerViewModel extends AndroidViewModel {

    private final MutableLiveData<String> title = new MutableLiveData<>("暂无标题");
    private final MutableLiveData<String> date = new MutableLiveData<>();
    private final MutableLiveData<String> good = new MutableLiveData<>();
    private final MutableLiveData<String> comment = new MutableLiveData<>();
    private final MutableLiveData<String> downLoad = new MutableLiveData<>();
    private final MutableLiveData<String> forwarding = new MutableLiveData<>();
    private final MutableLiveData<String> play = new MutableLiveData<>();
    private final MutableLiveData<String> share = new MutableLiveData<>();
    private MutableLiveData<String> videoID = new MutableLiveData<>();



    public VideoPlayerViewModel(@NonNull Application application) {
        super(application);
    }

    public void getVideoInfo(String accessToken,String openId,String[] list){
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit(Constant.DOUYIN_OPENAPI);
        ApiService apiService = retrofit.create(ApiService.class);
        if (list==null){return;}
        Call<VideoData> videoDataCall = apiService.getVideoData(accessToken,openId, RequestBody.create(MediaType.parse("application/json; charset=utf-8"),"{"+"\"item_ids\""+": [\""+list[0]+"\"]}"));
        RetrofitUtil.enqueue(videoDataCall, new ResponseCallback<VideoData>() {
            @Override
            public void onSuccess(VideoData videoData) {
                if (!videoData.getData().getErrorCode().equals("0")){
                    Log.e("VideoPlayerModel","NetWork:(getDataInfo)"+videoData.getExtra().getSubDescription());
                }
                else if (videoData.getData().getList()!=null){
                    VideoData.DataDTO.ListDTO listDTO = videoData.getData().getList().get(0);
                    title.setValue(listDTO.getTitle());
                    date.setValue(getRealTime(listDTO.getCreateTime()));
                    good.setValue(listDTO.getStatistics().getDiggCount());
                    comment.setValue(listDTO.getStatistics().getCommentCount());
                    downLoad.setValue(listDTO.getStatistics().getDownloadCount());
                    forwarding.setValue(listDTO.getStatistics().getForwardCount());
                    play.setValue(listDTO.getStatistics().getPlayCount());
                    share.setValue(listDTO.getStatistics().getShareCount());
                    getVideoId(listDTO.getVideoId());
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("VideoPlayerModel","NetWork:(getDataInfo)"+t);
            }
        });
    }

    private void getVideoId(String itemID) {
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit("https://www.iesdouyin.com");
        ApiService apiService = retrofit.create(ApiService.class);
        if (itemID==null) {return;}
        Call<ResponseBody> videoIdCall = apiService.getVideoId(itemID);
        RetrofitUtil.enqueue(videoIdCall, new ResponseCallback<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody body) {
                try {
                    String jsonBody = body.string();
                    JSONObject data = new JSONObject(jsonBody);
                    JSONArray itemLists = data.getJSONArray("item_list");
                    JSONObject item = itemLists.getJSONObject(0);
                    JSONObject video = item.getJSONObject("video");
                    videoID.setValue(video.getString("vid"));
                } catch (Exception e) {
                    Log.e("VideoPlayerModel","JsonErr:(getVideoId)"+e);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("VideoPlayerModel","NetWork:(getVideoId)"+t);
            }
        });
    }

    //把时间戳换为日期
    private String getRealTime(String fTime){
        String realTime = "empty";
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            long lFTime= Long.parseLong(fTime);
            lFTime*=1000;
            Date date=new Date(lFTime);
            realTime = format.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return realTime;
    }


    public MutableLiveData<String> getTitle() {
        return title;
    }

    public MutableLiveData<String> getDate() {
        return date;
    }

    public MutableLiveData<String> getGood() {
        return good;
    }

    public MutableLiveData<String> getComment() {
        return comment;
    }

    public MutableLiveData<String> getDownLoad() {
        return downLoad;
    }

    public MutableLiveData<String> getForwarding() {
        return forwarding;
    }

    public MutableLiveData<String> getPlay() {
        return play;
    }

    public MutableLiveData<String> getShare() {
        return share;
    }

    public MutableLiveData<String> getVideoID() {
        return videoID;
    }

    public void setVideoID(MutableLiveData<String> videoID) {
        this.videoID = videoID;
    }
}
