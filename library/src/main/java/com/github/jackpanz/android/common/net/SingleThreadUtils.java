package com.github.jackpanz.android.common.net;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadUtils {

    private static ExecutorService mExecutor;

    public static ExecutorService getExecutor() {
        if (mExecutor == null) {
            synchronized ((SingleThreadUtils.class)) {
                if (mExecutor == null) {
                    mExecutor = Executors.newSingleThreadExecutor();
                }
            }
        }
        return mExecutor;
    }

    public static void executeUI(SimpleCallback callback, NetRunnable... myRunnable) {
        // new Runnable(){
        new MultipleNetwork(callback, myRunnable).run();
    }

    public static void delayUI(Activity activity, int delay, Runnable runnable) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            activity.runOnUiThread(runnable);
        }).start();
    }

    public static void shutDownNow() {
        if (mExecutor != null) {
            mExecutor.shutdownNow();
            mExecutor = null;
        }
    }

    private static final Handler sHandler = new Handler(Looper.getMainLooper());


    public static void post(Runnable runnable) {
        sHandler.post(runnable);
    }

    public static void postDelayed(Runnable runnable, int delayMillis) {
        sHandler.postDelayed(runnable, delayMillis);
    }

}
