package com.github.jackpanz.android.common.net;

public class SingleNetwork {
    NetRunnable[] netRunnables = null;

    Object[] values = null;

    int length = 0;

    int endRun = 0;

    CallbackTag callbackTag = null;

    public SingleNetwork(SimpleCallback callback, NetRunnable... netRunnables) {
        this.netRunnables = netRunnables;
        this.length = netRunnables.length;
        this.callbackTag = new CallbackTag(callback);
        this.values = new Object[netRunnables.length];
    }

    public void run() {
        for (int i = 0; i < netRunnables.length; i++) {
            int index = i;
            NetRunnable netRunnable = netRunnables[i];
            SingleThreadUtils.getExecutor().execute(() -> {
                Object value = null;
                try {
                    value = netRunnable.run(callbackTag);
                    this.callbackTag.success(index, value);
                } catch (Exception e) {
                    e.printStackTrace();
                    this.callbackTag.catchException(index, e);
                    this.callbackTag.catchException(e);
                } finally {
                    values[index] = value;
                    endRun++;
                    this.callbackTag.finallyBy(index);
                    if (endRun >= length) {
                        this.callbackTag.finallyAll(values);
                    }
                }
            });
        }
    }


}
