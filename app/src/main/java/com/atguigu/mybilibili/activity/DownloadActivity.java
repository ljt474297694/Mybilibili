package com.atguigu.mybilibili.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.atguigu.mybilibili.R;
import com.atguigu.mybilibili.utils.ProgressResponseBody;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;

public class DownloadActivity extends BaseActivity {


    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.nestedscrollview)
    NestedScrollView nestedscrollview;
    @Bind(R.id.activity_download)
    CoordinatorLayout activityDownload;
    @Bind(R.id.bt_download)
    Button btDownload;
    @Bind(R.id.progressbar)
    ProgressBar progressbar;
    @Bind(R.id.tv_progress)
    TextView tvProgress;
    private Call<ResponseBody> call;

    @Override
    protected String setUrl() {
        return null;
    }

    @Override
    protected void initListener() {
        btDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回键可用
    }

    @Override
    protected void initData(String json, String error) {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_download;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }






    private void download() {
        Retrofit.Builder retrofit = new Retrofit.Builder()
                .baseUrl("http://47.93.118.241:8081/");

        //使用OkHttpClient的拦截器去拦截Response  将自定义的ProgressReponseBody设置进去 得到监听的状态
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Response orginalResponse = chain.proceed(chain.request());

                        return orginalResponse.newBuilder()
                                .body(new ProgressResponseBody(orginalResponse.body(), new ProgressResponseBody.ProgressListener() {
                                    @Override
                                    public void onProgress(final long progress, final long total, boolean done) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if(progressbar.getMax()!=total) {
                                                    progressbar.setMax((int) total);
                                                }
                                                progressbar.setProgress((int) progress);
                                                String pro =   (int)progress*100 / (int)total+"%";
                                                tvProgress.setText(pro);
                                           }
                                        });
                                    }
                                }))
                                .build();
                    }
                })
                .build();
        //使用OkHttpClient设置到Retrofit中 并创建任务
        DownLoadApk api = retrofit.client(client)
                .build().create(DownLoadApk.class);

        call = api.DownLoadApk();
        //开启任务 开始请求
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                InputStream is = null;
                FileOutputStream fos =null;
                BufferedInputStream bis = null;
                try {
                     is = response.body().byteStream();
                    File file = new File(DownloadActivity.this.getExternalFilesDir(null), "xxx.apk");
                     fos = new FileOutputStream(file);
                     bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        fos.flush();
                    }

//                    Intent intent = new Intent("android.intent.action.INSTALL_PACKAGE");
//                    intent.setData(Uri.parse("file:" + file.getAbsolutePath()));
//                    startActivity(intent);
                    Intent intent = new Intent();
                    intent.setAction(android.content.Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file),
                            "application/vnd.android.package-archive");
                    startActivity(intent);

                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    if(bis!=null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(fos!=null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(is!=null) {
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

            }
        });
    }
    public  interface DownLoadApk{
        @GET("P2PInvest/app_new.apk")
        Call<ResponseBody> DownLoadApk();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        call.cancel();
    }
}
