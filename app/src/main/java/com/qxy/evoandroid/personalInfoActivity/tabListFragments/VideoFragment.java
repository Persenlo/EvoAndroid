package com.qxy.evoandroid.personalInfoActivity.tabListFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qxy.evoandroid.databinding.FragmentVideoBinding;
import com.qxy.evoandroid.douyinapi.TokenUtil;
import com.qxy.evoandroid.model.VideosInfo;
import com.qxy.evoandroid.personalInfoActivity.PIViewModel;
import com.qxy.evoandroid.personalInfoActivity.videoInfo.VideoAdapter;
import com.qxy.evoandroid.personalInfoActivity.videoInfo.VideoItem;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class VideoFragment extends Fragment {

    private List<VideoItem> video_list;
    private FragmentVideoBinding binding;
    private PIViewModel viewModel;

    private String userToken;
    private String openId;
    private TokenUtil tokenUtil;

    private boolean isHasMore = true;

    public VideoFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentVideoBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel=new ViewModelProvider(requireActivity()).get(PIViewModel.class);

        //获取Token
        tokenUtil = new TokenUtil(getContext());
        userToken = tokenUtil.getToken();
        openId = tokenUtil.getOpenId();

        video_list=new ArrayList<>();

        GridLayoutManager lm=new GridLayoutManager(getContext(),2);

        binding.rvVideoList.setLayoutManager(lm);
        VideoAdapter adp=new VideoAdapter(getContext(),video_list);
        binding.rvVideoList.setAdapter(adp);

        viewModel.getVideoList().observe(getViewLifecycleOwner(),list->{
            for(VideosInfo.DataDTO.ListDTO mem: list){
                VideoItem v =new VideoItem();
                v.setCover(mem.getCover());
                v.setTitle(mem.getTitle());
                v.setPlay_count(mem.getStatistics().getPlayCount());
                v.setComment_count(mem.getStatistics().getCommentCount());
                v.setItemId(mem.getItemId());//设置VideoId
                //把时间戳换为日期
                try {
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
                    long l= Long.parseLong(mem.getCreateTime());
                    l*=1000;
                    Date date=new Date(l);
                    v.setTime(format.format(date));
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                v.setTime(mem.getCreateTime());
//                v.setOn_top(mem.isIsTop());
                if(mem.isIsTop()) video_list.add(0,v);
                else video_list.add(v);
            }
            adp.notifyItemChanged(adp.getItemCount());

            //判断是否还有更多
            if (list.size() != 0 && isHasMore){
                //还有更多时时隐藏footer
                binding.tvVideoFoot.setVisibility(View.GONE);
            }else {
                //没有更多时时显示footer
                binding.tvVideoFoot.setVisibility(View.VISIBLE);
            }
        });
        adp.notifyItemChanged(adp.getItemCount());

        //添加滑动监听,实现加载更多
        binding.rvVideoList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy < 0)return;
                if (lm.findLastVisibleItemPosition()==adp.getItemCount()-1) {
                    if (viewModel.getVideoList(userToken,openId)){
                        isHasMore = true;
                    }else {
                        binding.tvVideoFoot.setVisibility(View.VISIBLE);
                        isHasMore = false;
                    }
                }
            }
        });
    }
}