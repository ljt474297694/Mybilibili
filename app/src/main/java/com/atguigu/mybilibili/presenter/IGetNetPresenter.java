package com.atguigu.mybilibili.presenter;

/**
 * Created by 李金桐 on 2017/4/1.
 * QQ: 474297694
 * 功能: xxxx
 */

public interface IGetNetPresenter {

    void onSuccess(String json);

    void onError(String error);

    void getDataFromNet();

    void cancelCall();



}
