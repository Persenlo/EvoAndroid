package com.qxy.evoandroid.personalInfoActivity.TabListFragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qxy.evoandroid.R;
import com.qxy.evoandroid.model.FollowInfo;
import com.qxy.evoandroid.personalInfoActivity.PIViewModel;
import com.qxy.evoandroid.personalInfoActivity.piRecycleView.GuanzhuAdp;
import com.qxy.evoandroid.personalInfoActivity.piRecycleView.guanzhuP;

import java.util.ArrayList;
import java.util.List;

public class GuanzhuFragment extends Fragment {

    private List<guanzhuP> guanzhu_list;
    private RecyclerView gzList_rv;
    private PIViewModel viewModel;

    public GuanzhuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel=new ViewModelProvider(requireActivity()).get(PIViewModel.class);

        guanzhu_list=new ArrayList<>();
        gzList_rv = requireActivity().findViewById(R.id.rv_guanzhuList);
        gzList_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        GuanzhuAdp adp=new GuanzhuAdp(guanzhu_list);
        gzList_rv.setAdapter(adp);
        //尝试observe VM中的关注list以实现UI绘制，不一定能行
        //成功！！！
        viewModel.getFollowList().observe(getViewLifecycleOwner(),list -> {
            for(FollowInfo.DataDTO.ListDTO mem : list){
                guanzhuP p=new guanzhuP();
                p.setAvatar(mem.getAvatar());
                p.setLocate(mem.getCountry()+"-"+mem.getProvince()+"-"+mem.getCity());
                p.setNickName(mem.getNickname());
                if(mem.getGender()==1) p.setGender("♂");
                else if(mem.getGender()==2) p.setGender("♀");
                else p.setGender("/");
                guanzhu_list.add(p);
            }
            adp.notifyItemChanged(adp.getItemCount());
        });
        adp.notifyItemChanged(adp.getItemCount());
        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guanzhu, container, false);
    }
}