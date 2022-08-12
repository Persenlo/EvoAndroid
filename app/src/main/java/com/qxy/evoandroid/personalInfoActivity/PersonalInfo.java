package com.qxy.evoandroid.personalInfoActivity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.qxy.evoandroid.Adapter.PageAdapter;
import com.qxy.evoandroid.BaseActivity;
import com.qxy.evoandroid.R;
import com.qxy.evoandroid.databinding.ActivityPersonalInfoBinding;


import java.util.ArrayList;

public class PersonalInfo extends BaseActivity implements TabLayout.OnTabSelectedListener {

    TabLayout tab_title;
    ViewPager vp_pager;
    ArrayList<String> list = new ArrayList<>();

    PIViewModel PIViewModel;
    ActivityPersonalInfoBinding binding;

    String userToken;
    String userOpenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        list.add("粉丝");
        list.add("关注");
        list.add("视频列表");
        init();
    }

    private void init(){
        PIViewModel= new ViewModelProvider(this)
                .get(com.qxy.evoandroid.personalInfoActivity.PIViewModel.class);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_personal_info);
        binding.setPIVM(PIViewModel);
        binding.setLifecycleOwner(this);
        userToken=getIntent().getStringExtra("userToken");
        userOpenId=getIntent().getStringExtra("userOpenID");
        PIViewModel.getUserInfo(userToken,userOpenId);
        initTabLayout();
        //初始化标签翻页
        initTabViewpager();
    }

    private void initTabViewpager() {
        //构建商品翻页的适配器
        PageAdapter pagerAdapter = new PageAdapter(getSupportFragmentManager(), list);
        //设置商品翻页适配器
        binding.vpPager.setAdapter(pagerAdapter);
        //添加页面变更监听器
        binding.vpPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //选中tab_title指定的标签
                tab_title.getTabAt(position).select();
            }
        });
    }

    private void initTabLayout() {
        tab_title = findViewById(R.id.tab_title);
        //添加指定的文字标签
        tab_title.addTab(tab_title.newTab().setText(list.get(0)));
        tab_title.addTab(tab_title.newTab().setText(list.get(1)));
        tab_title.addTab(tab_title.newTab().setText(list.get(2)));
        //标签选中监听器
        tab_title.addOnTabSelectedListener(this);
    }
    //在标签选中时触发
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //让viewpager显示指定位置的页面
        binding.vpPager.setCurrentItem(tab.getPosition());
    }

    //取消选中
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }
    //重复选中
    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}