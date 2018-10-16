package com.dcode.wanjin.activity.user;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.dcode.corelibrary.network.BaseEntity;
import com.dcode.corelibrary.network.RequestCallbackSimply;
import com.dcode.corelibrary.network.RequestParamsMap;
import com.dcode.wanjin.R;
import com.dcode.wanjin.dialog.UserOddsDialog;
import com.dcode.wanjin.entity.User;
import com.dcode.wanjin.entity.UserOdds;
import com.dcode.wanjin.network.HttpApi;
import com.dcode.wanjin.util.QXConstant;
import com.dcode.wanjin.util.StrUtil;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

public class UserOddsKfActivity extends UserOddsActivity {

    public static void startActivity(Context context, User user) {
        Intent intent = new Intent(context, UserOddsKfActivity.class);
        intent.putExtra(PARAM_USER, user);
        context.startActivity(intent);
    }

    @Override
    protected void initAdapter() {
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
                    helper.setText(R.id.tv_max_odds, StrUtil.numTrim(item.getOddsHy().toString()));
//                    helper.setText(R.id.tv_rake, StrUtil.numTrim(item.getOddsHy().subtract(item.getOddsKf()).toString()));
                    helper.setText(R.id.tv_rake, StrUtil.numTrim(item.getRakeNum(QXConstant.LEVEL_KF).toString()));
                    helper.setText(R.id.tv_curr_odds, StrUtil.numTrim(item.getOddsKf().toString()));
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
                        UserOddsDialog dia = new UserOddsDialog(UserOddsKfActivity.this,
                                item.getPlayTypeName()+"  (赔率修改)",
                                StrUtil.numTrim(item.getOddsHy().toString()),
                                getListData(item.getOddsHy(), item.getPlayTypeClass()),
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

    @Override
    protected void saveUserOdds(String userId, String playType, String newOdds) {
        RequestParamsMap map = new RequestParamsMap();
        map.add("userId", userId);
        map.add("playType", playType);
        map.add("curOdds", newOdds);
        request(HttpApi.getUrlWithHost(HttpApi.editUserKfOdds), map, BaseEntity.class, new RequestCallbackSimply() {
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
}
