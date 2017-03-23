package com.atguigu.mybilibili.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atguigu.mybilibili.utils.NetUtils;
import com.zhy.http.okhttp.request.RequestCall;

import butterknife.ButterKnife;

/**
 * Created by 李金桐 on 2017/3/20.
 * QQ: 474297694
 * 功能: xxxx
 */

public abstract class BaseFragment extends Fragment {

    protected Context mContext;
    private boolean isShow;
    private RequestCall requestCall;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(mContext,setLayoutId(),null);
        ButterKnife.bind(this, view);
        isShow = true;
        return view;
    }

    protected abstract void initListener();

    protected abstract @LayoutRes int setLayoutId();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initNetData();
        initListener();
    }
    public void refresh(){
        initNetData();
    }

    private void initNetData() {
        if(isShow)  showLoad();
        if(!TextUtils.isEmpty(setUrl())) {
            requestCall = NetUtils.getInstance().okhttpUtilsget(setUrl(), new NetUtils.resultJson() {
                @Override
                public void onResponse(String json) {
                    if (isShow) {
                        initData(json, null);
                    }
                }
                @Override
                public void onError(String error) {
                    if (isShow) {
                        initData(null, error);
                    }
                }
            });
        }else{
            initData(null,"url为空无法请求数据");
        }
    }

    protected void showLoad() {
    }


    protected abstract String setUrl();

    public void startActivity(Class clazz) {
        startActivity(new Intent(mContext,clazz));
    }

    protected abstract void initData(String json,String error) ;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(requestCall!=null) {
            requestCall.cancel();
        }
        ButterKnife.unbind(this);
        isShow = false;
    }
}
