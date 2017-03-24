package com.atguigu.mybilibili.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.adapter.LiveAdapter;
import com.atguigu.mybilibili.bean.LiveBean;
import com.atguigu.mybilibili.utils.AppNetConfig;

import butterknife.Bind;

/**
 * Created by 李金桐 on 2017/3/21.
 * QQ: 474297694
 * 功能: 直播
 */

public class LiveFragment extends BaseFragment {


    @Bind(R.id.recyclerview)
 public    RecyclerView recyclerview;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
   public LiveAdapter adapter;

    @Override
    protected void initListener() {
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_live;
    }

    @Override
    protected void showLoad() {
        super.showLoad();
        swiperefreshlayout.setColorSchemeResources(R.color.colorPrimary);
        swiperefreshlayout.setRefreshing(true);
    }

    @Override
    protected String setUrl() {
        return AppNetConfig.LIVE;
    }

    @Override
    protected void initData(String json, String error) {
        if (swiperefreshlayout != null) {
            swiperefreshlayout.setRefreshing(false);
        }
        if (TextUtils.isEmpty(json)) {
            Log.e("TAG", "LiveFragment initData()" + error);
        } else {
            JSONObject jsonObject = JSONObject.parseObject(json);
            Integer code = jsonObject.getInteger("code");
            if (code == 0) {
                setAdapter(JSON.parseObject(json, LiveBean.class).getData());
            } else {
                Log.e("TAG", "LiveFragment initData()联网失败");
            }
        }
    }

    private void setAdapter(LiveBean.DataBean liveBean) {
        adapter = new LiveAdapter(mContext, liveBean);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}
