package com.atguigu.mybilibili.model;

import com.atguigu.mybilibili.presenter.IDownLoadPresenter;
import com.atguigu.mybilibili.utils.ProgressResponseBody;
import com.atguigu.mybilibili.utils.RetrofitNetUtils;

/**
 * Created by 李金桐 on 2017/4/6.
 * QQ: 474297694
 * 功能: xxxx
 */

public class DownLoadModel implements IDownLoadModel {
    private IDownLoadPresenter mIDownLoadPresenter;

    public DownLoadModel(IDownLoadPresenter iDownLoadPresenter) {
        this.mIDownLoadPresenter = iDownLoadPresenter;
    }

    @Override
    public void downLoadApk() {
        RetrofitNetUtils.getInstance().download(mIDownLoadPresenter.downFile(), new ProgressResponseBody.ProgressListener() {
            @Override
            public void onProgress(long progress, long total) {
                if (mIDownLoadPresenter != null) mIDownLoadPresenter.onProgress(progress, total);
            }

            @Override
            public void onResponse() {
                if (mIDownLoadPresenter != null) mIDownLoadPresenter.onResponse();
            }

            @Override
            public void onFailure(String error) {
                if (mIDownLoadPresenter != null) mIDownLoadPresenter.onFailure(error);
            }
        });
    }
}
