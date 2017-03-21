package com.atguigu.mybilibili.fragment;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.mybilibili.bean.DiscoverTagBean;
import com.atguigu.mybilibili.utils.AppNetConfig;

/**
 * Created by 李金桐 on 2017/3/21.
 * QQ: 474297694
 * 功能: xxxx
 */

public class OriginalFragment extends BaseFragment {
    @Override
    protected void initListener() {

    }

    @Override
    protected int setLayoutId() {
        return 0;
    }

    @Override
    protected String setUrl() {
        return AppNetConfig.DISCOVER_ORIGINAL;
    }

    @Override
    protected void initData(String json, String error) {
        if (TextUtils.isEmpty(json)) {
          Log.e("TAG", "OriginalFragment initData()"+error);
        } else {
            JSONObject jsonObject = JSONObject.parseObject(json);
            Integer code = jsonObject.getInteger("code");
            if (code == 0) {
                setAdapter();
            } else {
                Log.e("TAG", "OriginalFragment initData()联网失败");
            }
        }
    }
}
