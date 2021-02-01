package com.github.jackpanz.android.common.net;

import android.accounts.NetworkErrorException;
import android.util.Log;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtils {

    final static private String TAG = OkHttpUtils.class.getSimpleName();

    static OkHttpUtils netUtil = null;

    public OkHttpClient sOkHttpClient;

    boolean debug = false;

    private OkHttpUtils() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
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

        sOkHttpClient = builder.build();
    }

    public static OkHttpUtils getInstance() {
        if (netUtil == null) {
            netUtil = new OkHttpUtils();
        }
        return netUtil;
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

        Response response = sOkHttpClient.newCall(request).execute();
        if (!response.isSuccessful())
            throw new IOException("Unexpected code " + response);
        String result = response.body().string();
        if (debug) {
            Log.i(TAG, result);
        }
        return result;
    }

}
