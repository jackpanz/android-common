package com.github.jackpanz.android.common;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.akexorcist.localizationactivity.core.LanguageSetting;

import java.util.Locale;


/**
 * Created by jack on 2020/10/22 17:40
 * 多語言工具類
 */
public class LocalizationUtils {

    private static final String TAG = LocalizationUtils.class.getSimpleName();

    final public static Locale PT_LOCALE = new Locale("pt", "");

    public static Locale DEFAULT_LOCALE = Locale.TRADITIONAL_CHINESE;

    public static Locale DEFAULT_CN_LOCALE = Locale.TRADITIONAL_CHINESE;

    public static boolean IS_HAVE_EN = true;

    public static boolean IS_HAVE_ZH_TW = true;

    public static boolean IS_HAVE_ZH_CN = true;

    public static boolean IS_HAVE_PT = true;

    public static Locale getLanguage() {
        return LanguageSetting.getLanguage(ContextUtils.getContext());
    }

    public static void setLanguage(Locale locale) {
        LanguageSetting.setLanguage(ContextUtils.getContext(), locale);
    }

    public static boolean isPT(Locale locale) {
        return PT_LOCALE.equals(locale);
    }

    public static boolean isCN(Locale locale) {
        return Locale.SIMPLIFIED_CHINESE.equals(locale);
    }

    public static boolean isTW(Locale locale) {
        return Locale.TRADITIONAL_CHINESE.equals(locale);
    }

    public static boolean isEN(Locale locale) {
        return Locale.ENGLISH.equals(locale);
    }

    public static boolean isPT() {
        return isPT(getLanguage());
    }

    public static boolean isCN() {
        return isCN(getLanguage());
    }

    public static boolean isTW() {
        return isTW(getLanguage());
    }

    public static boolean isEN() {
        return isEN(getLanguage());
    }

    public static void isPT(LocalizationRunnable localizationRunnable) {
        if(isPT(getLanguage())){
            localizationRunnable.run();
        }
    }

    public static void isCN(LocalizationRunnable localizationRunnable) {
        if(isCN(getLanguage())){
            localizationRunnable.run();
        }
    }

    public static void isTW(LocalizationRunnable localizationRunnable) {
        if(isTW(getLanguage())){
            localizationRunnable.run();
        }
    }

    public static void isEN(LocalizationRunnable localizationRunnable) {
        if(isEN(getLanguage())){
            localizationRunnable.run();
        }
    }

    @FunctionalInterface
    public interface LocalizationRunnable {
        void run();
    }


    public static <T> T et(T en, T zh_TW) {
        return get(en, zh_TW, null, null);
    }

    public static <T> T etc(T en, T zh_TW,T zh_CN) {
        return get(en, zh_TW, zh_CN, null);
    }

    public static <T> T etp(T en, T zh_TW, T pt) {
        return get(en, zh_TW, null, pt);
    }

    public static <T> T tc(T en, T zh_TW) {
        return get(en, zh_TW, null, null);
    }

    public static <T> T tcp(T zh_TW, T zh_CN, T pt) {
        return get(null, zh_TW, zh_CN, pt);
    }

    public static <T> T tp(T zh_TW, T pt) {
        return get(null, zh_TW, null, pt);
    }

    public static <T> T cp(T zh_CN, T pt) {
        return get(null, null, zh_CN, pt);
    }

    public static <T> T get(T en, T zh_TW, T zh_CN, T pt) {
        Locale locale = getLanguage();
        if (isTW(locale)) {
            return zh_TW;
        } else if (isCN(locale)) {
            return zh_CN;
        } else if (isPT(locale)) {
            return pt;
        } else if (isEN(locale)) {
            return en;
        } else {
            if (isTW(DEFAULT_LOCALE)) {
                return zh_TW;
            } else if (isCN(DEFAULT_LOCALE)) {
                return zh_CN;
            } else if (isPT(DEFAULT_LOCALE)) {
                return pt;
            } else if (isEN(DEFAULT_LOCALE)) {
                return en;
            }
        }
        return en;
    }

    /**
     * 設置語言後重啟APP，重新加載Application
     *
     * @param context
     * @param clazz
     */
    public static void makeRestartActivityTask(Context context, Class clazz) {
        // AlarmManager在API19以上执行时间变得不准确
        Intent intent = new Intent(context, clazz);
        Intent restartIntent = Intent.makeRestartActivityTask(intent.getComponent());
        context.startActivity(restartIntent);
        System.exit(0);
    }

    private static Locale getSystemDefaultLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            return context.getResources().getConfiguration().locale;
        }
    }

    /**
     * APP第一次打開時，通過系統Locale來判斷對應語言
     *
     * @param context
     * @return
     */
    public static Locale getAppDefaultLocale(Context context) {
        try {
            Locale locale = getSystemDefaultLocale(context);
            Log.d(TAG, "Country:" + locale.getCountry());
            Log.d(TAG, "Language:" + locale.getLanguage());
            if ( ( IS_HAVE_ZH_CN || IS_HAVE_ZH_TW ) && "zh".equals(locale.getLanguage()) ) {
                if( IS_HAVE_ZH_CN && "Hans".equals(locale.getScript())){
                    return Locale.SIMPLIFIED_CHINESE;
                } else if( IS_HAVE_ZH_TW && "Hant".equals(locale.getScript()) ) {
                    return Locale.TRADITIONAL_CHINESE;
                } else {
                    return DEFAULT_CN_LOCALE;
                }
            } else if ( IS_HAVE_PT && "pt".equals(locale.getLanguage())) {
                return PT_LOCALE;
            } else if ( IS_HAVE_EN && "en".equals(locale.getLanguage())) {
                return Locale.ENGLISH;
            }
        } catch (Exception ex) { ex.printStackTrace(); }
        return DEFAULT_LOCALE;
    }

}
