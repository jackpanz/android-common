package com.github.jackpanz.android;

import android.os.Bundle;
import com.github.jackpanz.android.common.activity.LocalizationBasicActivity;
import com.github.jackpanz.android.common.dialog.LoadingDialog;
import com.github.jackpanz.android.common.net.ThreadUtils;
import com.github.jackpanz.android.common.test.R;

public class LoadingDialogAcitvity extends LocalizationBasicActivity {

        LoadingDialog loadingDialog1;

        LoadingDialog loadingDialog2;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_dialog);

        loadingDialog1 = new LoadingDialog.Builder(this)
                .setCancelOutside(false)
                .setCancelable(false)
                .create();

        loadingDialog2 = new LoadingDialog.Builder(this)
                .setCancelOutside(false)
                .setCancelable(false)
                .setMessage("加载中..")
                .create();

        findViewById(R.id.button1).setOnClickListener(v -> {
            loadingDialog1.show();
            ThreadUtils.delayUI(this, 5000,()-> {
                loadingDialog1.dismiss();
            });
        });

        findViewById(R.id.button1).setOnClickListener(v -> {
            loadingDialog2.show();
            ThreadUtils.delayUI(this, 5000,()-> {
                loadingDialog2.dismiss();
            });
        });

    }
}