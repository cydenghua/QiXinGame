package com.dcode.corelibrary.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dcode.corelibrary.R;
import com.dcode.corelibrary.dialog.ProgressDialog;
import com.dcode.corelibrary.network.Request;
import com.dcode.corelibrary.network.RequestCallbackSimply;
import com.dcode.corelibrary.network.RequestParamsMap;
import com.dcode.corelibrary.util.NetworkUtils;
import com.dcode.corelibrary.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {


    protected ImageView mIvTopRight, mIvTopRight2;
    protected TextView mTvTopTitle, mTvTopRight;

    protected final String TAG = getClass().getSimpleName();
    protected ProgressDialog mLoadingDialog;
    protected boolean mIsRegisterEventbus = false;//是否注册eventbus

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
        if (isRegisterEventbus()) {
            EventBus.getDefault().register(this);
        }

        mTvTopTitle = (TextView) findViewById(R.id.tv_title);
        mTvTopRight = (TextView) findViewById(R.id.tv_top_right);
        mIvTopRight = (ImageView) findViewById(R.id.iv_top_right);
        mIvTopRight2 = (ImageView) findViewById(R.id.iv_top_right2);
        if (mTvTopTitle != null) {
            mTvTopTitle.setText(getTopTitle());
        }

        Log.d("className", TAG);
    }

    @Override
    protected void onDestroy() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
        }
//        ButterKnife.unbind(this);
        if (isRegisterEventbus()) {
            EventBus.getDefault().unregister(this);
        }
        Request.getInstance().cancelRequest(this);
        super.onDestroy();
    }

    protected abstract int getLayoutResource();

    protected abstract String getTopTitle();

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
            if (isFinishing())
                return;
            if (mLoadingDialog == null) {
                mLoadingDialog = new ProgressDialog(this, msg);
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
        if (isFinishing() || mLoadingDialog == null)
            return;
        mLoadingDialog.dismiss();
    }


    /**
     * 显示Toast
     *
     * @param resId 资源id
     */
    public void showToast(int resId) {
        ToastUtils.show(this, resId);
    }

    /**
     * 显示Toast
     *
     * @param text 文本
     */
    public void showToast(String text) {
        ToastUtils.show(this, text);
    }

    public void request(final String url, final String token, final RequestParamsMap params, Class clazz, final RequestCallbackSimply callback) {
        request(url, token, params, clazz, callback, false);
    }

    public void request(final String url, final String token, final RequestParamsMap params, Class clazz, final RequestCallbackSimply callback, final boolean loading) {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            showToast(R.string.network_error);
            callback.onFailure(-1, getString(R.string.network_error));
            return;
        }
        params.add("channel", "android");
        if (loading) {
            showLoadingDialog();
        }
        Request.getInstance().post(this, url, token, params, clazz, new RequestCallbackSimply() {
            @Override
            public void onSuccess(final Object object) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingDialog();
                        if (object == null) {
                            ToastUtils.show(BaseActivity.this, "服务器数据有误");
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingDialog();
//                        if (mStateLayout != null) {
//                            mStateLayout.showError();
//                        }
                        if (!callback.onFailure(statusCode, response)) {
                            showToast("Err:" + response);
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


    public void setTopTitle(int resId) {
        setTopTitle(getString(resId));
    }

    public void setTopTitle(String text) {
        if (mTvTopTitle != null) {
            mTvTopTitle.setText(text);
        }
    }

    public void setTopRightText(int resId) {
        if (mTvTopRight != null) {
            mTvTopRight.setText(resId);
            mTvTopRight.setVisibility(View.VISIBLE);
            mTvTopRight.setOnClickListener(this);
        }
    }

    public void setTopRightText(String text) {
        if (mTvTopRight != null) {
            mTvTopRight.setText(text);
            mTvTopRight.setVisibility(View.VISIBLE);
            mTvTopRight.setOnClickListener(this);
        }
    }

    public void setTopRightTextVisibility(int visibility) {
        if (mTvTopRight != null) {
            mTvTopRight.setVisibility(visibility);
        }
    }

    public void setTopRightImage(int resId) {
        if (mIvTopRight != null) {
            mIvTopRight.setImageResource(resId);
            mIvTopRight.setVisibility(View.VISIBLE);
            mIvTopRight.setOnClickListener(this);
        }
    }

    public void setTopRightImageVisibility(int visibility) {
        if (mIvTopRight != null) {
            mIvTopRight.setVisibility(visibility);
        }
    }

    public void setTopTitleImage(int resId) {
        if (mTvTopTitle != null) {
            Drawable drawable = getResources().getDrawable(resId);
            drawable.setBounds(0, 0, 50, 50);
            mTvTopTitle.setCompoundDrawables(null, null, drawable, null);
            mTvTopTitle.setGravity(Gravity.CENTER_VERTICAL);
            mTvTopTitle.setCompoundDrawablePadding(10);
            mTvTopTitle.setOnClickListener(this);
        }
    }

    public void setTopRightImage2(int resId) {
        if (mIvTopRight2 != null) {
            mIvTopRight2.setImageResource(resId);
            mIvTopRight2.setVisibility(View.VISIBLE);
            mIvTopRight2.setOnClickListener(this);
        }
    }


    public void onBack(View v) {
        finish();
    }

    public void onEmpty(View v) {
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_top_right || v.getId() == R.id.iv_top_right) {// 右上角按钮
            dealTopRightClickEvent();
        } else if (v.getId() == R.id.iv_top_right2) {
            dealTopRightClickEvent2();
        } else if (v.getId() == R.id.tv_title) {
            dealTopTitleImageClickEvent();
        }
//        else if (v.getId() == R.id.btn_empty) {// 空视图
//        } else if (v.getId() == R.id.iv_failed) {// 刷新视图
//        }
    }

    protected void dealTopRightClickEvent() {
    }

    protected void dealTopRightClickEvent2() {
    }

    protected void dealTopTitleImageClickEvent() {
    }



}
