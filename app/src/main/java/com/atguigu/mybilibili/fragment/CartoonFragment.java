package com.atguigu.mybilibili.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.adapter.CartoonAdapter;
import com.atguigu.mybilibili.bean.CartoonBean;
import com.atguigu.mybilibili.utils.AppNetConfig;

import java.util.List;

import butterknife.Bind;

/**
 * Created by 李金桐 on 2017/3/21.
 * QQ: 474297694
 * 功能: 动漫
 */

public class CartoonFragment extends BaseFragment {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;

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
        return R.layout.fragment_cartoon;
    }

    @Override
    protected void showLoad() {
        super.showLoad();
        swiperefreshlayout.setColorSchemeResources(R.color.colorPrimary);
        swiperefreshlayout.setRefreshing(true);
    }

    @Override
    protected String setUrl() {
        return AppNetConfig.CARTOON;
    }

    @Override
    protected void initData(String json, String error) {
        swiperefreshlayout.setRefreshing(false);
        if (TextUtils.isEmpty(json)) {
            Log.e("TAG", "CartoonFragment initData()" + error);
        } else {
            JSONObject jsonObject = JSONObject.parseObject(json);
            Integer code = jsonObject.getInteger("code");
            if (code == 0) {
                setAdapter(JSON.parseObject(json, CartoonBean.class).getResult().getPrevious().getList());
            } else {
                Log.e("TAG", "CartoonFragment initData()联网失败");
            }
        }
    }

    private void setAdapter(List<CartoonBean.ResultBean.PreviousBean.ListBean> list) {
        recyclerview.setAdapter(new CartoonAdapter(mContext,list));

        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
    }

}
