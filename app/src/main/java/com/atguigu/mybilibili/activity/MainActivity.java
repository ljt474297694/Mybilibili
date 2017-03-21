package com.atguigu.mybilibili.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.adapter.HomeAdapter;
import com.atguigu.mybilibili.fragment.CartoonFragment;
import com.atguigu.mybilibili.fragment.DiscoverFragment;
import com.atguigu.mybilibili.fragment.LiveFragment;
import com.atguigu.mybilibili.fragment.PartitionFragment;
import com.atguigu.mybilibili.fragment.RecommendFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.coordinatorlayout)
    CoordinatorLayout coordinatorlayout;
    @Bind(R.id.drawerlayout)
    DrawerLayout drawerlayout;

    List<Fragment> fragments;
    private HomeAdapter homeAdapter;

    @Override
    protected String setUrl() {
        return null;
    }

    @Override
    protected void initListener() {
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();

                if (menuItemId == R.id.menu_download) {
                    Toast.makeText(MainActivity.this, "搜索", Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.menu_search) {
                    Toast.makeText(MainActivity.this, "下载", Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });
    }

    @Override
    protected void initView() {
        toolBar.inflateMenu(R.menu.menu_toolbar);
    }

    @Override
    protected void initData(String json, String error) {
        fragments = new ArrayList<>();
        fragments.add(new LiveFragment());
        fragments.add(new RecommendFragment());
        fragments.add(new CartoonFragment());
        fragments.add(new PartitionFragment());
        fragments.add(new DiscoverFragment());


        homeAdapter = new HomeAdapter(getSupportFragmentManager(), fragments);
        viewpager.setAdapter(homeAdapter);

        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }


    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }


    private int startY;
    private int startX;
    private boolean isScrollY;
    private boolean isFirst;
    private boolean isOpen = true;
    //tollBar 回弹效果
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int eventY = (int) ev.getY();
        int eventX = (int) ev.getX();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = eventY;
                startX = eventX;
                isFirst = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isFirst) {
                    if (Math.abs(eventX - startX) > Math.abs(eventY - startY) && Math.abs(eventX - startX) > toolBar.getHeight()*0.30) {
                        isScrollY = false;
                        isFirst = false;
                        appbar.setExpanded(isOpen);
                    } else if (Math.abs(eventY - startY) > Math.abs(eventX - startX) && Math.abs(eventY - startY) > toolBar.getHeight()*0.30) {
                        isScrollY = true;
                        isFirst = false;
                    }
                }
                if (isOpen) {
                    if (startY < eventY) {
                        startY = eventY;
                    }
                } else {
                    if (startY > eventY) {
                        startY = eventY;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isScrollY) {
                    if (isOpen) {
                        if (startY - eventY > toolBar.getHeight() * 0.36) {
                            appbar.setExpanded(false);
                            isOpen = false;
                        } else {
                            appbar.setExpanded(true);
                            isOpen = true;
                        }
                    } else {
                        if (eventY - startY > toolBar.getHeight() * 0.36) {
                            appbar.setExpanded(true);
                            isOpen = true;
                        } else {
                            appbar.setExpanded(false);
                            isOpen = false;
                        }
                    }
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}