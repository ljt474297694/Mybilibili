package com.atguigu.mybilibili.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.activity.HuatiActivity;
import com.atguigu.mybilibili.activity.HuoDongActivity;
import com.atguigu.mybilibili.activity.MailActivity;
import com.atguigu.mybilibili.activity.OriginalActivity;
import com.atguigu.mybilibili.activity.SearchActivity;
import com.atguigu.mybilibili.bean.DiscoverTagBean;
import com.atguigu.mybilibili.utils.AppNetConfig;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.wyt.searchbox.SearchFragment;
import com.wyt.searchbox.custom.IOnSearchClickListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by 李金桐 on 2017/3/21.
 * QQ: 474297694
 * 功能: 发现
 */

public class DiscoverFragment extends BaseFragment {
    @Bind(R.id.flowlayout)
    TagFlowLayout flowlayout;
    @Bind(R.id.tv_xingququan)
    TextView tvXingququan;
    @Bind(R.id.tv_huatizhongxin)
    TextView tvHuatizhongxin;
    @Bind(R.id.tv_huodongzhongxin)
    TextView tvHuodongzhongxin;
    @Bind(R.id.tv_yuanchuang)
    TextView tvYuanchuang;
    @Bind(R.id.tv_quanqu)
    TextView tvQuanqu;
    private SearchFragment searchFragment;

    @Override
    protected void initListener() {
        searchFragment = SearchFragment.newInstance();
        searchFragment.setOnSearchClickListener(new IOnSearchClickListener() {
            @Override
            public void OnSearchClick(String keyword) {
                startActivity(new Intent(mContext, SearchActivity.class).putExtra("search", keyword));
            }
        });
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
        flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String str = ((TextView) view).getText().toString().trim();
                startActivity(new Intent(mContext, SearchActivity.class).putExtra("search", str));
                return true;
            }
        });
    }


    @OnClick({R.id.tv_mail,R.id.iv_scan, R.id.tv_search, R.id.tv_xingququan, R.id.tv_huatizhongxin, R.id.tv_huodongzhongxin, R.id.tv_yuanchuang, R.id.tv_quanqu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_mail:
//                Toast.makeText(mContext, "购物车", Toast.LENGTH_SHORT).show();
                startActivity(MailActivity.class);
                break;

            case R.id.iv_scan:
//                Toast.makeText(mContext, "二维码", Toast.LENGTH_SHORT).show();

                if (ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions((Activity) mContext,
                            new String[]{Manifest.permission.CAMERA},
                            1);
                }else{
                    Intent intent = new Intent(mContext, CaptureActivity.class);
                    startActivityForResult(intent, 0);
                }

                break;
            case R.id.tv_search:
                searchFragment.show(getChildFragmentManager(),SearchFragment.TAG);

                break;
            case R.id.tv_xingququan:
                break;
            case R.id.tv_huatizhongxin:
                startActivity(HuatiActivity.class);
                break;
            case R.id.tv_huodongzhongxin:
                startActivity(HuoDongActivity.class);
                break;
            case R.id.tv_yuanchuang:
                startActivity(OriginalActivity.class);
                break;
            case R.id.tv_quanqu:
                startActivity(OriginalActivity.class);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(mContext, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(mContext, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
