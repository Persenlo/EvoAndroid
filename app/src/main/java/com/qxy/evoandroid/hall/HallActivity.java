package com.qxy.evoandroid.hall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.qxy.evoandroid.BaseActivity;
import com.qxy.evoandroid.R;
import com.qxy.evoandroid.databinding.ActivityHallBinding;
import com.qxy.evoandroid.douyinapi.TokenUtil;
import com.qxy.evoandroid.list.ListActivity;
import com.qxy.evoandroid.userLogin.LoginViewModel;

/**
 * 大厅界面，用于做其它功能的入口
 * @author Persenlo
 */
public class HallActivity extends BaseActivity {

    HallViewModel hallViewModel;
    ActivityHallBinding binding;

    TokenUtil tokenUtil;
    String userToken;
    String userOpenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall);

        initView();

        initRank();
    }

    //跳转排行榜
    private void initRank() {
        Intent intent=new Intent(HallActivity.this, ListActivity.class);
        binding.cvHallRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("SELECT_TYPE",1);
                startActivity(intent);
            }
        });
        binding.cvHallTeleplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("SELECT_TYPE",2);
                startActivity(intent);
            }
        });
        binding.cvHallVariety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("SELECT_TYPE",3);
                startActivity(intent);
            }
        });

    }


    private void initView() {
        //获取令牌
        tokenUtil = new TokenUtil(this);
        userToken = tokenUtil.getToken();
        userOpenId = tokenUtil.getOpenId();

        hallViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(HallViewModel.class);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_hall);
        binding.setHallViewModel(hallViewModel);
        binding.setLifecycleOwner(this);

        hallViewModel.getUserInfo(userToken,userOpenId);
    }


}