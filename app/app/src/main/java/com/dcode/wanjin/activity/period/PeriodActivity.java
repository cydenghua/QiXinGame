package com.dcode.wanjin.activity.period;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.dcode.corelibrary.network.RequestCallbackSimply;
import com.dcode.corelibrary.network.RequestParamsMap;
import com.dcode.wanjin.R;
import com.dcode.wanjin.WanjinApplication;
import com.dcode.wanjin.activity.WanJinBaseActivity;
import com.dcode.wanjin.entity.PeriodEntity;
import com.dcode.wanjin.entity.QxPeriod;
import com.dcode.wanjin.entity.User;
import com.dcode.wanjin.event.EventPeriod;
import com.dcode.wanjin.network.HttpApi;
import com.dcode.wanjin.util.QXConstant;
import com.dcode.wanjin.util.StrUtil;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

public class PeriodActivity extends WanJinBaseActivity {

    @BindView(R.id.lv)
    ListView mListView;
    @BindView(R.id.tv_tips)
    TextView mTvTips;

    private User mUser;
    private QuickAdapter<QxPeriod> mAdapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PeriodActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WanjinApplication app = (WanjinApplication)getApplication();
        mUser = app.getCurrUser();

        if(mUser.getLevel() < QXConstant.LEVEL_ZHONGDAI) {
            setTopRightText("增加奖期");
        }
        mTvTips.setVisibility(mUser.getLevel() < QXConstant.LEVEL_ZHONGDAI ? View.VISIBLE : View.GONE);

        getPeriodRecent();
    }

    @Override
    protected int layoutResource() {
        return R.layout.activity_period;
    }

    @Override
    protected String topTitle() {
        return "开奖号码";
    }

    @Override
    protected boolean isRegisterEventbus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive(EventPeriod event) {
        getPeriodRecent();
    }

    @Override
    protected void initAdapter() {
        super.initAdapter();
        mAdapter = new QuickAdapter<QxPeriod>(this, R.layout.activity_period_item) {
            @Override
            protected void convert(BaseAdapterHelper helper, final QxPeriod item) {
                helper.setText(R.id.tv_pid, "期号：" + item.getpId().toString());
                helper.setText(R.id.tv_ptime, "开奖时间：" + item.getpTime().substring(0, 10));
                helper.setText(R.id.tv_p1, getNum(item.getP1()));
                helper.setText(R.id.tv_p2, getNum(item.getP2()));
                helper.setText(R.id.tv_p3, getNum(item.getP3()));
                helper.setText(R.id.tv_p4, getNum(item.getP4()));
                helper.setText(R.id.tv_p5, getNum(item.getP5()));
                helper.setText(R.id.tv_p6, getNum(item.getP6()));
                helper.setText(R.id.tv_p7, getNum(item.getP7()));

                if(item.getStatus() < QXConstant.PERIOD_STATUS_OUT) {
                    helper.setBackgroundRes(R.id.tv_p1, R.drawable.bg_circle_2);
                    helper.setBackgroundRes(R.id.tv_p2, R.drawable.bg_circle_2);
                    helper.setBackgroundRes(R.id.tv_p3, R.drawable.bg_circle_2);
                    helper.setBackgroundRes(R.id.tv_p4, R.drawable.bg_circle_2);
                    helper.setBackgroundRes(R.id.tv_p5, R.drawable.bg_circle_2);
                    helper.setBackgroundRes(R.id.tv_p6, R.drawable.bg_circle_2);
                    helper.setBackgroundRes(R.id.tv_p7, R.drawable.bg_circle_2);
                }

                helper.setOnLongClickListener(R.id.item, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        WanjinApplication app = WanjinApplication.getInstance();
                        if(app.getCurrUser().getLevel() == QXConstant.LEVEL_GUDONG){
                            PeriodEditActivity.startActivityEdit(PeriodActivity.this, item);
                        }
                        return false;
                    }
                });
            }
        };

        mListView.setAdapter(mAdapter);
    }

    private String getNum(Short num) {
        return  num == null ? "-" : StrUtil.shortToStr(num);
    }

    @Override
    protected void dealTopRightClickEvent() {
        super.dealTopRightClickEvent();

        if(!mAdapter.isEmpty() && mAdapter.getItem(0).getStatus() < QXConstant.PERIOD_STATUS_OUT) {
            showToast("请先设置上一期为结束状态.");
            return;
        }

        //取上一个奖期的下一个开奖日期
        QxPeriod mp = new QxPeriod();
        mp.setStatus(Short.parseShort("" + QXConstant.PERIOD_STATUS_ING));
        mp.setpTime(StrUtil.dateToStr(new Date(System.currentTimeMillis())));
        if(!mAdapter.isEmpty()) {
            Calendar cd = Calendar.getInstance();
            cd.setTime(StrUtil.strToDate(mAdapter.getItem(0).getpTime()));
            for (int i = 1; i < 4; i++) {
                cd.add(Calendar.DATE, 1);
                //是否周日， 周二， 周五
                if(cd.get(Calendar.DAY_OF_WEEK) == 1
                        || cd.get(Calendar.DAY_OF_WEEK) == 3
                        || cd.get(Calendar.DAY_OF_WEEK) == 6) {
                    break;
                }
            }
            mp.setpTime(StrUtil.dateToStr(cd.getTime()));  //取上一个奖期的下一个开奖日期
            mp.setpId(mAdapter.getItem(0).getpId()+1);  //新建奖期， 默认把上期号码+1
        }

        PeriodEditActivity.startActivityAdd(PeriodActivity.this, mp);

    }

    private void getPeriodRecent() {
        RequestParamsMap map = new RequestParamsMap();
        request(HttpApi.getUrlWithHost(HttpApi.getPeriodRecent), map, PeriodEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                PeriodEntity entity = (PeriodEntity) object;
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

}
