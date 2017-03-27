package com.atguigu.mybilibili.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

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
    @Bind(R.id.tv_seekbar)
    TextView tvSeekbar;
    @Bind(R.id.seekbar)
    SeekBar seekbar;
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
        seekbar.setMax(4);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSeekbar.setText("同时下载数量  "+(progress+1)+"  ");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回键可用
    }

    @Override
    protected void initData(String json, String error) {
        adapter = new DownloadAdapter(this,seekbar);
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
        super.onDestroy();
    }

}
