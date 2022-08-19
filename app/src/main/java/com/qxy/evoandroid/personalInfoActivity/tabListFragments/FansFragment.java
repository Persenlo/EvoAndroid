package com.qxy.evoandroid.personalInfoActivity.tabListFragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qxy.evoandroid.databinding.FragmentFansBinding;
import com.qxy.evoandroid.douyinapi.TokenUtil;
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

    private String userToken;
    private String openId;
    private TokenUtil tokenUtil;

    private boolean isHasMore = true;
    private boolean stopRequest = false;

    public FansFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //获取Token
        tokenUtil = new TokenUtil(getContext());
        userToken = tokenUtil.getToken();
        openId = tokenUtil.getOpenId();

        viewModel=new ViewModelProvider(requireActivity()).get(PIViewModel.class);

        fensi_list=new ArrayList<>();

        //配置RV
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvFensiList.setLayoutManager(layoutManager);
        FansAdapter adp=new FansAdapter(fensi_list);
        binding.rvFensiList.setAdapter(adp);
        //observe VM中的list以实现UI绘制
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
            //加载完毕，允许再次请求
            stopRequest = false;

            //判断是否还有更多
            if (list.size() != 0 && isHasMore){
                //还有更多时时隐藏footer
                binding.tvFansFoot.setVisibility(View.GONE);
            }else {
                //没有更多时时显示footer
                binding.tvFansFoot.setVisibility(View.VISIBLE);
            }
        });
        adp.notifyItemChanged(adp.getItemCount());


        //添加滑动监听,实现加载更多
        binding.rvFensiList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy < 0)return;
                if (layoutManager.findLastVisibleItemPosition()==adp.getItemCount()-1) {
                    //判断是否还有更多
                    if(!stopRequest){
                        if (viewModel.getFansList(userToken,openId)){
                            isHasMore = true;
                            stopRequest = true;
                        }else {
                            binding.tvFansFoot.setVisibility(View.VISIBLE);
                            isHasMore = false;
                        }
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentFansBinding.inflate(inflater);
        return binding.getRoot();
    }
}