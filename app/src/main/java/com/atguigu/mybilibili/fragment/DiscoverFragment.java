package com.atguigu.mybilibili.fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.bean.DiscoverTagBean;
import com.atguigu.mybilibili.utils.AppNetConfig;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by 李金桐 on 2017/3/21.
 * QQ: 474297694
 * 功能: 发现
 */

public class DiscoverFragment extends BaseFragment {
    @Bind(R.id.flowlayout)
    TagFlowLayout flowlayout;

    @Override
    protected void initListener() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    protected String setUrl() {
        return AppNetConfig.DISCOVER_TAG;
    }

    @Override
    protected void initData(String json, String error) {
        if (TextUtils.isEmpty(json)) {
            Log.e("TAG", "DiscoverFragment initData()" + error);
        } else {
            JSONObject jsonObject = JSONObject.parseObject(json);
            Integer code = jsonObject.getInteger("code");
            if (code == 0) {
                setAdapter(JSON.parseObject(json, DiscoverTagBean.class).getData().getList());
            } else {
                Log.e("TAG", "DiscoverFragment initData()联网失败");
            }
        }

    }

    private void setAdapter(List<DiscoverTagBean.DataBean.ListBean> list) {

        List<String> data = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            data.add(list.get(i).getKeyword());
        }
        flowlayout.setAdapter(new TagAdapter<String>(data) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                TextView view = (TextView) View.inflate(getActivity(), R.layout.tag_text, null);
                view.setText(o);
                return view;
            }
        });
    }


}
