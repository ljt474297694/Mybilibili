package com.atguigu.mybilibili.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.atguigu.mybilibili.fragment.SearchFragment;

import java.util.List;

/**
 * Created by 李金桐 on 2017/3/21.
 * QQ: 474297694
 * 功能: xxxx
 */

public class SearchAdapter extends FragmentStatePagerAdapter {
    private  List<SearchFragment> fragments;
    String[] data = {"综合","番剧","UP主","影视"};
    @Override
    public CharSequence getPageTitle(int position) {
        return data[position];
    }

    public SearchAdapter(FragmentManager fm, List<SearchFragment> fragments, int total) {
        super(fm);
        this.fragments =fragments;
        data[2] = "UP主("+total+")";
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
