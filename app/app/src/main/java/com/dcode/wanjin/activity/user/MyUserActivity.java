package com.dcode.wanjin.activity.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dcode.corelibrary.network.RequestCallbackSimply;
import com.dcode.corelibrary.network.RequestParamsMap;
import com.dcode.wanjin.R;
import com.dcode.wanjin.activity.WanJinBaseActivity;
import com.dcode.wanjin.entity.MyUsersEntity;
import com.dcode.wanjin.entity.SelfUserInfoEntity;
import com.dcode.wanjin.entity.User;
import com.dcode.wanjin.event.EventMyUser;
import com.dcode.wanjin.network.HttpApi;
import com.dcode.wanjin.util.StrUtil;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class MyUserActivity extends WanJinBaseActivity {

    private static final int MENU_ID_DETAIL = 1;
    private static final int MENU_ID_EDIT = 2;
    private static final int MENU_ID_ODDS = 3;

    @BindView(R.id.lv)
    ListView mListView;
    @BindView(R.id.tv_my_name)
    TextView mTvMyName;
    @BindView(R.id.tv_my_credit)
    TextView mTvMyCredit;
    @BindView(R.id.tv_my_credit_left)
    TextView mTvMyCreditLeft;

    private QuickAdapter<User> mAdapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MyUserActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopTitle("下级管理");
        setTopRightText("增加");
        getSelfUserInfo();
        getMyUser();
    }

    @Override
    protected int layoutResource() {
        return R.layout.activity_my_user;
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
    public void onReceive(EventMyUser event) {
        getMyUser();
        getSelfUserInfo();
    }

    @Override
    protected void initAdapter() {
        super.initAdapter();
        mAdapter = new QuickAdapter<User>(this, R.layout.activity_my_user_item) {
            @Override
            protected void convert(BaseAdapterHelper helper, User item) {
                helper.setText(R.id.tv_name, item.getName());
                helper.setText(R.id.tv_nickname, item.getNickname());
                helper.setText(R.id.tv_credit_limit, StrUtil.intFenToYuan(item.getCreditLimit()));
                helper.setChecked(R.id.cb_deleted, item.getEneabled() > 0);

                helper.setOnLongClickListener(R.id.item, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        //必须有此代码才能触发长按事件？？？？？？？？？？？
                        return false;
                    }
                });
            }
        };
        mListView.setAdapter(mAdapter);

        mListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.setHeaderTitle("选择操作");
//                contextMenu.add(0, MENU_ID_DETAIL, 0, "查看详情");
                contextMenu.add(0, MENU_ID_EDIT, 0, "修改会员");
                contextMenu.add(0, MENU_ID_ODDS, 0, "赔率设置");
            }
        });
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        long id = info.id;//当前项的key标示，独一无二的
//        int position = info.position;//当前项在ListView中的位置
//        showToast("pos is "  + position + "   id is " + id + " item id is " + item.getItemId());

        User user = mAdapter.getItem(info.position);
        if (item.getItemId() == MENU_ID_DETAIL) {
            //
        } else if (item.getItemId() == MENU_ID_EDIT) {
            AddUserActivity.startActivityEdit(MyUserActivity.this, user);
        } else if (item.getItemId() == MENU_ID_ODDS) {
            UserOddsActivity.startActivity(MyUserActivity.this, user);
        }

//        return super.onContextItemSelected(item);
        return true;
    }

    @Override
    protected void dealTopRightClickEvent() {
        super.dealTopRightClickEvent();
        AddUserActivity.startActivityAdd(this);
    }

    private void getMyUser() {
        RequestParamsMap map = new RequestParamsMap();
        request(HttpApi.getUrlWithHost(HttpApi.getMyUsers), map, MyUsersEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                MyUsersEntity entity = (MyUsersEntity) object;
                if (entity.isSuccess()) {
                    mAdapter.replaceAll(entity.getData());
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

    private void getSelfUserInfo() {
        RequestParamsMap map = new RequestParamsMap();
        request(HttpApi.getUrlWithHost(HttpApi.getSelfUserInfo), map, SelfUserInfoEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                SelfUserInfoEntity entity = (SelfUserInfoEntity) object;
                if (entity.isSuccess()) {
                    mTvMyName.setText("我的账号：" + entity.getData().getName());
                    mTvMyCredit.setText("        信用额度：" + StrUtil.intFenToYuan(entity.getData().getCreditLimit()));
                    mTvMyCreditLeft.setText("        信用额度剩余：" + StrUtil.intFenToYuan(entity.getData().getCreditLimitLeft()));
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
