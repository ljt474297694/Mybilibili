package com.atguigu.mybilibili.view.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.bean.DiscoverOriginalBean;
import com.atguigu.mybilibili.presenter.adapter.OriginalFragmentAdapter;
import com.atguigu.mybilibili.utils.AppNetConfig;
import com.atguigu.mybilibili.view.base.BaseFragment;

import java.util.List;

import butterknife.Bind;

/**
 * Created by 李金桐 on 2017/3/21.
 * QQ: 474297694
 * 功能: xxxx
 */

public class OriginalFragment extends BaseFragment {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    private OriginalFragmentAdapter adapter;
    public boolean isRefresh;

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
    protected int setLayoutId() {
        return R.layout.fragment_original;
    }

    @Override
    protected void initView() {
        swiperefreshlayout.setColorSchemeResources(R.color.colorPrimary);
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
        return AppNetConfig.DISCOVER_ORIGINAL;
    }

    @Override
    protected void initData(String json, String error) {
        if (TextUtils.isEmpty(json)) {
            Log.e("TAG", "OriginalFragment initData()" + error);
        } else {
            JSONObject jsonObject = JSONObject.parseObject(json);
            Integer code = jsonObject.getInteger("code");
            if (code == 0) {
                setAdapter(JSON.parseObject(json, DiscoverOriginalBean.class).getData());
            } else {
                Log.e("TAG", "OriginalFragment initData()联网失败");
            }
        }
    }

    private void setAdapter(List<DiscoverOriginalBean.DataBean> data) {
        if (adapter == null) {
            adapter = new OriginalFragmentAdapter(mContext, data);
        } else {
            adapter.refresh(data);
            adapter.notifyDataSetChanged();
            isRefresh = false;
        }
        if (!isRefresh) {
            recyclerview.setAdapter(adapter);
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        }
    }


}
