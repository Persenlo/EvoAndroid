package com.qxy.evoandroid.personalInfoActivity.TabListFragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qxy.evoandroid.R;
import com.qxy.evoandroid.model.VideosInfo;
import com.qxy.evoandroid.personalInfoActivity.PIViewModel;
import com.qxy.evoandroid.personalInfoActivity.videoInfo.videoAdp;
import com.qxy.evoandroid.personalInfoActivity.videoInfo.videoItem;

import java.util.ArrayList;
import java.util.List;


public class ShipingFragment extends Fragment {

    private List<videoItem> video_list;
    private RecyclerView videoList_rv;
    private PIViewModel viewModel;

    public ShipingFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shiping, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel=new ViewModelProvider(requireActivity()).get(PIViewModel.class);

        video_list=new ArrayList<>();
        videoList_rv=requireActivity().findViewById(R.id.rv_videoList);

        StaggeredGridLayoutManager lm=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        LinearLayoutManager m=new LinearLayoutManager(getActivity());
        m.setOrientation(LinearLayoutManager.HORIZONTAL);

        videoList_rv.setLayoutManager(lm);
        videoAdp adp=new videoAdp(getContext(),video_list);
        videoList_rv.setAdapter(adp);

        viewModel.getVideoList().observe(getViewLifecycleOwner(),list->{
            for(VideosInfo.DataDTO.ListDTO mem: list){
                videoItem v =new videoItem();
                //封面先放放，设置imageview的网络uri还有点头疼，可能要用ViewModel，但也很麻烦
                v.setTitle(mem.getTitle());
                v.setPlay_count(mem.getStatistics().getPlayCount());
                v.setComment_count(mem.getStatistics().getCommentCount());
                v.setOn_top(mem.isIsTop());
                video_list.add(v);
            }
           adp.notifyItemChanged(adp.getItemCount());
        });
        adp.notifyItemChanged(adp.getItemCount());
    }
}