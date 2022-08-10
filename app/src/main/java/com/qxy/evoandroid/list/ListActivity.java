package com.qxy.evoandroid.list;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.qxy.evoandroid.Adapter.ListAdapter;
import com.qxy.evoandroid.BaseActivity;
import com.qxy.evoandroid.R;
import com.qxy.evoandroid.databinding.ActivityListBinding;
import com.qxy.evoandroid.douyinapi.TokenUtil;
import com.qxy.evoandroid.model.VideoRank;

import java.util.List;

public class ListActivity extends BaseActivity {

    private ListViewModel listViewModel;
    private com.qxy.evoandroid.databinding.ActivityListBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        initRecyclerView();
    }

    private void initRecyclerView() {
        listViewModel.getLiveData().observe(this, new Observer<List<VideoRank.DataDTO.ListDTO>>() {
            @Override
            public void onChanged(List<VideoRank.DataDTO.ListDTO> listDTOS) {
                binding.rvList.setLayoutManager(new LinearLayoutManager(ListActivity.this));
                binding.rvList.setAdapter(new ListAdapter(listDTOS,ListActivity.this));
            }
        });
    }

    private void init(){
        TokenUtil tokenUtil=new TokenUtil(this);
        String cToken=tokenUtil.getClientToken();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        listViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.AndroidViewModelFactory()).get(ListViewModel.class);

        binding.setData(listViewModel);
        binding.setLifecycleOwner(this);

        int SELECT_TYPE=getIntent().getIntExtra("SELECT_TYPE",0);

        listViewModel.getListData(cToken,SELECT_TYPE);
    }

}
