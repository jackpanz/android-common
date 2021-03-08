package com.github.jackpanz.android.common.net;

import android.app.Activity;

public class SimpleCallback<T> {

    public Activity activity;

    public SimpleCallback() {

    }

    public SimpleCallback(Activity activity) {
        this.activity = activity;
    }

    public void onCatchException(Exception e) {

    }

    public void onCatchException(int index,Exception e) {

    }

    public void onSendData(String key, Object object) {

    }

    public void onSuccess(int index, T object) {

    }

    public void onFinally(Object ... values) {

    }

    public void onFinally() {

    }

    public void onFinally(int i) {

    }


}
