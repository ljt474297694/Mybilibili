package com.atguigu.mybilibili.presenter.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.atguigu.mybilibili.view.fragment.LiveHudongFragment;
import com.atguigu.mybilibili.view.fragment.LiveJianDuiFragment;
import com.atguigu.mybilibili.view.fragment.LivePaiHangFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李金桐 on 2017/3/25.
 * QQ: 474297694
 * 功能: xxxx
 */

public class LivePagerAdapter extends FragmentPagerAdapter {
    String[] datas  = {"互动","排行榜","舰队"};
    List<Fragment> fragments;

    @Override
    public CharSequence getPageTitle(int position) {
        return datas[position];
    }

    public LivePagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(new LiveHudongFragment());
        fragments.add(new LivePaiHangFragment());
        fragments.add(new LiveJianDuiFragment());

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
