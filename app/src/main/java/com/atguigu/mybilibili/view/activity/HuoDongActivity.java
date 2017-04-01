package com.atguigu.mybilibili.view.activity;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.presenter.adapter.HuatiAdapter;
import com.atguigu.mybilibili.model.bean.DiscoverHuatiBean;
import com.atguigu.mybilibili.utils.AppNetConfig;

import java.util.List;

import butterknife.Bind;

public class HuoDongActivity extends BaseActivity {

    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;

    @Override
    protected String setUrl() {
        return AppNetConfig.DISCOVER_HUODONG;
    }

    @Override
    protected void initListener() {
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    @Override
    protected void initView() {
        toolBar.setNavigationIcon(R.drawable.ucrop_ic_next);
        toolBar.setTitle("活动中心");
        toolBar.setTitleTextColor(Color.WHITE);
    }

    @Override
    protected void initData(String json, String error) {
        swiperefreshlayout.setRefreshing(false);
        if (TextUtils.isEmpty(json)) {
            Log.e("TAG", "HuoDongActivity initData()" + error);
        } else {
            JSONObject jsonObject = JSONObject.parseObject(json);
            Integer code = jsonObject.getInteger("code");
            if (code == 0) {
                setAdapter(JSON.parseObject(json, DiscoverHuatiBean.class).getList());
            } else {
                Log.e("TAG", "HuoDongActivity initData()联网失败");
            }
        }
    }

    private void setAdapter(List<DiscoverHuatiBean.ListBean> list) {
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(new HuatiAdapter(this, list));
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_huo_dong;
    }

}
