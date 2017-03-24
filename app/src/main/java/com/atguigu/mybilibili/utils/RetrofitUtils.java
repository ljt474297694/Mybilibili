package com.atguigu.mybilibili.utils;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

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
 * 功能: RetrofitUtils
 */

public class RetrofitUtils {
    private static final RetrofitUtils retrofitUtils = new RetrofitUtils();

    private ProgressResponseBody.ProgressListener listener;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what) {
                case  0:
                    listener.onResponse();
                    break;
                case  1:
                    listener.onFailure((String)msg.obj);
                    break;
            }
        }
    };
    private RetrofitUtils() {
    }

    public static RetrofitUtils getInstance() {
        return retrofitUtils;
    }

    public  void download(final File file, final ProgressResponseBody.ProgressListener progressListener) {
        this.listener = progressListener;
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
                   handler.sendEmptyMessage(0);
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
                    Message message = new Message();
                    message.what=1;
                    message.obj = e.getMessage();
                    handler.sendMessage(message);
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
                Message message = new Message();
                message.what=1;
                message.obj = t.getMessage();
                handler.sendMessage(message);
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
