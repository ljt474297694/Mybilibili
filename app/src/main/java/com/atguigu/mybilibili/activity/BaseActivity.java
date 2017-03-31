package com.atguigu.mybilibili.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.atguigu.mybilibili.utils.NetUtils;
import com.zhy.http.okhttp.request.RequestCall;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private RequestCall requestCall;
    private boolean isShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());

        ButterKnife.bind(this);
        isShow = true;
        initView();
        initDataNet();
        initListener();
    }

    private void initDataNet() {
        if (TextUtils.isEmpty(setUrl())) {
            initData(null, "url为空无法请求数据");
            return;
        }
        requestCall = NetUtils.getInstance().okhttpUtilsget(setUrl(), new NetUtils.resultJson() {
            @Override
            public void onResponse(String json) {
                if (isShow) initData(json, null);
            }

            @Override
            public void onError(String error) {
                if (isShow) initData(null, error);
            }
        });
    }

    @Override
    protected void onDestroy() {
        isShow = false;
        if (requestCall != null) {
            requestCall.cancel();
        }
        super.onDestroy();
    }

    protected void refresh() {
        initDataNet();
    }

    protected abstract String setUrl();

    protected abstract void initListener();

    protected abstract void initView();

    protected abstract void initData(String json, String error);

    protected abstract
    @LayoutRes
    int setLayoutId();


    protected void startActivity(Class activityClazz) {
        startActivity(new Intent(this, activityClazz));
    }
}
