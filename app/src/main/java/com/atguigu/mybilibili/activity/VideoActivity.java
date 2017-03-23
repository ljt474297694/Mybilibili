package com.atguigu.mybilibili.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.bean.VideoBean;
import com.atguigu.mybilibili.fragment.BaseFragment;
import com.atguigu.mybilibili.utils.BitmapUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class VideoActivity extends BaseActivity {
    @Bind(R.id.iv_head)
    ImageView ivHead;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_avid)
    TextView tvAvid;
    @Bind(R.id.tv_play)
    TextView tvPlay;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    //    @Bind(R.id.viewpager)
//    ViewPager viewpager;
//    @Bind(R.id.tabLayout)
//    TabLayout tabLayout;
    @Bind(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;
    boolean isOpen = true;
    List<BaseFragment> fragments;


    @Override
    protected String setUrl() {
        return null;
    }

    @Override
    protected void initListener() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > (collapsingToolbarLayout.getHeight() - toolbar.getHeight()) * 0.75) {
                    if (isOpen) {
                        floatingActionButton.hide();
                        isOpen = false;
                        tvAvid.setVisibility(View.GONE);
                        tvPlay.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (!isOpen) {
                        floatingActionButton.show();
                        tvAvid.setVisibility(View.VISIBLE);
                        tvPlay.setVisibility(View.GONE);
                        isOpen = true;
                    }
                }
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_test:
                        Toast.makeText(VideoActivity.this, "测试", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_help:
                        Toast.makeText(VideoActivity.this, "帮助", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_setting:
                        Toast.makeText(VideoActivity.this, "设置", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void initView() {
        toolbar.inflateMenu(R.menu.menu_toolbar_dian);
        tvPlay.setVisibility(View.GONE);
        BitmapUtils.glideToImage("http://i0.hdslb.com/bfs/archive/7c6c605d69914540c0f2ea1c48645113451228d9.jpg@384w_240h_1e_1c.webp", ivHead);

    }

    @Override
    protected void initData(String json, String error) {
        if (TextUtils.isEmpty(json)) {
            Log.e("TAG", "SearchActivity initData()" + error);
        } else {
            JSONObject jsonObject = JSONObject.parseObject(json);
            Integer code = jsonObject.getInteger("code");
            if (code == 0) {
//                setAdapter(JSON.parseObject(json, VideoBean.class).getData());
            } else {
                Log.e("TAG", "SearchActivity initData()联网失败");
            }
        }
    }

    private void setAdapter(VideoBean.DataBean data) {
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_video;
    }


    @OnClick({R.id.iv_back, R.id.tv_play, R.id.floatingActionButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_play:
                appBarLayout.setExpanded(true);
                floatingActionButton.show();
                tvAvid.setVisibility(View.VISIBLE);
                tvPlay.setVisibility(View.GONE);
                isOpen = true;
                break;
            case R.id.floatingActionButton:
                Toast.makeText(VideoActivity.this, "播放", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
