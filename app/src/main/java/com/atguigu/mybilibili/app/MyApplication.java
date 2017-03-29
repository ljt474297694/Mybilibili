package com.atguigu.mybilibili.app;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import cn.jpush.android.api.JPushInterface;

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
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
         ZXingLibrary.initDisplayOpinion(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }

    public static  Context getInstance(){
        return mContext;
    }
}
