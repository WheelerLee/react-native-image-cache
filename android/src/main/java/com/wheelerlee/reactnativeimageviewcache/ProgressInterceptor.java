package com.wheelerlee.reactnativeimageviewcache;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ProgressInterceptor implements Interceptor {

    public static final Map<String, ResponseProgressListener> LISTENER_MAP = new HashMap<>();

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response response = chain.proceed(request);
        final String key = request.url().toString();
        return response.newBuilder().body(new OkHttpProgressResponseBody(key, response.body())).build();

    }

    //入注册下载监听
    public static void addListener(String url, ResponseProgressListener listener) {
        LISTENER_MAP.put(url, listener);
    }

    //取消注册下载监听
    public static void removeListener(String url) {
        LISTENER_MAP.remove(url);
    }

}
