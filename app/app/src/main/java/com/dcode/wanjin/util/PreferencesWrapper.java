package com.dcode.wanjin.util;

import com.dcode.wanjin.entity.User;

import java.util.Date;

public class PreferencesWrapper {

    private static final String KEY_TOKEN = "TOKEN";
    private static final String KEY_TOKEN_TIME = "TOKEN_TIME";
    private static final String KEY_LOCAL_PASSWORD = "LOCAL_PASSWORD";
    private static final String KEY_CURR_USER = "CURR_USER";

    /**
     * 设置token
     *
     * @param token
     */
    public static void setToken(String token) {
        SharePrefUtil.putString(KEY_TOKEN, token);
        SharePrefUtil.putLong(KEY_TOKEN_TIME, new Date().getTime());//保存token更新时间
        SharePrefUtil.commit();
    }

    /**
     * 获取token
     *
     * @return
     */
    public static String getToken() {
        return SharePrefUtil.getString(KEY_TOKEN);
    }

    /**
     * 获取token是否过期
     *
     * @return
     */
    public static boolean isTokenValid() {
        return new Date().getTime() < SharePrefUtil.getLong(KEY_TOKEN_TIME) + 24 * 60 * 60 * 1000;  //24小时
    }

    /**
     * 设置本地密码
     *
     * @param pw
     */
    public static void setLocalPassword(String pw) {
        SharePrefUtil.putString(KEY_LOCAL_PASSWORD, pw);
        SharePrefUtil.commit();
    }

    /**
     * 获取本地密码
     *
     * @return
     */
    public static String getLocalPassword() {
        return SharePrefUtil.getString(KEY_LOCAL_PASSWORD);
    }


}
