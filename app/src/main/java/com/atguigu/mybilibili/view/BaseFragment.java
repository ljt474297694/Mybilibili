package com.atguigu.mybilibili.view;

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

import com.atguigu.mybilibili.presenter.GetNetPresenter;

import butterknife.ButterKnife;

/**
 * Created by 李金桐 on 2017/3/20.
 * QQ: 474297694
 * 功能: xxxx
 */

public abstract class BaseFragment extends Fragment implements IGetNetView {

    protected Context mContext;
    private boolean isShow;
    private GetNetPresenter mGetNetPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(mContext, setLayoutId(), null);
        ButterKnife.bind(this, view);
        isShow = true;
        mGetNetPresenter = new GetNetPresenter(this);
        return view;
    }

    protected abstract void initListener();

    protected abstract
    @LayoutRes
    int setLayoutId();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initNetData();
        initListener();
    }

    protected abstract void initView();

    public void refresh() {
        initNetData();
    }

    private void initNetData() {
        if (TextUtils.isEmpty(setUrl())) {
            initData(null, "url为空无法请求数据");
            return;
        }
        mGetNetPresenter.getDataFromNet();
    }

    @Override
    public void onSuccess(String json) {
        initData(json, null);
    }

    @Override
    public void onError(String error) {
        initData(null, error);
    }

    @Override
    public abstract void showLoading();

    @Override
    public abstract void hideLoading();

    public abstract String setUrl();

    @Override
    public boolean isShowView() {
        return isShow;
    }

    public void startActivity(Class clazz) {
        startActivity(new Intent(mContext, clazz));
    }

    protected abstract void initData(String json, String error);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isShow = false;
        mGetNetPresenter.cancelCall();
        ButterKnife.unbind(this);
    }
}
