package com.qxy.evoandroid.personalInfoActivity.tabListFragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qxy.evoandroid.databinding.FragmentFollowBinding;
import com.qxy.evoandroid.model.FollowInfo;
import com.qxy.evoandroid.personalInfoActivity.PIViewModel;
import com.qxy.evoandroid.personalInfoActivity.piRecycleView.FollowAdapter;
import com.qxy.evoandroid.personalInfoActivity.piRecycleView.FollowP;

import java.util.ArrayList;
import java.util.List;

public class FollowFragment extends Fragment {

    private List<FollowP> guanzhu_list;
    private FragmentFollowBinding binding;
    private PIViewModel viewModel;

    public FollowFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel=new ViewModelProvider(requireActivity()).get(PIViewModel.class);
        guanzhu_list=new ArrayList<>();
        binding.rvGuanzhuList.setLayoutManager(new LinearLayoutManager(getActivity()));
        FollowAdapter adp=new FollowAdapter(guanzhu_list);
        binding.rvGuanzhuList.setAdapter(adp);
        //observe VM中的关注list以实现UI绘制
        viewModel.getFollowList().observe(getViewLifecycleOwner(),list -> {
            for(FollowInfo.DataDTO.ListDTO mem : list){
                FollowP p=new FollowP();
                p.setAvatar(mem.getAvatar());
                p.setLocate(mem.getCountry()+"-"+mem.getProvince()+"-"+mem.getCity());
                p.setNickName(mem.getNickname());
                p.setGender(mem.getGender());
                guanzhu_list.add(p);
            }
            adp.notifyItemChanged(adp.getItemCount());
        });
        adp.notifyItemChanged(adp.getItemCount());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentFollowBinding.inflate(inflater);
        return binding.getRoot();
    }
}