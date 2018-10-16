package com.dcode.corelibrary.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcode.corelibrary.R;
import com.dcode.corelibrary.dialog.ProgressDialog;
import com.dcode.corelibrary.network.Request;
import com.dcode.corelibrary.network.RequestCallbackSimply;
import com.dcode.corelibrary.network.RequestParamsMap;
import com.dcode.corelibrary.util.NetworkUtils;
import com.dcode.corelibrary.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by xiaoming on 2017/4/13.
 */

public abstract class BaseFragment extends Fragment {

    protected Activity mActivity;
    protected ProgressDialog mLoadingDialog;
//    protected StateLayout mStateLayout;
    protected final String TAG = getClass().getSimpleName();

    protected boolean mIsRegisterEventbus = false;//是否注册eventbus

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.bind(this, view);
        Log.d("className", TAG);
        if (isRegisterEventbus()) {
            EventBus.getDefault().register(this);
        }
        return view;
    }

    protected abstract int getLayoutResource();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onDestroyView() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
//        ButterKnife.unbind(this);
        if (isRegisterEventbus()) {
            EventBus.getDefault().unregister(this);
        }
        Request.getInstance().cancelRequest(mActivity);
        super.onDestroyView();
    }

    protected boolean isRegisterEventbus() {
        return mIsRegisterEventbus;
    }

    /**
     * 显示进度条(屏蔽点击)
     */
    public void showLoadingDialog() {
        showLoadingDialog("加载中...");
    }

    /**
     * 显示进度条(屏蔽触摸事件, 提交，修改数据时用)
     *
     * @param msg
     */
    public void showLoadingDialog(String msg) {
        try {
            if (isDetached())
                return;
            if (mLoadingDialog == null) {
                mLoadingDialog = new ProgressDialog(mActivity, msg);
            } else {
//                mLoadingDialog.startShowAnimation();
            }
            if (mLoadingDialog.isShowing()) return;
            mLoadingDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏进度条(屏蔽点击)
     */
    public void hideLoadingDialog() {
        if (isDetached() || mLoadingDialog == null)
            return;
        mLoadingDialog.dismiss();
    }

    /**
     * 显示Toast
     *
     * @param resId 资源id
     */
    public void showToast(int resId) {
        ToastUtils.show(mActivity, resId);
    }

    /**
     * 显示Toast
     *
     * @param text 文本
     */
    public void showToast(String text) {
        ToastUtils.show(mActivity, text);
    }


    public void request(final String url, final String token,  final RequestParamsMap params, Class clazz, final RequestCallbackSimply callback) {
        request(url, token, params, clazz, callback, false);
    }

    public void request(final String url, final String token, final RequestParamsMap params, Class clazz, final RequestCallbackSimply callback, final boolean loading) {
        if (!NetworkUtils.isNetworkAvailable(mActivity)) {
            showToast(R.string.network_error);
            callback.onFailure(-1, getString(R.string.network_error));
            return;
        }
        params.add("channel", "android");
        if (loading) {
            showLoadingDialog();
        }
        Request.getInstance().post(mActivity, url, token, params, clazz, new RequestCallbackSimply() {
            @Override
            public void onSuccess(final Object object) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingDialog();
                        if (object == null) {
                            showToast("服务器数据有误");
//                            if (mStateLayout != null) {
//                                mStateLayout.showError();
//                            }
                            return;
                        }
                        callback.onSuccess(object);
                    }
                });
            }

            @Override
            public boolean onFailure(final int statusCode, final String response) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingDialog();
//                        if (mStateLayout != null) {
//                            mStateLayout.showError();
//                        }
                        showToast("Err:" + response);
                        if (!callback.onFailure(statusCode, response)) {
                            if (loading) {
                                hideLoadingDialog();
                            }
                        }
                    }
                });
                return false;
            }
        });
    }

//    public void showImage(@NonNull View view, String url) {
//        ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions(view, url));
//    }
//
//    public void showImage(@NonNull ImageLoaderOptions options) {
//        ImageLoaderManager.getInstance().showImage(options);
//    }
}