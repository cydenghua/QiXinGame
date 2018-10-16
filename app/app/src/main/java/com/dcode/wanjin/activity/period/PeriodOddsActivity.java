package com.dcode.wanjin.activity.period;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.dcode.corelibrary.network.BaseEntity;
import com.dcode.corelibrary.network.RequestCallbackSimply;
import com.dcode.corelibrary.network.RequestParamsMap;
import com.dcode.wanjin.R;
import com.dcode.wanjin.WanjinApplication;
import com.dcode.wanjin.activity.WanJinBaseActivity;
import com.dcode.wanjin.entity.PeriodEntity;
import com.dcode.wanjin.entity.PeriodOdds;
import com.dcode.wanjin.entity.PeriodOddsEntity;
import com.dcode.wanjin.entity.QxPeriod;
import com.dcode.wanjin.entity.User;
import com.dcode.wanjin.event.EventPeriod;
import com.dcode.wanjin.event.EventPeriodOdds;
import com.dcode.wanjin.network.HttpApi;
import com.dcode.wanjin.util.QXConstant;
import com.dcode.wanjin.util.StrUtil;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class PeriodOddsActivity extends WanJinBaseActivity {


    @BindView(R.id.tv_tips)
    TextView mTvTips;
    @BindView(R.id.lv)
    ListView mLv;

    private QxPeriod mPeriodLast;
    private QuickAdapter<PeriodOdds> mAdapter;
    private User mUser;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PeriodOddsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WanjinApplication app = (WanjinApplication)getApplication();
        mUser = app.getCurrUser();
        if(mUser.getLevel() < QXConstant.LEVEL_ZHONGDAI) {
            setTopRightText("增加");
        }
        mTvTips.setVisibility(mUser.getLevel() < QXConstant.LEVEL_ZHONGDAI ? View.VISIBLE : View.GONE);

        getPeriodRecent();
    }

    @Override
    protected int layoutResource() {
        return R.layout.activity_period_odds;
    }

    @Override
    protected String topTitle() {
        return "赔率变动";
    }

    @Override
    protected boolean isRegisterEventbus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive(EventPeriodOdds event) {
        getPeriodOdds();
    }

    @Override
    protected void initAdapter() {
        mAdapter = new QuickAdapter<PeriodOdds>(this, R.layout.activity_period_odds_item) {
            @Override
            protected void convert(BaseAdapterHelper helper, PeriodOdds item) {
                helper.setText(R.id.tv_odds,"赔率："+ StrUtil.decimalToStr(item.getOddsNew()));
                helper.setText(R.id.tv_value, "号码："+item.getpValue());
                helper.setText(R.id.tv_class, "类型："+item.getPlayClassName());
            }
        };

        mLv.setAdapter(mAdapter);
    }

    protected void getPeriodOdds() {
        RequestParamsMap map = new RequestParamsMap();
        map.add("periodId", mPeriodLast.getSysId().toString());
        request(HttpApi.getUrlWithHost(HttpApi.getPeriodOdds), map, PeriodOddsEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                PeriodOddsEntity entity = (PeriodOddsEntity) object;
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

    private void getPeriodRecent() {
        RequestParamsMap map = new RequestParamsMap();
        request(HttpApi.getUrlWithHost(HttpApi.getPeriodRecent), map, PeriodEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                PeriodEntity entity = (PeriodEntity) object;
                if (entity.isSuccess()) {
                    if(!entity.getData().isEmpty()) {
                        mPeriodLast = entity.getData().get(0);
                        setTopTitle("赔率变动 (第" + mPeriodLast.getpId()  + "期)");
                        getPeriodOdds();
                    }
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

    @Override
    protected void dealTopRightClickEvent() {
        super.dealTopRightClickEvent();
        PeriodOddsEditActivity.startActivity(this, mPeriodLast.getSysId().toString());
    }
}
