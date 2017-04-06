package com.atguigu.mybilibili.view;

import java.io.File;

/**
 * Created by 李金桐 on 2017/4/6.
 * QQ: 474297694
 * 功能: xxxx
 */

public interface IDownloadView {

    void onProgress(long progress, long total);

    void onResponse();

    void onFailure(String error);

    File downFile();

}
