package com.qxy.evoandroid.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.qxy.evoandroid.personalInfoActivity.tabListFragments.FensiFragment;
import com.qxy.evoandroid.personalInfoActivity.tabListFragments.GuanzhuFragment;
import com.qxy.evoandroid.personalInfoActivity.tabListFragments.ShipingFragment;

import java.util.ArrayList;

public class PageAdapter extends FragmentPagerAdapter {
    //声明标题文本队列
    private ArrayList<String> list;
    //碎片页适配器的构造函数，传入碎片管理器与标题队列
    public PageAdapter(@NonNull FragmentManager fm, ArrayList<String> list) {
        super(fm);
        this.list = list;
    }
    //获取指定位置的碎片fragment
    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new FensiFragment();
        }else if (position == 1){
            return new GuanzhuFragment();
        }else if (position == 2){
            return new ShipingFragment();
        }
        return new FensiFragment();
    }
    //获取fragment的个数
    @Override
    public int getCount() {
        return list.size();
    }
    //获取指定碎片页的标题文本
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }
}
