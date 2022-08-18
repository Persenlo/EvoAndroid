package com.qxy.evoandroid.videoPlayer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.qxy.evoandroid.BaseActivity;
import com.qxy.evoandroid.R;
import com.qxy.evoandroid.databinding.ActivityVideoPlayerBinding;
import com.qxy.evoandroid.douyinapi.TokenUtil;
import com.qxy.evoandroid.utils.SingleClickListener;

import xyz.doikki.videoplayer.player.VideoView;

/**
 * 视频播放器
 * 注意事项： 需要通过Intent传入"video_id"
 *          videoId示例：@9VwN1P/FUtMzaHOmbd8sV8791GfsPfCHOZF3qwmjKFEbafD/60zdRmYqig357zEBL8T2VCk3217uyOceYtmRQw==
 */
public class VideoPlayerActivity extends BaseActivity {


    private ActivityVideoPlayerBinding binding;
    private VideoPlayerViewModel videoPlayerViewModel;

    private String openId;
    private String accessToken;
    private String videoID = "empty";
    private String[] videoLists = new String[1];

    private VideoView videoView;
    private TokenUtil tokenUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        initView();
    }

    private void initView(){
        //设置ViewModel
        videoPlayerViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(VideoPlayerViewModel.class);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_video_player);
        binding.setVideoPlayerViewModel(videoPlayerViewModel);
        binding.setLifecycleOwner(this);
        //获取VideoView
        videoView = binding.videoView;
        //初始化数据
        initData();
        //设置状态栏
        getWindow().setStatusBarColor(Color.BLACK);
    }

    private void initData() {
        //获取Token
        tokenUtil = new TokenUtil(this);
        accessToken = tokenUtil.getToken();
        openId = tokenUtil.getOpenId();
        //获取视频ID
        Intent intent = getIntent();
        if (intent.getStringExtra("video_id")!=null){
            videoID = intent.getStringExtra("video_id");
            videoLists[0] = videoID;
        }


        if (videoID.equals("empty")){
            Toast.makeText(this, "视频ID不能为空", Toast.LENGTH_SHORT).show();
            finish();
        }

        videoPlayerViewModel.getVideoInfo(accessToken,openId,videoLists);

        //初始化观察者
        initObserver();

    }

    private void initObserver(){
        videoPlayerViewModel.getVideoID().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                videoView.setUrl("https://www.douyin.com/aweme/v1/play/?video_id="+s);
                //初始化视频播放器
                initVideoView();
            }
        });
    }

    private void initVideoView() {
        //设置循环播放
        videoView.setLooping(true);
        //开始播放
        videoView.start();
        //点击暂停或播放
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (videoView.isPlaying()){
                    videoView.pause();
                    binding.ivVpPlay.setVisibility(View.VISIBLE);
                }else {
                    videoView.start();
                    binding.ivVpPlay.setVisibility(View.GONE);
                }
            }
        });
    }


    //APP暂停时暂停播放
    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
        binding.ivVpPlay.setVisibility(View.VISIBLE);
    }
}