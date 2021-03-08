package com.github.jackpanz.android;

import android.content.Intent;
import android.os.Bundle;

import com.github.jackpanz.android.common.activity.LocalizationBasicActivity;
import com.github.jackpanz.android.common.test.R;


public class MainActivity extends LocalizationBasicActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        findViewById(R.id.button1).setOnClickListener(v -> {
            startActivity(new Intent(this, LoadingDialogAcitvity.class));
        });

        findViewById(R.id.button2).setOnClickListener(v -> {
            startActivity(new Intent(this, ProgressDialogActivity.class));
        });

    }

}
