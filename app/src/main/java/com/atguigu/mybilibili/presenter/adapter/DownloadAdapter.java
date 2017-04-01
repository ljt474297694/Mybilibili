package com.atguigu.mybilibili.presenter.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.view.activity.DownloadActivity;
import com.atguigu.mybilibili.app.MyApplication;
import com.atguigu.mybilibili.utils.ProgressResponseBody;
import com.atguigu.mybilibili.utils.RetrofitUtils;
import com.atguigu.mybilibili.view.view.MyProgressBar;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by 李金桐 on 2017/3/24.
 * QQ: 474297694
 * 功能: xxxx
 */

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {


    private DownloadActivity mContext;
    private SeekBar seekbar;
    private int seekbarProgress;
    private int downnum;

    public DownloadAdapter(DownloadActivity mContext, SeekBar seekbar) {
        this.mContext = mContext;
        this.seekbar = seekbar;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_download, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.bt_download)
        Button btDownload;
        @Bind(R.id.progresss)
        MyProgressBar progresss;
        @Bind(R.id.tv_progress)
        TextView tvProgress;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            btDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (seekbarProgress == 0) {
                        seekbarProgress = seekbar.getProgress() + 1;
                        Log.e("TAG", "ViewHolder onClick()" + seekbarProgress);
                    }

                    if (downnum >= seekbarProgress) {
                        Toast.makeText(mContext, "同时下载数量过多 无法继续下载", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    downnum++;
                    btDownload.setText("下载中!");
                    btDownload.setEnabled(false);
                    RetrofitUtils.getInstance().download(new File(MyApplication.getInstance().getExternalFilesDir(null), getLayoutPosition() + "x.apk"),
                            new ProgressResponseBody.ProgressListener() {
                                @Override
                                public void onProgress(final long progress, final long total, final boolean done) {
                                    Observable.just(progress, total).buffer(2).observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Observer<List<Long>>() {
                                                @Override
                                                public void onSubscribe(Disposable d) {
                                                }
                                                @Override

                                                public void onNext(List<Long> value) {
                                                    Long progress = value.get(0);
                                                    Long total = value.get(1);
                                                    long l = progress * 100 / total;
                                                    progresss.setProgress((int) l);
                                                    int l1 = (int) (progress * 100);
                                                    String pro = l1 / total + "%";
                                                    String p = RetrofitUtils.formetFileSize(progress);
                                                    String t = RetrofitUtils.formetFileSize(total);
                                                    tvProgress.setText(p + " / " + t + "\t" + pro);
                                                }

                                                @Override
                                                public void onError(Throwable e) {
                                                }
                                                @Override
                                                public void onComplete() {
                                                }
                                            });
                                }

                                @Override
                                public void onResponse() {
                                    downnum--;
                                    btDownload.setText("下载完成!");
                                }

                                @Override
                                public void onFailure(String error) {
                                    downnum--;
                                    btDownload.setText("下载出错!");
                                    btDownload.setEnabled(true);
                                    Log.e("TAG", "ViewHolder onFailure()" + error);
                                }
                            });
                }
            });
        }
    }

    class DoewnLoadBean {

    }

}