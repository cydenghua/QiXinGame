package com.dcode.wanjin.activity.bet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.dcode.corelibrary.network.RequestCallbackSimply;
import com.dcode.corelibrary.network.RequestParamsMap;
import com.dcode.wanjin.R;
import com.dcode.wanjin.WanjinApplication;
import com.dcode.wanjin.activity.WanjinBaseFragment;
import com.dcode.wanjin.entity.PeriodCount;
import com.dcode.wanjin.entity.PeriodCountEntity;
import com.dcode.wanjin.network.HttpApi;
import com.dcode.wanjin.util.QXConstant;
import com.dcode.wanjin.util.StrUtil;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import butterknife.BindView;

public class PeriodCountFragment extends WanjinBaseFragment {

    @BindView(R.id.lv)
    ListView mLv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private QuickAdapter<PeriodCount> mAdapter;

    public PeriodCountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPeriodCount();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int layoutResource() {
        return R.layout.fragment_period_count;
    }

    @Override
    protected String getTitle() {
        return "账单";
    }

    @Override
    protected void initAdapter() {
        WanjinApplication app = (WanjinApplication)mActivity.getApplication();
        if(QXConstant.LEVEL_HUIYUAN == app.getCurrUser().getLevel()) {
            initAdapterHy();
        }else {
            initAdapterDl();
        }
//        if(QXConstant.LEVEL_DAILI == app.getCurrUser().getLevel()) {
//            initAdapterDl();
//        }

        mLv.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getPeriodCount();
            }
        });
        mRefreshLayout.setEnableLoadMore(false);
    }

    private void initAdapterHy() {
        mAdapter = new QuickAdapter<PeriodCount>(mActivity, R.layout.fragment_period_count_item_hy) {
            @Override
            protected void convert(BaseAdapterHelper helper, PeriodCount item) {

                helper.setText(R.id.tv_period, item.getpId());
                helper.setText(R.id.tv_user_hy, item.getHyName()+"(" + item.getHyNickname() + ")");
                helper.setText(R.id.tv_playmoney, item.getStrPlayMoney());
                helper.setText(R.id.tv_paykf, item.getStrPayKfMoney());

                helper.setText(R.id.tv_rake_hy, item.getStrRakeHy());
                helper.setText(R.id.tv_rake_dl, item.getStrRakeDl());
                helper.setText(R.id.tv_rake_zd, item.getStrRakeZd());

                // 中奖+回水-收单总数
                int k = item.getPayKf()*10 + item.getRakeHy() - item.getPlayMoney()*10;
                helper.setText(R.id.tv_yk, StrUtil.intLiToYuan(k));
            }
        };
//        mLv.addHeaderView();
        View view = LayoutInflater.from(mActivity).inflate(R.layout.fragment_period_count_item_hy, mLv, false);
        mLv.addHeaderView(view);
    }

    private void initAdapterDl() {
        mAdapter = new QuickAdapter<PeriodCount>(mActivity, R.layout.fragment_period_count_item_dl) {
            @Override
            protected void convert(BaseAdapterHelper helper, PeriodCount item) {
                helper.setText(R.id.tv_period, item.getpId());
                helper.setText(R.id.tv_user_hy, item.getHyName()+"(" + item.getHyNickname() + ")");
                helper.setText(R.id.tv_playmoney, item.getStrPlayMoney());
                helper.setText(R.id.tv_paykf, item.getStrPayKfMoney());

                helper.setText(R.id.tv_rake_hy, item.getStrRakeHy());
                helper.setText(R.id.tv_rake_dl, item.getStrRakeDl());
                helper.setText(R.id.tv_rake_zd, item.getStrRakeZd());

                // 中奖+回水-收单总数
                int k = item.getPayKf()*10 + item.getRakeHy() - item.getPlayMoney()*10;
                helper.setText(R.id.tv_yk, StrUtil.intLiToYuan(k));
            }
        };
//        mLv.addHeaderView();
        View view = LayoutInflater.from(mActivity).inflate(R.layout.fragment_period_count_item_dl, mLv, false);
        view.findViewById(R.id.ll_title).setVisibility(View.GONE);
        mLv.addHeaderView(view);
    }

    //获取我的账单
    public void getPeriodCount() {
        RequestParamsMap map = new RequestParamsMap();
        request(HttpApi.getUrlWithHost(HttpApi.getPeriodCount), map, PeriodCountEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                PeriodCountEntity entity = (PeriodCountEntity) object;
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
