package com.atguigu.mybilibili.presenter;

/**
 * Created by 李金桐 on 2017/4/5.
 * QQ: 474297694
 * 功能: 需要请求多个网址的回调接口
 */

public interface ResultListener {
    //成功
    void onSuccess(String json);
    //失败
    void onError(String error);
}
