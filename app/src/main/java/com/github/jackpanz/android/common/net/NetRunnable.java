package com.github.jackpanz.android.common.net;

@FunctionalInterface
public interface NetRunnable {

    Object run(CallbackTag callbackTag) throws Exception;

}
