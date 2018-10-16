package com.dcode.corelibrary.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

/**
 * App工具类
 */
public class AppUtils {

    /**
     * 获取app版本号
     *
     * @param context context
     * @return 版本号
     */
    public static int getVersionCode(Context context) {
        int versionCode = -1;
        try {
            PackageInfo packInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            versionCode = packInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取app版本名称
     *
     * @param context context
     * @return 版本名称
     */
    public static String getVersionName(Context context) {
        String appVersion = "3.0";
        try {
            PackageInfo packInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            appVersion = packInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersion;
    }

    /**
     * 跳转到应用市场
     */
    public static void jumpToMarket(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
        // intent.setData(Uri.parse("market://search?q=" +
        // getResources().getString(R.string.app_name)));
        context.startActivity(intent);
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

}
