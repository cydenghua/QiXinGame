package com.dcode.wanjin.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.dcode.corelibrary.activity.BaseActivity;
import com.dcode.corelibrary.network.RequestCallbackSimply;
import com.dcode.corelibrary.network.RequestParamsMap;
import com.dcode.wanjin.util.PreferencesWrapper;

public abstract class WanJinBaseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (screenOrientationPortrait() && getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        initView();
        initAdapter();
    }

    @Override
    protected int getLayoutResource() {
        return layoutResource();
    }

    @Override
    protected String getTopTitle() {
        return topTitle();
    }

    protected abstract int layoutResource();

    protected abstract String topTitle();

    protected void initView() {
    }

    protected void initAdapter() {
    }

    /**
     * 屏幕方向是否必须是竖屏
     *
     * @return
     */
    protected boolean screenOrientationPortrait() {
        return true;
    }


    public void request(final String url,final RequestParamsMap params, Class clazz, final RequestCallbackSimply callback) {
        String token = PreferencesWrapper.getToken();
        request(url, token, params, clazz, callback, false);
    }

    public void request(final String url,final RequestParamsMap params, Class clazz, final RequestCallbackSimply callback, final boolean loading) {
        String token = PreferencesWrapper.getToken();
        request(url, token, params, clazz, callback, loading);
    }


}
