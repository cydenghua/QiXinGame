package com.dcode.wanjin.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import com.dcode.corelibrary.network.RequestCallbackSimply;
import com.dcode.corelibrary.network.RequestParamsMap;
import com.dcode.wanjin.R;
import com.dcode.wanjin.activity.WanJinBaseActivity;
import com.dcode.wanjin.entity.MyUsersEntity;
import com.dcode.wanjin.entity.User;
import com.dcode.wanjin.event.EventMyUser;
import com.dcode.wanjin.network.HttpApi;
import com.dcode.wanjin.util.Md5Util;
import com.dcode.wanjin.util.StrUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class AddUserActivity extends WanJinBaseActivity {

    public static final String PARAM_MODE = "p_mode";
    public static final String PARAM_USER = "p_user";
    public static final int MODE_ADD = 1;
    public static final int MODE_EDIT = 2;

    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_pw)
    EditText mEtPw;
    @BindView(R.id.et_nickname)
    EditText mEtNickname;
    @BindView(R.id.et_credit_limit)
    EditText mEtCreditLimit;
    @BindView(R.id.cb_eneabled)
    CheckBox mCbEneabled;

    private int mMode = 1;
    private User mUser;

    public static void startActivityAdd(Context context) {
        Intent intent = new Intent(context, AddUserActivity.class);
        intent.putExtra(PARAM_MODE, MODE_ADD);
        context.startActivity(intent);
    }

    public static void startActivityEdit(Context context, User user) {
        Intent intent = new Intent(context, AddUserActivity.class);
        intent.putExtra(PARAM_MODE, MODE_EDIT);
        intent.putExtra(PARAM_USER, user);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMode = getIntent().getIntExtra(PARAM_MODE, MODE_ADD);

        if(mMode == MODE_EDIT) {
            mUser = getIntent().getParcelableExtra(PARAM_USER);
            mUser.setPassword("11111111");
            mEtName.setText(mUser.getName());
            mEtName.setEnabled(false);
            mEtNickname.setText(mUser.getNickname());
            mEtCreditLimit.setText(StrUtil.intFenToYuan(mUser.getCreditLimit()));
            mEtPw.setText(mUser.getPassword());
            mCbEneabled.setChecked(mUser.getEneabled() == 1);
        }
    }

    @Override
    protected int layoutResource() {
        return R.layout.activity_add_user;
    }

    @Override
    protected String topTitle() {
        return "会员设置";
    }

    @OnClick(R.id.btn_save)
    public void clickSave() {
        if (mEtName.getText().toString().isEmpty() || mEtPw.getText().toString().isEmpty()) {
            showToast("账号 和 密码不能为空");
            return;
        }
        if (mEtPw.getText().toString().length() < 6) {
            showToast("密码长度不少于6");
            return;
        }
        if (mEtCreditLimit.getText().toString().isEmpty()) {
            showToast("请输入信用额度");
            return;
        }

        try {
            Integer.parseInt(mEtCreditLimit.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showToast("信用额度输入错误");
            return;
        }
//        String regex = "^(?![A-Za-z]+$)(?!\\d+$)(?![\\W_]+$)\\S{8,16}$" ;    //密码的组成至少要包括大小写字母、数字及标点符号的其中两项
        String regex = "^(?![^a-zA-Z]+$)(?!\\\\D+$).{6,16}$" ;
        if(mMode == MODE_ADD && !mEtPw.getText().toString().matches(regex)){
            showToast("密码必须包含数字和字母");
            return;
        }

        //如果有修改密码则需要验证密码
        if(mMode == MODE_EDIT && !mUser.getPassword().equals(mEtPw.getText().toString()) && !mEtPw.getText().toString().matches(regex)){
            showToast("密码必须包含数字和字母");
            return;
        }

        if(mMode == MODE_ADD) {
            addUser();
        }
        if(mMode == MODE_EDIT) {
            editUser();
        }
    }

    @OnClick(R.id.btn_cancel)
    public void clickCancel() {
        finish();
    }

    private void addUser() {
        RequestParamsMap map = new RequestParamsMap();
        map.add("name", mEtName.getText().toString());
        map.add("password", Md5Util.strMd5(mEtPw.getText().toString()));
        map.add("nickname", mEtNickname.getText().toString());
        map.add("creditLimit", mEtCreditLimit.getText().toString());
        request(HttpApi.getUrlWithHost(HttpApi.addMyUser), map, MyUsersEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                MyUsersEntity entity = (MyUsersEntity) object;
                if (entity.isSuccess()) {
                    EventBus.getDefault().post(new EventMyUser(EventMyUser.KEY_REFRESH));
                    finish();
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

    private void editUser() {
        RequestParamsMap map = new RequestParamsMap();
//        map.add("sysId", mUser.getSysId().toString());
        map.add("name", mEtName.getText().toString());
        map.add("nickname", mEtNickname.getText().toString());
        map.add("creditLimit", mEtCreditLimit.getText().toString());
        map.add("createUser", mUser.getCreateUser().toString());
        map.add("eneabled", mCbEneabled.isChecked() ? "1" : "0");
        if(!mUser.getPassword().equals(mEtPw.getText().toString())) {
            map.add("password", Md5Util.strMd5(mEtPw.getText().toString()));
        }
        if(mUser.getEneabled().equals(Short.parseShort("0")) && mCbEneabled.isChecked()) {
            map.add("pwErrCount", "0");
        }
        request(HttpApi.getUrlWithHost(HttpApi.editMyUser), map, MyUsersEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                MyUsersEntity entity = (MyUsersEntity) object;
                if (entity.isSuccess()) {
                    EventBus.getDefault().post(new EventMyUser(EventMyUser.KEY_REFRESH));
                    finish();
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


}
