package com.github.jackpanz.android.common.activity;

import android.content.Context;
import android.os.Build;
import android.preference.PreferenceActivity;

import com.akexorcist.localizationactivity.core.LocalizationActivityDelegate;

public class LocalizationBasicPreferenceActivity extends PreferenceActivity {

    protected LocalizationActivityDelegate localizationDelegate = new LocalizationActivityDelegate(this);

    protected void attachBaseContext(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            applyOverrideConfiguration(localizationDelegate.updateConfigurationLocale(context));
            super.attachBaseContext(context);
        } else {
            super.attachBaseContext(localizationDelegate.attachBaseContext(context));
        }
    }

}
