package com.atguigu.mybilibili.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.atguigu.mybilibili.utils.NetUtils;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        ButterKnife.bind(this);
        initView();
        initDataNet();
        initListener();
    }

    private void initDataNet(){
        if(TextUtils.isEmpty(setUrl())) {
            initData(null,"url为空无法请求数据");
           return ;
        }
        NetUtils.getInstance().okhttpUtilsget(setUrl(), new NetUtils.resultJson() {
            @Override
            public void onResponse(String json) {
                initData(json,null);
            }

            @Override
            public void onError(String error) {
                initData(null,error);
            }
        });
    }
    protected void refresh(){
        initDataNet();
    }
    protected abstract String setUrl();

    protected abstract void initListener();

    protected abstract void initView();

    protected abstract void initData(String jaon,String error);

    protected abstract @LayoutRes int setLayoutId();


    protected void startActivity(Class activityClazz){
        startActivity(new Intent(this,activityClazz));
    }
}
