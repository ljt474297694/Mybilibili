package com.atguigu.mybilibili.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.adapter.DownloadAdapter;

import butterknife.Bind;

public class DownloadActivity extends BaseActivity {


    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.activity_download)
    CoordinatorLayout activityDownload;
    private DownloadAdapter adapter;

    @Override
    protected String setUrl() {
        return null;
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回键可用
    }

    @Override
    protected void initData(String json, String error) {
        adapter = new DownloadAdapter(this);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_download;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        for (int i = 0; i < adapter.list.size(); i++) {
            adapter.list.get(i).cancel();
        }
        super.onDestroy();

    }

}
