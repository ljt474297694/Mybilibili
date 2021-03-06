package com.atguigu.mybilibili.utils;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by 李金桐 on 2017/3/24.
 * QQ: 474297694
 * 功能: xxxx
 */

public class ProgressResponseBody extends ResponseBody {

    private final ResponseBody responseBody;
    private final ProgressListener listener;
    private BufferedSource bufferedSource;

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener listener) {
        this.responseBody = responseBody;
        this.listener = listener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (null == bufferedSource) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                Observable.just(totalBytesRead, responseBody.contentLength()).buffer(2)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Long>>() {
                            @Override
                            public void accept(List<Long> value) throws Exception {
                                listener.onProgress(value.get(0), value.get(1));
                            }
                        });
                return bytesRead;
            }
        };
    }

    public interface ProgressListener {
        /**
         * @param progress 已经下载或上传字节数
         * @param total    总字节数
         */
        void onProgress(long progress, long total);

        void onResponse();

        void onFailure(String error);
    }
}