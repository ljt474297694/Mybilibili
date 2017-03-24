package com.atguigu.mybilibili.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.activity.DownloadActivity;
import com.atguigu.mybilibili.app.MyApplication;
import com.atguigu.mybilibili.utils.ProgressResponseBody;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * Created by 李金桐 on 2017/3/24.
 * QQ: 474297694
 * 功能: xxxx
 */

public class DownloadAdapter extends RecyclerView.Adapter {
    private static Context mContext;
    public static List<Call<ResponseBody>> list;

    public DownloadAdapter(Context mContext) {
        this.mContext = mContext;
        list = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_download, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.bt_download)
        Button btDownload;
        @Bind(R.id.progressbar)
        ProgressBar progressbar;
        @Bind(R.id.tv_progress)
        TextView tvProgress;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            btDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    download(new File(MyApplication.getInstance().getExternalFilesDir(null), getLayoutPosition() + "x.apk"),
                            new ProgressResponseBody.ProgressListener() {
                                @Override
                                public void onProgress(final long progress, final long total, boolean done) {
                                    ((DownloadActivity) mContext).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (progressbar.getMax() != total) {
                                                progressbar.setMax((int) total);
                                            }
                                            progressbar.setProgress((int) progress);
                                            String pro = (int) progress * 100 / (int) total + "%";
                                            String p = formetFileSize(progress);
                                            String t = formetFileSize(total);
                                            tvProgress.setText(p+" / "+t+"\t"+pro);
                                        }
                                    });
                                }
                            });
                }
            });
        }
    }

    private static void download(final File file, final ProgressResponseBody.ProgressListener progressListener) {
        Retrofit.Builder retrofit = new Retrofit.Builder()
                .baseUrl("http://47.93.118.241:8081/");
        //使用OkHttpClient的拦截器去拦截Response  将自定义的ProgressReponseBody设置进去 得到监听的状态
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Response orginalResponse = chain.proceed(chain.request());

                        return orginalResponse.newBuilder()
                                .body(new ProgressResponseBody(orginalResponse.body(), progressListener))
                                .build();
                    }
                })
                .build();
        //使用OkHttpClient设置到Retrofit中 并创建任务
        DownLoadApk api = retrofit.client(client)
                .build().create(DownLoadApk.class);


        //开启任务 开始请求

        Call<ResponseBody> call = api.downLoadApk();
        list.add(call);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                InputStream is = null;
                FileOutputStream fos = null;
                BufferedInputStream bis = null;
                try {
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        fos.flush();
                    }
                    list.remove(file);
//                    Intent intent = new Intent("android.intent.action.INSTALL_PACKAGE");
//                    intent.setData(Uri.parse("file:" + file.getAbsolutePath()));
//                    startActivity(intent);
//                    Intent intent = new Intent();
//                    intent.setAction(android.content.Intent.ACTION_VIEW);
//                    intent.setDataAndType(Uri.fromFile(file),
//                            "application/vnd.android.package-archive");
//                    startActivity(intent);

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG", "DownloadAdapter onFailure()" + t.getMessage());
            }
        });
    }

    public interface DownLoadApk {
        @GET("P2PInvest/app_new.apk")
        Call<ResponseBody> downLoadApk();
    }

    public static String formetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
}
