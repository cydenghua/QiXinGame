package com.dcode.wanjin.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.dcode.corelibrary.dialog.CommonDialog;
import com.dcode.wanjin.R;
import com.dcode.wanjin.activity.WanJinBaseActivity;
import com.dcode.wanjin.event.EventLogin;
import com.dcode.wanjin.entity.LoginInfo;
import com.dcode.wanjin.util.SharePrefUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginAddActivity extends WanJinBaseActivity {

    public static String PARAM_INDEX = "param_index";

    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_url)
    EditText mEtUrl;
    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_password)
    EditText mEtPassword;

    private int mIndex;
    private List<LoginInfo> mLoginInfo;

    public static void startActivityAdd(Context context) {
        Intent intent = new Intent(context, LoginAddActivity.class);
        intent.putExtra(PARAM_INDEX, -1);
        context.startActivity(intent);
    }

    public static void startActivityEdit(Context context, int fIndex) {
        Intent intent = new Intent(context, LoginAddActivity.class);
        intent.putExtra(PARAM_INDEX, fIndex);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndex = getIntent().getIntExtra(PARAM_INDEX, -1);

        setTopTitle("登录设置");

        String strList = SharePrefUtil.getString("LoginList");
        Gson gs = new Gson();
        mLoginInfo =gs.fromJson(strList, new TypeToken<List<LoginInfo>>() {}.getType());

        if(mLoginInfo == null) {
            mLoginInfo = new ArrayList<LoginInfo>();
            mLoginInfo.add(new LoginInfo());
            mIndex = 0;
        }else {
            if(mIndex < 0) {
                mLoginInfo.add(new LoginInfo());
                mIndex = mLoginInfo.size() -1;
            }
        }
        getEditData();
    }

    @Override
    protected int layoutResource() {
        return R.layout.activity_login_add;
    }

    @Override
    protected String topTitle() {
        return null;
    }

    @OnClick(R.id.btn_del)
    public void clickDel(){
        new CommonDialog(this, "确定要删除？", "取消", "删除", new CommonDialog.PositiveListener() {
            @Override
            public void positiveAction() {
                mLoginInfo.remove(mIndex);
                Gson gs = new Gson();
                SharePrefUtil.putString("LoginList", gs.toJson(mLoginInfo));
                SharePrefUtil.commit();
                EventBus.getDefault().post(new EventLogin(EventLogin.KEY_REFRESH));
                LoginAddActivity.this.finish();
            }
        });

    }

    @OnClick(R.id.btn_clear)
    public void clickClear(){
        mEtName.getText().clear();
        mEtUrl.getText().clear();
        mEtUsername.getText().clear();
        mEtPassword.getText().clear();
    }

    @OnClick(R.id.btn_save)
    public void clickSave(){
        mLoginInfo.get(mIndex).setLoginName(mEtName.getText().toString());
        mLoginInfo.get(mIndex).setServerUrl(mEtUrl.getText().toString());
        mLoginInfo.get(mIndex).setUserName(mEtUsername.getText().toString());
        mLoginInfo.get(mIndex).setPassword(mEtPassword.getText().toString());

        Gson gs = new Gson();
        SharePrefUtil.putString("LoginList", gs.toJson(mLoginInfo));
        SharePrefUtil.commit();

        EventBus.getDefault().post(new EventLogin(EventLogin.KEY_REFRESH));

        finish();
    }

    private void getEditData() {
        mEtName.setText(mLoginInfo.get(mIndex).getLoginName());
        mEtUrl.setText(mLoginInfo.get(mIndex).getServerUrl());
        mEtUsername.setText(mLoginInfo.get(mIndex).getUserName());
        mEtPassword.setText(mLoginInfo.get(mIndex).getPassword());
    }


}
