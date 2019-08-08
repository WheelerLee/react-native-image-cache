package com.wheelerlee.reactnativeimageviewcache;

import android.util.Log;

import java.io.IOException;

import javax.annotation.Nullable;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class OkHttpProgressResponseBody extends ResponseBody {

    private final String key;
    private final ResponseBody responseBody;
    private BufferedSource bufferedSource;
    private ResponseProgressListener listener;

    public OkHttpProgressResponseBody(String key, ResponseBody responseBody) {
        this.key = key;
        this.responseBody = responseBody;
        this.listener = ProgressInterceptor.LISTENER_MAP.get(key);
    }

    @Nullable
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
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {

            long totalBytesRead = 0L;
            int currentProgress;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                long fullLength = responseBody.contentLength();
                if (bytesRead == -1) {
                    totalBytesRead = fullLength;
                } else {
                    totalBytesRead += bytesRead;
                }
                int progress = (int) (100f * totalBytesRead / fullLength);
                if (listener != null && progress != currentProgress) {
                    listener.update(totalBytesRead, fullLength, progress);
                }
                if (listener != null && totalBytesRead == fullLength) {
                    listener = null;
                }

                currentProgress = progress;
                return bytesRead;
            }
        };
    }

}
