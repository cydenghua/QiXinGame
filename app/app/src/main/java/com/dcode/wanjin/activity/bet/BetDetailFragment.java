package com.dcode.wanjin.activity.bet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dcode.corelibrary.network.BaseEntity;
import com.dcode.corelibrary.network.RequestCallbackSimply;
import com.dcode.corelibrary.network.RequestParamsMap;
import com.dcode.wanjin.R;
import com.dcode.wanjin.activity.WanjinBaseFragment;
import com.dcode.wanjin.entity.PageInfo;
import com.dcode.wanjin.entity.PeriodBet;
import com.dcode.wanjin.entity.PeriodUserBetEntity;
import com.dcode.wanjin.entity.PeriodUserOdds;
import com.dcode.wanjin.event.EventBetQuick;
import com.dcode.wanjin.event.EventFragmentDetail;
import com.dcode.wanjin.network.HttpApi;
import com.dcode.wanjin.util.StrUtil;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.BindView;
import butterknife.OnClick;


public class BetDetailFragment extends WanjinBaseFragment {

    @BindView(R.id.lv)
    ListView mLv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private int mPage = 1;
    private QuickAdapter<PeriodBet> mAdapter;
    private PeriodUserOdds mPeriodUserOdds;
    private PageInfo<PeriodBet> mLastPage;

    public BetDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        getMyBet();
        return view;
    }

    @Override
    protected int layoutResource() {
        return R.layout.fragment_bet_detail;
    }

    @Override
    protected String getTitle() {
        return "明细";
    }


    @Override
    protected boolean isRegisterEventbus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive(EventFragmentDetail event) {
        mPage = 1;
        getMyBet();
    }

    @Override
    protected void initAdapter() {
        mAdapter = new QuickAdapter<PeriodBet>(mActivity, R.layout.fragment_bet_quick_item) {
            @Override
            protected void convert(BaseAdapterHelper helper, final PeriodBet item) {
                helper.setText(R.id.tv_time, StrUtil.strDateStr(item.getCreatedTime()));
                helper.setVisible(R.id.tv_xian, item.getPlayClass().contains("px") );
                helper.setText(R.id.tv_num, item.getPlayValue());
                helper.setText(R.id.tv_odds, StrUtil.decimalToStr(item.getPlayOdds()));
                helper.setText(R.id.tv_money, StrUtil.intFenToYuan(item.getPlayMoney()));
                helper.setText(R.id.tv_sn, ""+(helper.getPosition()+1));
                helper.setChecked(R.id.cb_back, item.getTmpDeleted() == 1);
                helper.setVisible(R.id.tv_back, !item.getDeleted().equals(Short.parseShort("0")));
                if(!item.isCancelable() && !item.getDeleted().equals(Short.parseShort("1"))) {
                    helper.getView(R.id.cb_back).setVisibility(View.INVISIBLE);
                }else {
                    helper.setVisible(R.id.cb_back, item.getDeleted().equals(Short.parseShort("0")));
                }
                helper.setOnClickListener(R.id.cb_back, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(item.getTmpDeleted() == 1) {
                            item.setTmpDeleted(0);
                        }else {
                            item.setTmpDeleted(1);
                        }
                    }
                });
            }
        };

        mLv.setAdapter(mAdapter);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 1;
                getMyBet();
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if(null != mLastPage && mLastPage.isHasNextPage()) {
                    mPage++;
                    getMyBet();
                }else {
                    mRefreshLayout.finishLoadMore(100);
                    showToast("没有更多了");
                }
            }
        });
    }

    private void getMyBet() {
        RequestParamsMap map = new RequestParamsMap();
        map.add("page", "" + mPage);
        map.add("rows", "20");
        request(HttpApi.getUrlWithHost(HttpApi.getUserPeriodBet), map, PeriodUserBetEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                mRefreshLayout.finishRefresh(100);//传入false表示刷新失败
                mRefreshLayout.finishLoadMore(100);
                PeriodUserBetEntity entity = (PeriodUserBetEntity) object;
                if (entity.isSuccess()) {
                    mLastPage = entity.getData();
                    if(entity.getData().isIsFirstPage()) {
                        mAdapter.replaceAll(entity.getData().getList());
                    }else {
                        mAdapter.addAll(entity.getData().getList());
                    }
                    mRefreshLayout.setEnableLoadMore(mLastPage.isHasNextPage());
                } else {
                    showToast(entity.getMessage());
                }
            }

            @Override
            public boolean onFailure(int statusCode, String response) {
                mRefreshLayout.finishRefresh(100);//传入false表示刷新失败
                mRefreshLayout.finishLoadMore(100);
                return super.onFailure(statusCode, response);
            }
        }, false);
    }

    //退单
    @OnClick(R.id.btn_back)
    public void clickBackBet() {
        if (mAdapter.isEmpty()) {
            return;
        }
        String sid = "";
        for (int i = 0; i < mAdapter.getCount(); i++) {
            if (mAdapter.getItem(i).getTmpDeleted() == 1) {
                if (sid.length() > 0) {
                    sid = sid + "," + mAdapter.getItem(i).getSysId();
                } else {
                    sid = mAdapter.getItem(i).getSysId().toString();
                }
            }
        }
        if (sid.length() < 1) {
            return;
        }

        final String finalSid = sid;
        RequestParamsMap map = new RequestParamsMap();
        map.add("idArr", sid);
        request(HttpApi.getUrlWithHost(HttpApi.deleteUserPeriodBetBatch), map, BaseEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                BaseEntity entity = (BaseEntity) object;
                showToast(entity.getMessage());
                if (entity.isSuccess()) {
                    mPage = 1;
                    //getMyBet(); delete replace by afterBackBetSucc();
                    afterBackBetSucc();
                    EventBus.getDefault().post(new EventBetQuick(EventBetQuick.KEY_BACK_NUMBER, finalSid));
                }
            }

            @Override
            public boolean onFailure(int statusCode, String response) {
                return super.onFailure(statusCode, response);
            }
        }, true);
    }

    private void afterBackBetSucc() {
        for (int i = 0; i < mAdapter.getCount(); i++) {
            if (mAdapter.getItem(i).getTmpDeleted() == 1) {
                mAdapter.getItem(i).setDeleted(new Short("1"));
                mAdapter.getItem(i).setTmpDeleted(0);
            }
        }
        mAdapter.notifyDataSetChanged();
    }


}
