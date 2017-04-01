package com.atguigu.mybilibili.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.bean.PartitionBean;
import com.atguigu.mybilibili.presenter.adapter.PartitionAdapter;
import com.atguigu.mybilibili.utils.AppNetConfig;
import com.atguigu.mybilibili.view.base.BaseFragment;

import java.util.List;

import butterknife.Bind;

/**
 * Created by 李金桐 on 2017/3/21.
 * QQ: 474297694
 * 功能: 分区
 */

public class PartitionFragment extends BaseFragment {
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    private PartitionAdapter adapter;

    @Override
    protected void initListener() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_partition;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public String setUrl() {
        return AppNetConfig.PARTITION;
    }

    @Override
    protected void initData(String json, String error) {
        if (TextUtils.isEmpty(json)) {
            Log.e("TAG", "PartitionFragment initData()"+error);
        } else {
            JSONObject jsonObject = JSONObject.parseObject(json);
            Integer code = jsonObject.getInteger("code");
            if (code == 0) {
                setAdapter(JSON.parseObject(json, PartitionBean.class).getData());
            } else {
                Log.e("TAG", "PartitionFragment initData()联网失败");
            }
        }
    }

    private void setAdapter(List<PartitionBean.DataBean> data) {

        if(adapter==null) {
            adapter = new PartitionAdapter(mContext, data);
        }else{
            adapter.refresh(data);
            adapter.notifyDataSetChanged();
        }
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
    }

}
