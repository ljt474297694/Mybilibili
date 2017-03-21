package com.atguigu.mybilibili.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 李金桐 on 2017/3/21.
 * QQ: 474297694
 * 功能: xxxx
 */

public class OriginalAdapter extends FragmentPagerAdapter {
    private  List<Fragment> fragments;
    String[] titles  = {"全站","原创","番剧"};
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public OriginalAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
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
