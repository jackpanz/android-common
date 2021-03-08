package com.github.jackpanz.android.common;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class ScreenUtils {

    public static int widthPixels;

    public static int heightPixels;

    public static float density;

    public static int densityDpi;

    public static int screenWidth;

    public static int screenHeight;

    public static void init(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        widthPixels = dm.widthPixels;         // 屏幕宽度（像素）
        heightPixels = dm.heightPixels;       // 屏幕高度（像素）
        density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        screenWidth = (int) (widthPixels / density);  // 屏幕宽度(dp)
        screenHeight = (int) (heightPixels / density);// 屏幕高度(dp)
        Log.e("h_bl", "屏幕宽度（像素）：" + widthPixels);
        Log.e("h_bl", "屏幕高度（像素）：" + heightPixels);
        Log.e("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
        Log.e("h_bl", "屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
        Log.e("h_bl", "屏幕宽度（dp）：" + screenWidth);
        Log.e("h_bl", "屏幕高度（dp）：" + screenHeight);

        try {
            printDeviceHardwareInfo();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void printDeviceHardwareInfo(){
        Log.e("zz", "---------------Build------------");
        Log.e("zz", "Build.BOARD: "+ Build.BOARD);
        Log.e("zz", "Build.BOOTLOADER: "+Build.BOOTLOADER);
        Log.e("zz", "Build.BRAND: "+Build.BRAND);//设备牌子
        Log.e("zz", "Build.DEVICE: "+Build.DEVICE);//设备名
        Log.e("zz", "Build.DISPLAY: "+Build.DISPLAY);//显示设备号
        Log.e("zz", "Build.FINGERPRINT: "+Build.FINGERPRINT);//设备指纹
        Log.e("zz", "Build.HARDWARE: "+Build.HARDWARE);
        Log.e("zz", "Build.HOST: "+Build.HOST);
        Log.e("zz", "Build.ID: "+Build.ID);//设备硬件id
        Log.e("zz", "Build.MANUFACTURER: "+Build.MANUFACTURER);//厂商
        Log.e("zz", "Build.MODEL: "+Build.MODEL);//设备型号
        Log.e("zz", "Build.PRODUCT: "+Build.PRODUCT);//产品名，和DEVICE一样
        Log.e("zz", "Build.SERIAL: "+Build.SERIAL);//设备序列号
        Log.e("zz", "Build.TAGS: "+Build.TAGS);
        Log.e("zz", "Build.TYPE: "+Build.TYPE);
        Log.e("zz", "Build.UNKNOWN: "+Build.UNKNOWN);
        Log.e("zz", "Build.USER: "+Build.USER);
        Log.e("zz", "Build.CPU_ABI: "+Build.SUPPORTED_ABIS);
        Log.e("zz", "Build.CPU_ABI2: "+Build.SUPPORTED_ABIS);
        Log.e("zz", "Build.RADIO: "+Build.getRadioVersion());
        Log.e("zz", "Build.TIME: "+Build.TIME);//出厂时间
        Log.e("zz", "Build.VERSION.CODENAME: "+Build.VERSION.CODENAME);
        Log.e("zz", "Build.VERSION.INCREMENTAL: "+Build.VERSION.INCREMENTAL);//不详，重要
        Log.e("zz", "Build.VERSION.RELEASE: "+Build.VERSION.RELEASE);//系统版本号
        Log.e("zz", "Build.VERSION.SDK_INT: "+Build.VERSION.SDK_INT);//api级数，int型返回
    }



}
