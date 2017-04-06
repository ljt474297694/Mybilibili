package com.atguigu.mybilibili.presenter;

import com.atguigu.mybilibili.model.GetNetModel;
import com.atguigu.mybilibili.model.IGetNetModel;
import com.atguigu.mybilibili.view.IGetNetView;

/**
 * Created by 李金桐 on 2017/4/1.
 * QQ: 474297694
 * 功能: xxxx
 */

public class GetNetPresenter implements IGetNetPresenter {

    private IGetNetView mIGetNetView;
    private IGetNetModel mIGetNetModel;

    public GetNetPresenter(IGetNetView iGetNetView) {
        this.mIGetNetView = iGetNetView;
        mIGetNetModel = new GetNetModel(this);
    }

    @Override
    public void onSuccess(String json) {
        if (mIGetNetView.isShowView()) {
            mIGetNetView.hideLoading();
            mIGetNetView.onSuccess(json);
        }
    }

    @Override
    public void onError(String error) {
        if (mIGetNetView.isShowView()) {
            mIGetNetView.hideLoading();
            mIGetNetView.onError(error);
        }
    }

    @Override
    public void getDataFromNet() {
        if (mIGetNetView.isShowView()) {
            mIGetNetView.showLoading();
            mIGetNetModel.getDataFromNet(mIGetNetView.setUrl());
        }
    }

    @Override
    public void cancelCall() {
        mIGetNetModel.cancelCall();
    }

    @Override
    public void getDataFromNet(String url, ResultListener listener) {
        mIGetNetModel.getDataFromNet(url, listener);
    }
}
