package com.atguigu.mybilibili.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by 李金桐 on 2017/3/21.
 * QQ: 474297694
 * 功能: xxxx
 */

public class MyApplication extends Application {

    private static  Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static  Context getInstance(){
        return mContext;
    }
}
