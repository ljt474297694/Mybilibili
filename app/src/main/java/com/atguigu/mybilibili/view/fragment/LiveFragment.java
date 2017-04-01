package com.atguigu.mybilibili.view.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.bean.LiveBean;
import com.atguigu.mybilibili.presenter.adapter.LiveAdapter;
import com.atguigu.mybilibili.utils.AppNetConfig;
import com.atguigu.mybilibili.view.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by 李金桐 on 2017/3/21.
 * QQ: 474297694
 * 功能: 直播
 */

public class LiveFragment extends BaseFragment {


    @Bind(R.id.recyclerview)
    public RecyclerView recyclerview;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    public LiveAdapter adapter;

    public boolean isRefresh;


    @Override
    protected int setLayoutId() {
        return R.layout.fragment_live;
    }

    @Override
    protected void initView() {
        swiperefreshlayout.setColorSchemeResources(R.color.colorPrimary);
    }

    @Override
    protected void initListener() {
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                refresh();
            }
        });
    }

    @Override
    public void showLoading() {
        swiperefreshlayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swiperefreshlayout.setRefreshing(false);
    }


    @Override
    public String setUrl() {
        return AppNetConfig.LIVE;
    }

    @Override
    protected void initData(String json, String error) {
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
        Log.e("TAG", "LiveFragment setAdapter()"+isRefresh);
        if (adapter == null) {
            adapter = new LiveAdapter(mContext, liveBean);
        } else {
            adapter.refresh(liveBean);
            adapter.notifyDataSetChanged();
            isRefresh = false;
        }
        if (!isRefresh) {
            recyclerview.setAdapter(adapter);
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }
}
