package com.qxy.evoandroid.personalInfoActivity.tabListFragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qxy.evoandroid.databinding.FragmentFansBinding;
import com.qxy.evoandroid.model.FansInfo;
import com.qxy.evoandroid.personalInfoActivity.PIViewModel;
import com.qxy.evoandroid.personalInfoActivity.piRecycleView.FansAdapter;
import com.qxy.evoandroid.personalInfoActivity.piRecycleView.FansP;

import java.util.ArrayList;
import java.util.List;

public class FansFragment extends Fragment {

    private List<FansP> fensi_list;
    private PIViewModel viewModel;
    private FragmentFansBinding binding;

    public FansFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel=new ViewModelProvider(requireActivity()).get(PIViewModel.class);

        fensi_list=new ArrayList<>();

        binding.rvFensiList.setLayoutManager(new LinearLayoutManager(getActivity()));
        FansAdapter adp=new FansAdapter(fensi_list);
        binding.rvFensiList.setAdapter(adp);
        //observe VM中的关注list以实现UI绘制
        viewModel.getFanList().observe(getViewLifecycleOwner(),list -> {
            for(FansInfo.DataDTO.ListDTO mem : list){
                FansP p=new FansP();
                p.setAvatar(mem.getAvatar());
                p.setLocate(mem.getCountry()+"-"+mem.getProvince()+"-"+mem.getCity());
                p.setNickName(mem.getNickname());
                p.setGender(mem.getGender());
                fensi_list.add(p);
            }
            adp.notifyItemChanged(adp.getItemCount());
        });
        adp.notifyItemChanged(adp.getItemCount());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentFansBinding.inflate(inflater);
        return binding.getRoot();
    }
}