package com.dcode.wanjin.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcode.corelibrary.activity.BaseFragment;
import com.dcode.corelibrary.network.RequestCallbackSimply;
import com.dcode.corelibrary.network.RequestParamsMap;
import com.dcode.wanjin.util.PreferencesWrapper;

public abstract class WanjinBaseFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initView();
        initAdapter();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(this.getClass().getName());
    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(this.getClass().getName());
    }

    @Override
    protected int getLayoutResource() {
        return layoutResource();
    }

    protected abstract int layoutResource();

    protected abstract String getTitle();

    protected void initView() {
    }

    protected void initAdapter() {
    }

    public void request(final String url, final RequestParamsMap params, Class clazz, final RequestCallbackSimply callback) {
        String token = PreferencesWrapper.getToken();
        request(url, token, params, clazz, callback, false);
    }

    public void request(final String url,final RequestParamsMap params, Class clazz, final RequestCallbackSimply callback, final boolean loading) {
        String token = PreferencesWrapper.getToken();
        request(url, token, params, clazz, callback, loading);
    }


}
