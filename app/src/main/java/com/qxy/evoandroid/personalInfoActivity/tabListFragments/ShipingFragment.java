package com.qxy.evoandroid.personalInfoActivity.tabListFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qxy.evoandroid.databinding.FragmentShipingBinding;
import com.qxy.evoandroid.model.VideosInfo;
import com.qxy.evoandroid.personalInfoActivity.PIViewModel;
import com.qxy.evoandroid.personalInfoActivity.videoInfo.VideoAdp;
import com.qxy.evoandroid.personalInfoActivity.videoInfo.VideoItem;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.SimpleFormatter;


public class ShipingFragment extends Fragment {

    private List<VideoItem> video_list;
    private FragmentShipingBinding binding;
    private PIViewModel viewModel;

    public ShipingFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentShipingBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel=new ViewModelProvider(requireActivity()).get(PIViewModel.class);

        video_list=new ArrayList<>();

        StaggeredGridLayoutManager lm=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        binding.rvVideoList.setLayoutManager(lm);
        VideoAdp adp=new VideoAdp(getContext(),video_list);
        binding.rvVideoList.setAdapter(adp);

        viewModel.getVideoList().observe(getViewLifecycleOwner(),list->{
            for(VideosInfo.DataDTO.ListDTO mem: list){
                VideoItem v =new VideoItem();
                v.setCover(mem.getCover());
                v.setTitle(mem.getTitle());
                v.setPlay_count(mem.getStatistics().getPlayCount());
                v.setComment_count(mem.getStatistics().getCommentCount());
                //把时间戳换为日期
                //怎么这么困难呢orz
                //Unix时间戳要乘1000，难绷
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
                //v.setOn_top(mem.isIsTop());
                if(mem.isIsTop()) video_list.add(0,v);
                else video_list.add(v);
            }
           adp.notifyItemChanged(adp.getItemCount());
        });
        adp.notifyItemChanged(adp.getItemCount());
    }
}