package com.atguigu.mybilibili.model;

import com.atguigu.mybilibili.presenter.IGetNetPresenter;
import com.atguigu.mybilibili.presenter.ResultListener;
import com.atguigu.mybilibili.utils.NetUtils;
import com.zhy.http.okhttp.request.RequestCall;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李金桐 on 2017/4/1.
 * QQ: 474297694
 * 功能: xxxx
 */

public class GetNetModel implements IGetNetModel {
    private IGetNetPresenter mIGetNetPresenter;
    private HashMap<String, RequestCall> map;

    public GetNetModel(IGetNetPresenter iGetNetPresenter) {
        this.mIGetNetPresenter = iGetNetPresenter;
        map = new HashMap<>();
    }

    @Override
    public void getDataFromNet(final String url) {
        map.put(url, NetUtils.getInstance().okhttpUtilsget(url, new NetUtils.resultJson() {
            @Override
            public void onResponse(String json) {
                if (mIGetNetPresenter != null) mIGetNetPresenter.onSuccess(json);
                map.remove(url);
            }

            @Override
            public void onError(String error) {
                if (mIGetNetPresenter != null) mIGetNetPresenter.onError(error);
                map.remove(url);
            }
        }));
    }

    @Override
    public void cancelCall() {
        if (map != null) {
            for (Map.Entry<String, RequestCall> entry : map.entrySet()) {
                entry.getValue().cancel();
            }
        }
    }

    @Override
    public void getDataFromNet(final String url, final ResultListener listener) {
        map.put(url, NetUtils.getInstance().okhttpUtilsget(url, new NetUtils.resultJson() {
            @Override
            public void onResponse(String json) {
                if (listener != null) listener.onSuccess(json);
                map.remove(url);
            }

            @Override
            public void onError(String error) {
                if (listener != null) listener.onError(error);
                map.remove(url);
            }
        }));
    }
}
