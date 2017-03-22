package com.atguigu.mybilibili.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.adapter.PartitionAdapter;
import com.atguigu.mybilibili.bean.PartitionBean;
import com.atguigu.mybilibili.utils.AppNetConfig;

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

    @Override
    protected void initListener() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_partition;
    }

    @Override
    protected String setUrl() {
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
        recyclerview.setAdapter(new PartitionAdapter(mContext,data));
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
    }

}
