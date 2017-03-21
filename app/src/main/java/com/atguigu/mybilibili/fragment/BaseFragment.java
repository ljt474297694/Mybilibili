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

import butterknife.ButterKnife;

/**
 * Created by 李金桐 on 2017/3/20.
 * QQ: 474297694
 * 功能: xxxx
 */

public abstract class BaseFragment extends Fragment {

    protected Context mContext;

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
        initListener();
        return view;
    }

    protected abstract void initListener();

    protected abstract @LayoutRes int setLayoutId();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(!TextUtils.isEmpty(setUrl())) {
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
        }else{
            initData(null,"url为空无法请求数据");
        }


    }

    protected abstract String setUrl();

    public void startActivity(Class clazz) {
        startActivity(new Intent(mContext,clazz));
    }

    protected abstract void initData(String jaon,String error) ;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
