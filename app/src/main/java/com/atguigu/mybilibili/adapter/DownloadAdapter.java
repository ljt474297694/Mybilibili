package com.atguigu.mybilibili.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.activity.DownloadActivity;
import com.atguigu.mybilibili.app.MyApplication;
import com.atguigu.mybilibili.utils.ProgressResponseBody;
import com.atguigu.mybilibili.utils.RetrofitUtils;
import com.atguigu.mybilibili.view.MyProgressBar;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李金桐 on 2017/3/24.
 * QQ: 474297694
 * 功能: xxxx
 */

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {
    private static DownloadActivity mContext;
    public DownloadAdapter(DownloadActivity mContext) {
        this.mContext = mContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_download, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.progresss.setProgress(0);
    }

    @Override
    public int getItemCount() {
        return 10;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
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
                    btDownload.setText("下载中!");
                    btDownload.setEnabled(false);
                    RetrofitUtils.getInstance().download(new File(MyApplication.getInstance().getExternalFilesDir(null), getLayoutPosition() + "x.apk"),
                            new ProgressResponseBody.ProgressListener() {
                                @Override
                                public void onProgress(final long progress, final long total, final boolean done) {
                                    mContext.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            long l = progress*100 / total;
                                            progresss.setProgress((int) l);
                                            String pro = (int) progress * 100 / (int) total + "%";
                                            String p = RetrofitUtils.formetFileSize(progress);
                                            String t = RetrofitUtils.formetFileSize(total);
                                            tvProgress.setText(p + " / " + t + "\t" + pro);
                                        }
                                    });

                                }
                                @Override
                                public void onResponse() {
                                    btDownload.setText("下载完成!");
                                }

                                @Override
                                public void onFailure(String error) {
                                    btDownload.setText("下载出错!");
                                    btDownload.setEnabled(true);
                                    Log.e("TAG", "ViewHolder onFailure()" + error);
                                }
                            });
                }
            });
        }
    }


}
