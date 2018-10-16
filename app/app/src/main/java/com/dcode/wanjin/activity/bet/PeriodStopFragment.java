package com.dcode.wanjin.activity.bet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dcode.corelibrary.network.BaseEntity;
import com.dcode.corelibrary.network.RequestCallbackSimply;
import com.dcode.corelibrary.network.RequestParamsMap;
import com.dcode.wanjin.R;
import com.dcode.wanjin.activity.WanjinBaseFragment;
import com.dcode.wanjin.entity.PeriodStop;
import com.dcode.wanjin.entity.PeriodStopEntity;
import com.dcode.wanjin.network.HttpApi;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;


public class PeriodStopFragment extends WanjinBaseFragment {


    @BindView(R.id.lv)
    ListView mLv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private QuickAdapter<PeriodStop> mAdapter;

    public PeriodStopFragment() {
        // Required empty public constructor
    }

    public static PeriodStopFragment newInstance() {
        PeriodStopFragment fragment = new PeriodStopFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPeriodStop();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int layoutResource() {
        return R.layout.fragment_period_stop;
    }

    @Override
    protected String getTitle() {
        return "停押";
    }

    @Override
    protected void initAdapter() {
        super.initAdapter();
        mAdapter = new QuickAdapter<PeriodStop>(mActivity, R.layout.fragment_period_stop_item) {
            @Override
            protected void convert(BaseAdapterHelper helper, PeriodStop item) {
                helper.setText(R.id.tv_time, "停押时间:" + item.getModifyTime());
//                helper.setText(R.id.tv_value, "停押号码:" + item.getpValue());
                helper.setText(R.id.tv_value, item.getpValue());
                if(item.getIsXian().equals(new Short("1"))) {
                    helper.setText(R.id.tv_value, item.getpValue() + "现");
                }
            }
        };

        mLv.setAdapter(mAdapter);
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
        }, false);
    }
}
