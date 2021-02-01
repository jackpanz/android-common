package com.github.jackpanz.android.common;

import android.content.Context;

public class ContextUtils {

    private static Context context;

    public static void init(final Context context) {
        if (context == null)
            throw new NullPointerException("u should onInit first");
        ContextUtils.context = context.getApplicationContext();
    }

    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should onInit first");
    }
}
