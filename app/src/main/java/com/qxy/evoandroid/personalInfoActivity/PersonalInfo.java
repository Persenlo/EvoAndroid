package com.qxy.evoandroid.personalInfoActivity;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.StatusBarManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.qxy.evoandroid.BaseActivity;
import com.qxy.evoandroid.R;
import com.qxy.evoandroid.databinding.ActivityPersonalInfoBinding;
import com.qxy.evoandroid.douyinapi.TokenUtil;
import com.qxy.evoandroid.personalInfoActivity.tabListFragments.FansFragment;
import com.qxy.evoandroid.personalInfoActivity.tabListFragments.FollowFragment;
import com.qxy.evoandroid.personalInfoActivity.tabListFragments.VideoFragment;


import java.util.ArrayList;

public class PersonalInfo extends BaseActivity{
    ArrayList<String> list = new ArrayList<>();

    PIViewModel PIViewModel;
    ActivityPersonalInfoBinding binding;

    String userToken;
    String userOpenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        setDarkStatusBar();


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
        //获取令牌
        TokenUtil tokenUtil = new TokenUtil(this);
        userToken = tokenUtil.getToken();
        userOpenId = tokenUtil.getOpenId();
        //加载数据
        PIViewModel.getUserInfo(userToken,userOpenId);
        initTabLayout();
        //初始化标签翻页
        initTabViewpager();
        //返回键设置
        binding.ivBack.setOnClickListener(view -> finish());
    }

    private void initTabViewpager() {
        //设置页面翻页适配器
        binding.vpPager.setAdapter(new ViewPagerAdapter(this,list));
        binding.vpPager.setOffscreenPageLimit(1);
        binding.vpPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        //添加页面变更监听器
        binding.vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                //选中tab_title指定的标签
                binding.tabTitle.getTabAt(position).select();
            }
        });
    }

    //viewPager适配器设置
    private static class ViewPagerAdapter extends FragmentStateAdapter{
        ArrayList<String> list;

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity,ArrayList<String> list) {
            super(fragmentActivity);
            this.list=list;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) return new FansFragment();
            else if (position == 1)  return new FollowFragment();
            else if (position == 2)return new VideoFragment();
            return new FansFragment();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    //TabLayout设置
    private void initTabLayout() {
        //添加指定的文字标签
        binding.tabTitle.addTab(binding.tabTitle.newTab().setText(list.get(0)));
        binding.tabTitle.addTab(binding.tabTitle.newTab().setText(list.get(1)));
        binding.tabTitle.addTab(binding.tabTitle.newTab().setText(list.get(2)));
        //标签选中监听器
        binding.tabTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //让viewpager显示指定位置的页面
                binding.vpPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}