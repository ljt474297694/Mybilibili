package com.atguigu.mybilibili.view;

/**
 * Created by 李金桐 on 2017/4/1.
 * QQ: 474297694
 * 功能: xxxx
 */

public interface IGetNetView {
    void onSuccess(String json);

    void onError(String error);

    void showLoading();

    void hideLoading();

    String setUrl();

    boolean isShowView();

}
