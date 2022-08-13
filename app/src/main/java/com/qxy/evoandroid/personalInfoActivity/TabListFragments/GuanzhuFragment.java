package com.qxy.evoandroid.personalInfoActivity.TabListFragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qxy.evoandroid.R;
import com.qxy.evoandroid.personalInfoActivity.piRecycleView.GuanzhuAdp;
import com.qxy.evoandroid.personalInfoActivity.piRecycleView.guanzhuP;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GuanzhuFragment extends Fragment {
    private List<guanzhuP> guanzhu_list;
    private RecyclerView gzList;

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
        //测试用
        guanzhu_list=new ArrayList<>();
        gzList= requireActivity().findViewById(R.id.rv_guanzhuList);
        gzList.setLayoutManager(new LinearLayoutManager(getActivity()));
        GuanzhuAdp adp=new GuanzhuAdp(guanzhu_list);
        gzList.setAdapter(adp);
        guanzhuP p=new guanzhuP(null,"老王","中国-广东-佛山","♂");
        guanzhu_list.add(p);
        guanzhu_list.add(p);
        guanzhu_list.add(p);
        adp.notifyItemChanged(adp.getItemCount());
        //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guanzhu, container, false);
    }
}