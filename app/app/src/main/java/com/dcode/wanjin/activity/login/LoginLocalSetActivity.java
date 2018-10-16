package com.dcode.wanjin.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.dcode.wanjin.R;
import com.dcode.wanjin.activity.WanJinBaseActivity;
import com.dcode.wanjin.util.PreferencesWrapper;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginLocalSetActivity extends WanJinBaseActivity {

    @BindView(R.id.et_old_pw)
    EditText mEtOldPw;
    @BindView(R.id.et_new_pw)
    EditText mEtNewPw;
    @BindView(R.id.et_new_pw_2)
    EditText mEtNewPw2;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginLocalSetActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PreferencesWrapper.getLocalPassword().isEmpty()) {
            findViewById(R.id.ll_old).setVisibility(View.GONE);
        }
    }

    @Override
    protected int layoutResource() {
        return R.layout.activity_login_local_set;
    }

    @Override
    protected String topTitle() {
        return null;
    }

    @OnClick(R.id.btn_save)
    public void clickSave() {
        if(mEtNewPw.getText().toString().isEmpty() || mEtNewPw2.getText().toString().isEmpty()){
            showToast("请输入新密码");
            return;
        }
        if(!mEtNewPw.getText().toString().equals(mEtNewPw2.getText().toString())) {
            showToast("两次输入密码不一致");
            return;
        }

        if (!PreferencesWrapper.getLocalPassword().isEmpty()) {
            if(!PreferencesWrapper.getLocalPassword().equals(mEtOldPw.getText().toString())) {
                showToast("旧密码不正确");
                return;
            }
        }

        PreferencesWrapper.setLocalPassword(mEtNewPw.getText().toString());
        showToast("保存成功");
        finish();
    }

    @OnClick(R.id.btn_cancel)
    public void clickCancel() {
        finish();
    }
}
