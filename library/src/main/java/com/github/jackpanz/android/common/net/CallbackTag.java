package com.github.jackpanz.android.common.net;

public class CallbackTag {

    public SimpleCallback simpleCallback;

    public CallbackTag(SimpleCallback simpleCallback) {
        this.simpleCallback = simpleCallback;
    }

    public void sendData(String key, Object object) {
        if (simpleCallback != null && simpleCallback.activity != null) {
            simpleCallback.activity.runOnUiThread(() -> {
                simpleCallback.onSendData(key, object);
            });
        }
    }

    public void catchException(Exception e) {
        if (simpleCallback != null && simpleCallback.activity != null) {
            simpleCallback.activity.runOnUiThread(() -> {
                simpleCallback.onCatchException(e);
            });
        }
    }

    public void catchException(int index, Exception e) {
        if (simpleCallback != null && simpleCallback.activity != null) {
            simpleCallback.activity.runOnUiThread(() -> {
                simpleCallback.onCatchException(index, e);
            });
        }
    }

    public void success(int index, Object object) {
        if (simpleCallback != null && simpleCallback.activity != null) {
            simpleCallback.activity.runOnUiThread(() -> {
                simpleCallback.onSuccess(index, object);
            });
        }
    }

//    public void finallyAll() {
//        if (simpleCallback != null && simpleCallback.activity != null) {
//            simpleCallback.activity.runOnUiThread(() -> {
//                simpleCallback.onFinally();
//            });
//        }
//    }

    public void finallyAll(Object [] values) {
        if (simpleCallback != null && simpleCallback.activity != null) {
            simpleCallback.activity.runOnUiThread(() -> {
                simpleCallback.onFinally(values);
                simpleCallback.onFinally();
            });
        }
    }

    public void finallyBy(int index) {
        if (simpleCallback != null && simpleCallback.activity != null) {
            simpleCallback.activity.runOnUiThread(() -> {
                simpleCallback.onFinally(index);
            });
        }
    }

}
