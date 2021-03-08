package com.github.jackpanz.android.common.application;

import android.content.Context;

import com.akexorcist.localizationactivity.core.LocalizationApplicationDelegate;
import com.github.jackpanz.android.common.LocalizationUtils;

//爱啪啪
public class App extends android.app.Application {

    final protected LocalizationApplicationDelegate localizationDelegate = new LocalizationApplicationDelegate();

    public void attachBaseContext(Context base) {
        localizationDelegate.setDefaultLanguage(base, LocalizationUtils.getAppDefaultLocale(base));
        super.attachBaseContext(localizationDelegate.attachBaseContext(base));
    }
}
