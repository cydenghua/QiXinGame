package com.dcode.wanjin.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import com.dcode.corelibrary.util.ToastUtils;
import com.dcode.wanjin.R;

public class InputValueDialog extends Dialog {

    //定义回调事件，用于dialog的点击事件
    public interface OnGetValueListener{
        void getValue(String value, String isXian);
    }

    private Context mContext;
    private OnGetValueListener mGetValueListener;

    private TextView mTvValue;
    private Button mBtnOk;
    private CheckBox mCbXian;

    public InputValueDialog(@NonNull Context context) {
        super(context);
        mContext = context;
    }

    public InputValueDialog(@NonNull Context context, OnGetValueListener listener) {
        super(context);
        mContext = context;
        mGetValueListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dia_input_value);
        setTitle("输入");
        mBtnOk = findViewById(R.id.btn_ok);
        mTvValue = findViewById(R.id.tv_value);
        mCbXian = findViewById(R.id.cb_xian);
        mCbXian.setChecked(false);

        findViewById(R.id.btn0).setOnClickListener(clickNum);
        findViewById(R.id.btn1).setOnClickListener(clickNum);
        findViewById(R.id.btn2).setOnClickListener(clickNum);
        findViewById(R.id.btn3).setOnClickListener(clickNum);
        findViewById(R.id.btn4).setOnClickListener(clickNum);
        findViewById(R.id.btn5).setOnClickListener(clickNum);
        findViewById(R.id.btn6).setOnClickListener(clickNum);
        findViewById(R.id.btn7).setOnClickListener(clickNum);
        findViewById(R.id.btn8).setOnClickListener(clickNum);
        findViewById(R.id.btn9).setOnClickListener(clickNum);
        findViewById(R.id.btn_x).setOnClickListener(clickNum);


        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isXian = "0";
                String sValue = mTvValue.getText().toString();
                if(TextUtils.isEmpty(sValue)) {
                    ToastUtils.show(mContext, "还没有输入");
                    return;
                }
                if(sValue.length() < 4 && sValue.contains("X")) {
                    ToastUtils.show(mContext, "输入错误");
                    return;
                }
                if(sValue.length() < 4) {
                    isXian = "1";
                }
                if(mCbXian.isChecked() && sValue.length() > 3 && !sValue.contains("X")) {
                    isXian = "1";
                }

                if(null != mGetValueListener) {
                    mGetValueListener.getValue(sValue, isXian);
                    dismiss();
                }
            }
        });

    }

    View.OnClickListener clickNum = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button button = (Button)view;
//            button.getText().toString();
            mTvValue.setText(mTvValue.getText().toString().concat(button.getText().toString()));

            if(mTvValue.getText().length() > 4) {
                mTvValue.setText(button.getText().toString());
            }

        }
    };


}
