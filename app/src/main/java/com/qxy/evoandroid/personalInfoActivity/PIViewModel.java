package com.qxy.evoandroid.personalInfoActivity;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.qxy.evoandroid.Constant;
import com.qxy.evoandroid.http.RetrofitManager;
import com.qxy.evoandroid.http.RetrofitUtil;
import com.qxy.evoandroid.http.callback.ResponseCallback;
import com.qxy.evoandroid.model.FansInfo;
import com.qxy.evoandroid.model.FollowInfo;
import com.qxy.evoandroid.model.UserInfo;
import com.qxy.evoandroid.model.VideosInfo;
import com.qxy.evoandroid.request.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class PIViewModel extends AndroidViewModel {

    //粉丝列表信息
    private MutableLiveData<String> fanCursor = new MutableLiveData<>("0");
    private MutableLiveData<Boolean> fanHasMore = new MutableLiveData<>(true);
    private MutableLiveData<List<FansInfo.DataDTO.ListDTO>> fanList = new MutableLiveData<>(new ArrayList<>());
    private List<FansInfo.DataDTO.ListDTO> tempFanList = new ArrayList<>();

    //关注列表信息
    private MutableLiveData<String> followCursor = new MutableLiveData<>("0");
    private MutableLiveData<Boolean> followHasMore = new MutableLiveData<>(true);
    private MutableLiveData<List<FollowInfo.DataDTO.ListDTO>> followList = new MutableLiveData<>(new ArrayList<>());
    private List<FollowInfo.DataDTO.ListDTO> tempFollowList = new ArrayList<>();

    //视频列表信息
    private MutableLiveData<String> videoCursor = new MutableLiveData<>("0");
    private MutableLiveData<Boolean> videoHasMore = new MutableLiveData<>(true);
    private MutableLiveData<List<VideosInfo.DataDTO.ListDTO>> videoList = new MutableLiveData<>(new ArrayList<>());
    private List<VideosInfo.DataDTO.ListDTO> tempVideoList = new ArrayList<>();

    private MutableLiveData<String> userName = new MutableLiveData<>();
    private MutableLiveData<String> userIcon = new MutableLiveData<>();
    private MutableLiveData<String> userLocate = new MutableLiveData<>();
    private MutableLiveData<String> userDesc = new MutableLiveData<>();
    private MutableLiveData<String> usergender = new MutableLiveData<>();

    //每次获取10个
    private final String count = "10";

    Retrofit retrofit;
    ApiService apiService;


    public PIViewModel(@NonNull Application application) {
        super(application);
    }

    void getUserInfo(String userToken,String userOpenId){
        //获取Retrofit
        retrofit = RetrofitManager.getInstance().getRetrofit(Constant.DOUYIN_OPENAPI);
        apiService = retrofit.create(ApiService.class);
        Call<UserInfo> userInfo = apiService.getUserInfo(userToken,userToken,userOpenId);
        RetrofitUtil.enqueue(userInfo, new ResponseCallback<>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                Log.d("description->userInfo",userInfo.getData().getDescription());
                if (userInfo.getData().getErrorCode() == 0) {
                    //设置个人信息
                    userName.setValue(userInfo.getData().getNickname());
                    userIcon.setValue(userInfo.getData().getAvatarLarger());
                    userLocate.setValue(userInfo.getData().getCountry()+"-"+userInfo.getData().getCity());
                    userDesc.setValue(userInfo.getData().getDescription());
                    if(userInfo.getData().getGender()==1) usergender.setValue("男");
                    else if(userInfo.getData().getGender()==2) usergender.setValue("女");
                    else usergender.setValue("未知");
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        //同步加载粉丝、关注、视频列表数据
        getFansList(userToken,userOpenId);
        getFollowList(userToken,userOpenId);
        getVideoList(userToken,userOpenId);

    }

    //获取粉丝列表
    void getFansList(String userToken,String userOpenId){
        //没有下一页时取消请求
        if(!Boolean.TRUE.equals(fanHasMore.getValue())){
            return;
        }
        Call<FansInfo> getFanInfo = apiService.getFansInfo(userToken,userOpenId,fanCursor.getValue(),count);
        RetrofitUtil.enqueue(getFanInfo, new ResponseCallback<FansInfo>() {
            @Override
            public void onSuccess(FansInfo fansInfo) {
                Log.d("description->fansInfo",fansInfo.getData().getDescription());
                if(fansInfo.getData().getErrorCode() == 0){
                    //设置是否有下一页
                    fanHasMore.setValue(fansInfo.getData().isHasMore());
                    if(fanHasMore.getValue()){
                        //有下一页，设置新cursor
                        fanCursor.setValue(fansInfo.getData().getCursor());
                    }
                    //设置List
                    if (fansInfo.getData().getList() != null){
                        tempFanList.addAll(fansInfo.getData().getList());
                        fanList.setValue(tempFanList);
                    }

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("PIViewModel","FanGetErr"+t);
            }
        });
    }
    //获取关注列表
    void getFollowList(String userToken,String userOpenId){
        //没有下一页时取消请求
        if(!Boolean.TRUE.equals(followHasMore.getValue())){
            return;
        }
        Call<FollowInfo> getFollowInfo = apiService.getFollowInfo(userToken,count,userOpenId,followCursor.getValue());
        RetrofitUtil.enqueue(getFollowInfo, new ResponseCallback<FollowInfo>() {
            @Override
            public void onSuccess(FollowInfo followInfo) {
                if (followInfo.getData().getErrorCode() == 0){
                    //设置是否有下一页
                    followHasMore.setValue(followInfo.getData().isHasMore());
                    if(followHasMore.getValue()){
                        //有下一页，设置新cursor
                        followCursor.setValue(followInfo.getData().getCursor());
                    }
                    //设置List
                    if (followInfo.getData().getList() != null){
                        tempFollowList.addAll(followInfo.getData().getList());
                        followList.setValue(tempFollowList);
                        //然后在外面把followList.value拿出来就行了
                    }
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("PIViewModel","FollowGetErr"+t);
            }
        });
    }
    //获取视频列表
    void getVideoList(String userToken,String userOpenId){
        //没有下一页时取消请求
        if(!Boolean.TRUE.equals(videoHasMore.getValue())){
            return;
        }
        Call<VideosInfo> getVideoInfo = apiService.getVideoInfo(userToken,userOpenId,videoCursor.getValue(),count);
        RetrofitUtil.enqueue(getVideoInfo, new ResponseCallback<VideosInfo>() {
            @Override
            public void onSuccess(VideosInfo videoInfo) {
                if (videoInfo.getData().getErrorCode() == 0){
                    //设置是否有下一页
                    videoHasMore.setValue(videoInfo.getData().isHasMore());
                    if(videoHasMore.getValue()){
                        //有下一页，设置新cursor
                        videoCursor.setValue(videoInfo.getData().getCursor());
                    }
                    //设置List
                    if (videoInfo.getData().getList() != null){
                        tempVideoList.addAll(videoInfo.getData().getList());
                        videoList.setValue(tempVideoList);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("PIViewModel","VideoGetErr"+t);
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

    public void setUserDesc(MutableLiveData<String> userDesc) {
        this.userDesc = userDesc;
    }

    public void setUsergender(MutableLiveData<String> usergender) {
        this.usergender = usergender;
    }

    public void setUserLocate(MutableLiveData<String> userLocate) {
        this.userLocate = userLocate;
    }

    public MutableLiveData<String> getUserDesc() {
        return userDesc;
    }

    public MutableLiveData<String> getUsergender() {
        return usergender;
    }

    public MutableLiveData<String> getFanCursor() {
        return fanCursor;
    }


    public MutableLiveData<Boolean> getFanHasMore() {
        return fanHasMore;
    }


    public MutableLiveData<List<FansInfo.DataDTO.ListDTO>> getFanList() {
        return fanList;
    }


    public MutableLiveData<String> getFollowCursor() {
        return followCursor;
    }


    public MutableLiveData<Boolean> getFollowHasMore() {
        return followHasMore;
    }


    public MutableLiveData<List<FollowInfo.DataDTO.ListDTO>> getFollowList() {
        return followList;
    }


    public MutableLiveData<String> getVideoCursor() {
        return videoCursor;
    }


    public MutableLiveData<Boolean> getVideoHasMore() {
        return videoHasMore;
    }


    public MutableLiveData<List<VideosInfo.DataDTO.ListDTO>> getVideoList() {
        return videoList;
    }

}
