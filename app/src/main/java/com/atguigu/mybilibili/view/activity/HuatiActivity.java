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
import com.atguigu.mybilibili.bean.DiscoverHuatiBean;
import com.atguigu.mybilibili.presenter.adapter.HuatiAdapter;
import com.atguigu.mybilibili.utils.AppNetConfig;
import com.atguigu.mybilibili.view.base.BaseActivity;

import java.util.List;

import butterknife.Bind;

public class HuatiActivity extends BaseActivity {

    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    public boolean isRefresh;
    private HuatiAdapter adapter;

    @Override
    public String setUrl() {
        return AppNetConfig.DISCOVER_HUATI;
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
                isRefresh = true;
                refresh();
            }
        });
    }

    @Override
    protected void initView() {
        toolBar.setNavigationIcon(R.drawable.ucrop_ic_next);
        toolBar.setTitle("话题中心");
        toolBar.setTitleTextColor(Color.WHITE);
    }

    @Override
    protected void initData(String json, String error) {
        if (TextUtils.isEmpty(json)) {
            Log.e("TAG", "HuatiActivity initData()" + error);
        } else {
            JSONObject jsonObject = JSONObject.parseObject(json);
            Integer code = jsonObject.getInteger("code");
            if (code == 0) {
                setAdapter(JSON.parseObject(json, DiscoverHuatiBean.class).getList());
            } else {
                Log.e("TAG", "HuatiActivity initData()联网失败");
            }
        }
    }

    private void setAdapter(List<DiscoverHuatiBean.ListBean> list) {
        if (adapter == null) {
            adapter = new HuatiAdapter(this, list);
        } else {
            adapter.refresh(list);
            adapter.notifyDataSetChanged();
            isRefresh = false;
        }
        if (!isRefresh) {
            recyclerview.setAdapter(adapter);
            recyclerview.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_huati;
    }

    @Override
    public void showLoading() {
        swiperefreshlayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swiperefreshlayout.setRefreshing(false);
    }

}
