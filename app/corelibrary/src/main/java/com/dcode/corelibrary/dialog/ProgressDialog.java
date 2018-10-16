package com.dcode.corelibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.dcode.corelibrary.R;

public class ProgressDialog extends Dialog {
    private TextView tv_message;
    ImageView iv_progress;
    private String mMessage;
    private AnimationDrawable mDrawableProgress;

    public ProgressDialog(Context context) {
        this(context, "");
    }

    public ProgressDialog(Context context, String message) {
        super(context, R.style.ProgressDialog);
        setMessage(message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        setCanceledOnTouchOutside(false);
        tv_message = (TextView) findViewById(R.id.tv_message);
        iv_progress = (ImageView) findViewById(R.id.iv_progress);
        mDrawableProgress = (AnimationDrawable) iv_progress.getBackground();
        mDrawableProgress.start();
        tv_message.setText(TextUtils.isEmpty(mMessage) ? "" : mMessage);
    }


    public void setMessage(String msg) {
        this.mMessage = msg;
        if (null != tv_message) {
            tv_message.setText(TextUtils.isEmpty(mMessage) ? "" : mMessage);
        }
    }

    @Override
    protected void onStop() {
        if (mDrawableProgress != null)
            mDrawableProgress.stop();
        super.onStop();
    }
}
