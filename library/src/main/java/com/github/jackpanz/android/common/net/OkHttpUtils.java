package com.github.jackpanz.android.common.net;

import android.accounts.NetworkErrorException;
import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils {

    final static private String TAG = OkHttpUtils.class.getSimpleName();

    public static OkHttpUtils okHttpUtils = null;

    public OkHttpClient okHttpClient;

    public static boolean debug = false;

    private OkHttpUtils(int connectTimeout, int writeTimeout, int readTimeout) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if (debug) {
                            Log.i(TAG, request.url().toString());
                            if (request.body() instanceof FormBody) {
                                FormBody formBody = (FormBody) request.body();
                                for (int i = 0; i < formBody.size(); i++) {
                                    Log.i(TAG, formBody.name(i) + "=" + formBody.value(i));
                                }
                            }
                        }
                        return chain.proceed(request);
                    }
                });

        okHttpClient = builder.build();
    }


    public static OkHttpUtils getInstance(int connectTimeout, int writeTimeout, int readTimeout) {
        return new OkHttpUtils(connectTimeout, writeTimeout, readTimeout);
    }

    public static OkHttpUtils getInstance() {
        if (okHttpUtils == null) {
            okHttpUtils = new OkHttpUtils(15, 15, 15);
        }
        return okHttpUtils;
    }

    public String syncPostJson(String url, String json) throws IOException {
        return syncPostJson(url, json, "application/json;charset=utf-8", null);
    }

    public String syncPostJson(String url, String json, String mediaType) throws IOException {
        return syncPostJson(url, json, mediaType, null);
    }

    public String syncPostJson(String url, String json, Map<String, String> headers) throws IOException {
        return syncPostJson(url, json, "application/json;charset=utf-8", headers);
    }

    public String syncPostJson(String url, String json, String mediaType, Map<String, String> headers) throws IOException {
        Request.Builder builder = new Request.Builder();

        if (headers != null && headers.size() > 0) {
            Headers.Builder headerBuilder = new Headers.Builder();
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                headerBuilder.add(entry.getKey(), entry.getValue());
            }
            builder.headers(headerBuilder.build());
        }

        MediaType type = MediaType.Companion.parse(mediaType);
        RequestBody requestBody = RequestBody.Companion.create(json, type);

        Request request = builder
                .post(requestBody)
                .url(url)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);
        String result = response.body().string();
        if (debug) {
            Log.i(TAG, result);
        }
        return result;
    }

    public String syncPostForm(String url) throws IOException {
        return syncPostForm(url, null, null);
    }

    public String syncPostForm(String url, Map<String, String> params) throws IOException {
        return syncPostForm(url, params, null);
    }

    public String syncPostForm(String url, Map<String, String> params, Map<String, String> headers) throws IOException {

        Request.Builder builder = new Request.Builder();

        if (headers != null && headers.size() > 0) {
            Headers.Builder headerBuilder = new Headers.Builder();
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                headerBuilder.add(entry.getKey(), entry.getValue());
            }
            builder.headers(headerBuilder.build());
        }

        if (params != null && params.size() > 0) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formBodyBuilder.add(entry.getKey(), entry.getValue());
            }
            builder.post(formBodyBuilder.build());
        }

        Request request = builder
                .url(url)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);
        String result = response.body().string();
        if (debug) {
            Log.i(TAG, result);
        }
        return result;
    }

}
