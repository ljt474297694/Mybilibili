package com.atguigu.mybilibili.activity;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.fragment.OriginalFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class OriginalActivity extends BaseActivity {

    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.activity_original)
    CoordinatorLayout activityOriginal;
    List<Fragment> fragments;
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
                    Toast.makeText(OriginalActivity.this, "搜索", Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.menu_search) {
                    Toast.makeText(OriginalActivity.this, "下载", Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView() {
        toolBar.setNavigationIcon(R.drawable.ucrop_ic_next);
        toolBar.setTitle("排行榜");
        toolBar.setTitleTextColor(Color.WHITE);
        toolBar.inflateMenu(R.menu.menu_toolbar);
    }

    @Override
    protected void initData(String jaon, String error) {
        fragments = new ArrayList<>();
        fragments.add(new OriginalFragment());

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_original;
    }

}
