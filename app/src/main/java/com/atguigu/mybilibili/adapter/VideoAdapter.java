package com.atguigu.mybilibili.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.atguigu.mybilibili.fragment.BaseFragment;

import java.util.List;


/**
 * Created by 李金桐 on 2017/3/23.
 * QQ: 474297694
 * 功能: xxxx
 */

public class VideoAdapter extends FragmentPagerAdapter {
    private final List<BaseFragment> fragments;
    String[] datas = {"简介", "评论"};

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }

    public VideoAdapter(FragmentManager fm, List<BaseFragment> fragments, int accout) {
        super(fm);
        datas[1] = "评论(" + accout + ")";
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
