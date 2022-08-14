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
        // Required empty public constructor
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
        videoAdp adp=new videoAdp(video_list);
        videoList_rv.setAdapter(adp);
        video_list.add(new videoItem("wdubqudbuwnd"));

        adp.notifyItemChanged(adp.getItemCount());
    }
}