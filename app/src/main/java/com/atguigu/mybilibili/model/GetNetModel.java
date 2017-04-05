package com.atguigu.mybilibili.model;

import com.atguigu.mybilibili.presenter.IGetNetPresenter;
import com.atguigu.mybilibili.presenter.ResultListener;
import com.atguigu.mybilibili.utils.NetUtils;
import com.zhy.http.okhttp.request.RequestCall;

/**
 * Created by 李金桐 on 2017/4/1.
 * QQ: 474297694
 * 功能: xxxx
 */

public class GetNetModel implements IGetNetModel {
    private IGetNetPresenter mIGetNetPresenter;
    private RequestCall requestCall;

    public GetNetModel(IGetNetPresenter iGetNetPresenter) {
        this.mIGetNetPresenter = iGetNetPresenter;
    }

    @Override
    public void getDataFromNet(String utl) {
        requestCall = NetUtils.getInstance().okhttpUtilsget(utl, new NetUtils.resultJson() {
            @Override
            public void onResponse(String json) {
                if (mIGetNetPresenter != null) mIGetNetPresenter.onSuccess(json);
            }
            @Override
            public void onError(String error) {
                if (mIGetNetPresenter != null) mIGetNetPresenter.onError(error);
            }
        });
    }

    @Override
    public void cancelCall() {
        if (requestCall != null) {
            requestCall.cancel();
        }
    }

    @Override
    public void getDataFromNet(String utl, final ResultListener listener) {
        requestCall = NetUtils.getInstance().okhttpUtilsget(utl, new NetUtils.resultJson() {
            @Override
            public void onResponse(String json) {
                if (listener != null) listener.onSuccess(json);
            }
            @Override
            public void onError(String error) {
                if (listener != null) listener.onError(error);
            }
        });
    }
}
