package com.dcode.wanjin.util;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.dcode.wanjin.WanjinApplication;
import java.io.Serializable;

public class SharePrefUtil implements Serializable {
    private static SharedPreferences pref;
    private static Editor edit;

    static {
        pref = PreferenceManager.getDefaultSharedPreferences(WanjinApplication.getInstance());
        edit = pref.edit();
    }

    public static boolean getBoolean(String key) {
        return pref.getBoolean(key, false);
    }

    public static void putBoolean(String key, boolean value) {
        edit.putBoolean(key, value);
    }


    public static int getInt(String key) {
        return pref.getInt(key, 0);
    }

    public static void putInt(String key, int value) {
        edit.putInt(key, value);
    }


    public static String getString(String key) {
        // if (key.equals(Conast.ACCESS_TOKEN)) {
        // return "8888";//调试用通用token
        // }
        return pref.getString(key, "");
    }

    public static void putString(String key, String value) {
        edit.putString(key, value);
    }

    public static float getFloat(String key) {
        return pref.getFloat(key, 0);
    }

    public static void putFloat(String key, float value) {
        edit.putFloat(key, value);
    }

    public static long getLong(String key) {
        return pref.getLong(key, 0);
    }

    public static void putLong(String key, long value) {
        edit.putLong(key, value);
    }

    public static boolean getMsgNotifyBoolean(String key) {
        return pref.getBoolean(key, true);
    }

    public static void commit() {
        edit.commit();
    }
}
