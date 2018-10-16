package com.dcode.wanjin.activity.user;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.dcode.corelibrary.network.BaseEntity;
import com.dcode.corelibrary.network.RequestCallbackSimply;
import com.dcode.corelibrary.network.RequestParamsMap;
import com.dcode.wanjin.R;
import com.dcode.wanjin.activity.WanJinBaseActivity;
import com.dcode.wanjin.dialog.UserOddsDialog;
import com.dcode.wanjin.entity.User;
import com.dcode.wanjin.entity.UserOdds;
import com.dcode.wanjin.entity.UserOddsEntity;
import com.dcode.wanjin.network.HttpApi;
import com.dcode.wanjin.util.QXConstant;
import com.dcode.wanjin.util.StrUtil;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UserOddsActivity extends WanJinBaseActivity {

    protected static final String PARAM_USER = "p_user";

    @BindView(R.id.lv)
    ListView mLv;

    protected User mUser;
    protected QuickAdapter<UserOdds> mAdapter;

    public static void startActivity(Context context, User user) {
        Intent intent = new Intent(context, UserOddsActivity.class);
        intent.putExtra(PARAM_USER, user);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = getIntent().getParcelableExtra(PARAM_USER);
        setTopTitle("账号：" + mUser.getName() + ", 赔率设置(长按可修改赔率)");
        getUserOdds();
    }

    @Override
    protected int layoutResource() {
        return R.layout.activity_user_odds;
    }

    @Override
    protected String topTitle() {
        return "赔率设置(长按可修改赔率)";
    }

    @Override
    protected boolean screenOrientationPortrait() {
        return false;
    }

    @Override
    protected void initAdapter() {
        super.initAdapter();
        mAdapter = new QuickAdapter<UserOdds>(this, R.layout.activity_user_odds_item) {
            @Override
            protected void convert(BaseAdapterHelper helper, final UserOdds item) {
                if(item.getoId() > 10) {
                    helper.setTextColor(R.id.tv_name, getResources().getColor(R.color.orange));
                }else {
                    helper.setTextColor(R.id.tv_name, getResources().getColor(R.color.text_black));
                }

                if(item.getoId() > 0) {
                    helper.setText(R.id.tv_name, item.getPlayTypeName());
                    helper.setText(R.id.tv_min, StrUtil.intFenToYuan(item.getMinPlay()));
                    helper.setText(R.id.tv_max, StrUtil.intFenToYuan(item.getMaxPlay()));
                    helper.setText(R.id.tv_class_max, StrUtil.intFenToYuan(item.getMaxClassPlay()));
                    helper.setText(R.id.tv_max_odds, StrUtil.numTrim(item.getMaxOdds(mUser.getLevel()).toString()));
                    helper.setText(R.id.tv_rake, StrUtil.numTrim(item.getRakeNum(mUser.getLevel()).toString()));
                    helper.setText(R.id.tv_curr_odds, StrUtil.numTrim(item.getMyOdds(mUser.getLevel()).toString()));
                }else {
                    helper.setText(R.id.tv_name, item.getPlayTypeName());
                    helper.setTextColor(R.id.tv_name, getResources().getColor(R.color.orange));
                    helper.setText(R.id.tv_min, "");
                    helper.setText(R.id.tv_max, "");
                    helper.setText(R.id.tv_class_max, "");
                    helper.setText(R.id.tv_max_odds, "");
                    helper.setText(R.id.tv_rake, "");
                    helper.setText(R.id.tv_curr_odds, "");
                }

                helper.setOnLongClickListener(R.id.item, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if(mUser.getLevel() == QXConstant.LEVEL_GUDONG || mUser.getLevel() == QXConstant.LEVEL_ADMIN) {
                            return false;
                        }
                        UserOddsDialog dia = new UserOddsDialog(UserOddsActivity.this,
                                item.getPlayTypeName()+"  (赔率修改)",
                                StrUtil.numTrim(item.getMyOdds(mUser.getLevel()).toString()),
                                getListData(item.getMaxOdds(mUser.getLevel()), item.getPlayTypeClass()),
                                new UserOddsDialog.OnChooseListener() {
                                    @Override
                                    public void getValue(String value) {
                                        saveUserOdds(item.getUserId().toString(), item.getPlayType(), value);
                                    }
                                }
                        );
                        dia.show();
                        return false;
                    }
                });
            }
        };

        mLv.setAdapter(mAdapter);
    }

    protected void getUserOdds() {
        RequestParamsMap map = new RequestParamsMap();
        map.add("userName", mUser.getName());
        request(HttpApi.getUrlWithHost(HttpApi.getUserOdds), map, UserOddsEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                UserOddsEntity entity = (UserOddsEntity) object;
                if (entity.isSuccess()) {
                    addNameOddsRow(entity.getData());
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

    protected void saveUserOdds(String userId, String playType, String newOdds) {
        RequestParamsMap map = new RequestParamsMap();
        map.add("userId", userId);
        map.add("playType", playType);
        map.add("curOdds", newOdds);
        request(HttpApi.getUrlWithHost(HttpApi.editUserOdds), map, BaseEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                getUserOdds();
                BaseEntity entity = (BaseEntity) object;
                showToast(entity.getMessage());
            }

            @Override
            public boolean onFailure(int statusCode, String response) {
                return super.onFailure(statusCode, response);
            }
        }, true);
    }

    private void addNameOddsRow(List<UserOdds> oddsList) {
        UserOdds os = new UserOdds();
        os.setoId(Integer.parseInt("-1"));
        os.setPlayTypeName("二定位");
        oddsList.add(0, os);

        os = new UserOdds();
        os.setoId(Integer.parseInt("-1"));
        os.setPlayTypeName("三定位");
        oddsList.add(7, os);
    }

    protected List<String> getListData(BigDecimal maxOdds, String pType){
        BigDecimal minOdds = new BigDecimal("0");
        BigDecimal stepLen = new BigDecimal("0");
        //二定位
        if(QXConstant.CLASS_2P.equals(pType)) {
            minOdds = BigDecimal.valueOf(80);
            stepLen = BigDecimal.valueOf(0.1);
        }
        //三定位
        if(QXConstant.CLASS_3P.equals(pType)) {
            minOdds = BigDecimal.valueOf(750);
            stepLen = BigDecimal.valueOf(1);
        }
        //四定位
        if(QXConstant.CLASS_4P.equals(pType)) {
            minOdds = BigDecimal.valueOf(7000);
            stepLen = BigDecimal.valueOf(10);
        }
        //二字现
        if(QXConstant.CLASS_2PX.equals(pType)) {
            minOdds = BigDecimal.valueOf(8);
            stepLen = BigDecimal.valueOf(0.01);
        }
        //三字现
        if(QXConstant.CLASS_3PX.equals(pType)) {
            minOdds = BigDecimal.valueOf(40);
            stepLen = BigDecimal.valueOf(0.05);
        }
        //四字现
        if(QXConstant.CLASS_4PX.equals(pType)) {
            minOdds = BigDecimal.valueOf(300);
            stepLen = BigDecimal.valueOf(0.4);
        }

        maxOdds = maxOdds.add(stepLen);
        List<String> listData = new ArrayList<String>();
        for (BigDecimal i = minOdds; maxOdds.compareTo(i)==1; i=i.add(stepLen)) {
            listData.add(i.toString());
        }

        return listData;
    }

}
