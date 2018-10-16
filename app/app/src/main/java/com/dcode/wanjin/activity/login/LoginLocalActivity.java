package com.dcode.wanjin.activity.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.dcode.wanjin.R;
import com.dcode.wanjin.activity.WanJinBaseActivity;
import com.dcode.wanjin.util.PreferencesWrapper;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginLocalActivity extends WanJinBaseActivity {

    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.btn_set)
    Button btnSet;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferencesWrapper.setToken("1");//设置临时token，避免服务端过滤.
    }

    @Override
    protected int layoutResource() {
        return R.layout.activity_login_local;
    }

    @Override
    protected String topTitle() {
        return null;
    }

    @OnClick(R.id.btn_login)
    public void clickLogin() {
        if(mEtPassword.getText().toString().isEmpty()) {
            showToast("请输入密码.");
            return;
        }

        if(mEtPassword.getText().toString().equals(PreferencesWrapper.getLocalPassword())) {
            LoginActivity.startActivity(this);
            mEtPassword.setText("");
            LoginLocalActivity.this.finish();
        }else{
            showToast("密码错误！");
        }
    }

    @OnClick(R.id.btn_set)
    public void clickSet(){
        LoginLocalSetActivity.startActivity(this);
    }

}
