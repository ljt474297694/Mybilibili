package com.atguigu.mybilibili.model;

import com.atguigu.mybilibili.presenter.ResultListener;

/**
 * Created by 李金桐 on 2017/4/1.
 * QQ: 474297694
 * 功能: xxxx
 */

public interface IGetNetModel {

    void getDataFromNet(String utl);

    void cancelCall();
    void getDataFromNet(String utl, ResultListener listener);
}
