package com.dcode.wanjin.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.dcode.corelibrary.network.RequestCallbackSimply;
import com.dcode.corelibrary.network.RequestParamsMap;
import com.dcode.wanjin.R;
import com.dcode.wanjin.WanjinApplication;
import com.dcode.wanjin.activity.MainActivity;
import com.dcode.wanjin.activity.WanJinBaseActivity;
import com.dcode.wanjin.entity.PeriodLastEntity;
import com.dcode.wanjin.entity.UserLoginEntity;
import com.dcode.wanjin.event.EventLogin;
import com.dcode.wanjin.entity.LoginInfo;
import com.dcode.wanjin.network.HttpApi;
import com.dcode.wanjin.util.Md5Util;
import com.dcode.wanjin.util.PreferencesWrapper;
import com.dcode.wanjin.util.SharePrefUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class LoginActivity extends WanJinBaseActivity {

    @butterknife.BindView(R.id.lv)
    ListView mListView;

    private QuickAdapter<LoginInfo> mAdapter;
    private List<LoginInfo> mLoginInfo;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoginList();
    }

    @Override
    protected int layoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected String topTitle() {
        return null;
    }

    @Override
    protected boolean isRegisterEventbus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive(EventLogin event) {
        getLoginList();
    }

    @OnClick(R.id.add)
    public void clickAdd() {
        LoginAddActivity.startActivityAdd(LoginActivity.this);
    }

    @Override
    protected void initAdapter() {
        super.initAdapter();
        mAdapter = new QuickAdapter<LoginInfo>(this, R.layout.activity_login_item) {
            @Override
            protected void convert(final BaseAdapterHelper helper, final LoginInfo item) {
                helper.setText(R.id.tv_name, item.getLoginName());
                helper.setText(R.id.tv_url, item.getServerUrl());
                helper.setOnClickListener(R.id.btn_set, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LoginAddActivity.startActivityEdit(LoginActivity.this, helper.getPosition());
                    }
                });

                helper.setOnClickListener(R.id.btn_login, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        HttpApi.BASE_URL = item.getServerUrl();
                        userLogin(item.getUserName(), item.getPassword());
                    }
                });
            }
        };

        mListView.setAdapter(mAdapter);
    }

    public void getLoginList() {
        String strList = SharePrefUtil.getString("LoginList");
        Gson gs = new Gson();
        mLoginInfo =gs.fromJson(strList, new TypeToken<List<LoginInfo>>() {}.getType());

        if(mLoginInfo == null) {
            mLoginInfo = new ArrayList<LoginInfo>();
            mLoginInfo.add(new LoginInfo());
        }else {
        }
        mAdapter.replaceAll(mLoginInfo);
    }

    private void userLogin(String userName, String passWord) {
        RequestParamsMap map = new RequestParamsMap();
        map.add("name", userName);
        map.add("password", Md5Util.strMd5(passWord));
        request(HttpApi.getUrlWithHost(HttpApi.login), map, UserLoginEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                UserLoginEntity entity = (UserLoginEntity) object;
                if (entity.isSuccess()) {
                    WanjinApplication app = (WanjinApplication)getApplication();
                    app.setCurrUser(entity.getData().getUserInfo());
                    app.setCurrPeriod(entity.getData().getLastPeriod());

                    PreferencesWrapper.setToken(entity.getData().getUserInfo().getToken());
                    MainActivity.startActivity(LoginActivity.this);
//                    LoginActivity.this.finish();
                } else {
                    showToast(entity.getMessage());
                }
            }

            @Override
            public boolean onFailure(int statusCode, String response) {
                return super.onFailure(statusCode, response);
            }
        }, true);
    }
//
//    private void getLastPeriod() {
//        RequestParamsMap map = new RequestParamsMap();
//        request(HttpApi.getUrlWithHost(HttpApi.getLastPeriod), map, PeriodLastEntity.class, new RequestCallbackSimply() {
//            @Override
//            public void onSuccess(Object object) {
//                PeriodLastEntity entity = (PeriodLastEntity) object;
//                if (entity.isSuccess()) {
//                    WanjinApplication app = (WanjinApplication)getApplication();
//                    app.setCurrPeriod(entity.getData());
//                } else {
//                    showToast(entity.getMessage());
//                }
//            }
//            @Override
//            public boolean onFailure(int statusCode, String response) {
//                return super.onFailure(statusCode, response);
//            }
//        }, false);
//    }


}
