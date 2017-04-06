package com.atguigu.mybilibili.presenter;

import com.atguigu.mybilibili.model.DownLoadModel;
import com.atguigu.mybilibili.view.IDownloadView;

import java.io.File;

/**
 * Created by 李金桐 on 2017/4/6.
 * QQ: 474297694
 * 功能: xxxx
 */

public class DownLoadPresenter implements IDownLoadPresenter {
    private IDownloadView mIDownloadView;
    private DownLoadModel mDownLoadModel;

    public DownLoadPresenter(IDownloadView iDownloadView) {
        this.mIDownloadView = iDownloadView;
        mDownLoadModel = new DownLoadModel(this);
    }

    @Override
    public void downLoadApk() {
        mDownLoadModel.downLoadApk();
    }

    @Override
    public void onProgress(long progress, long total) {
        mIDownloadView.onProgress(progress, total);
    }

    @Override
    public void onResponse() {
        mIDownloadView.onResponse();
    }

    @Override
    public void onFailure(String error) {
        mIDownloadView.onFailure(error);
    }

    @Override
    public File downFile() {
        return mIDownloadView.downFile();
    }
}
