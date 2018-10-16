package com.dcode.wanjin.activity.period;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ListView;

import com.dcode.corelibrary.network.BaseEntity;
import com.dcode.corelibrary.network.RequestCallbackSimply;
import com.dcode.corelibrary.network.RequestParamsMap;
import com.dcode.wanjin.R;
import com.dcode.wanjin.activity.WanJinBaseActivity;
import com.dcode.wanjin.dialog.InputValueDialog;
import com.dcode.wanjin.entity.PeriodStop;
import com.dcode.wanjin.entity.PeriodStopEntity;
import com.dcode.wanjin.network.HttpApi;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.OnClick;

public class PeriodStopActivity extends WanJinBaseActivity {

    @BindView(R.id.lv)
    ListView mLv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private QuickAdapter<PeriodStop> mAdapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PeriodStopActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPeriodStop();
    }

    @Override
    protected int layoutResource() {
        return R.layout.activity_period_stop;
    }

    @Override
    protected String topTitle() {
        return "停押设置";
    }

    @Override
    protected void initAdapter() {
        super.initAdapter();
        mAdapter = new QuickAdapter<PeriodStop>(this, R.layout.fragment_period_stop_item) {
            @Override
            protected void convert(BaseAdapterHelper helper, PeriodStop item) {
                helper.setText(R.id.tv_time, "生效时间:" + item.getModifyTime());
                helper.setText(R.id.tv_value, item.getpValue());
                if(item.getIsXian().equals(new Short("1"))) {
                    helper.setText(R.id.tv_value, item.getpValue() + "现");
                }
            }
        };

        mLv.setAdapter(mAdapter);
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getPeriodStop();
            }
        });
    }

    private void getPeriodStop() {
        RequestParamsMap map = new RequestParamsMap();
        request(HttpApi.getUrlWithHost(HttpApi.getPeriodStopCurrPeriod), map, PeriodStopEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                PeriodStopEntity entity = (PeriodStopEntity) object;
                if(entity.isSuccess()) {
                    mAdapter.replaceAll(entity.getData());
                }else {
                    showToast(entity.getMessage());
                }
                mRefreshLayout.finishRefresh();
            }

            @Override
            public boolean onFailure(int statusCode, String response) {
                mRefreshLayout.finishRefresh();
                return super.onFailure(statusCode, response);
            }
        }, true);
    }

    private void addPeriodStop(String value, String isXian) {
        RequestParamsMap map = new RequestParamsMap();
        map.add("pValue", value);
        map.add("isXian", isXian);
        request(HttpApi.getUrlWithHost(HttpApi.addPeriodStop), map, BaseEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                BaseEntity entity = (BaseEntity) object;
                if(entity.isSuccess()) {
                    getPeriodStop();
                }
                showToast(entity.getMessage());
            }
            @Override
            public boolean onFailure(int statusCode, String response) {
                return super.onFailure(statusCode, response);
            }
        }, true);
    }

    private void deletePeriodStop(String value, String isXian) {
        RequestParamsMap map = new RequestParamsMap();
        map.add("pValue", value);
        map.add("isXian", isXian);
        request(HttpApi.getUrlWithHost(HttpApi.deletePeriodStop), map, BaseEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                BaseEntity entity = (BaseEntity) object;
                if(entity.isSuccess()) {
                    getPeriodStop();
                }
                showToast(entity.getMessage());
            }
            @Override
            public boolean onFailure(int statusCode, String response) {
                return super.onFailure(statusCode, response);
            }
        }, true);
    }

    @OnClick(R.id.btn_add)
    public void clidkAdd() {
        new InputValueDialog(PeriodStopActivity.this, new InputValueDialog.OnGetValueListener() {
            @Override
            public void getValue(String value, String isXian) {
                addPeriodStop(value, isXian);
            }
        }).show();
    }

    @OnClick(R.id.btn_delete)
    public void clidkDelete() {
        new InputValueDialog(PeriodStopActivity.this, new InputValueDialog.OnGetValueListener() {
            @Override
            public void getValue(String value, String isXian) {
                deletePeriodStop(value, isXian);
            }
        }).show();
    }

}
