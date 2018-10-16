package com.dcode.wanjin.activity.report;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.dcode.corelibrary.network.RequestCallbackSimply;
import com.dcode.corelibrary.network.RequestParamsMap;
import com.dcode.wanjin.R;
import com.dcode.wanjin.WanjinApplication;
import com.dcode.wanjin.activity.WanJinBaseActivity;
import com.dcode.wanjin.entity.PeriodCount;
import com.dcode.wanjin.entity.PeriodCountGroup;
import com.dcode.wanjin.entity.PeriodCountGroupEntity;
import com.dcode.wanjin.network.HttpApi;
import com.dcode.wanjin.util.QXConstant;
import com.dcode.wanjin.util.StrUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.List;

import butterknife.BindView;

public class UserBillActivity extends WanJinBaseActivity {

    @BindView(R.id.lv)
    ExpandableListView mLv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private int mUserLevel = 0;
    private boolean mGetData = false;
    private List<PeriodCountGroup> mGroupData;
    private MyExpandableAdapter mAdapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, UserBillActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WanjinApplication app = (WanjinApplication) getApplication();
        mUserLevel = app.getCurrUser().getLevel();
        if(!mGetData) {
            mGetData = true;
            getPeriodCount();
        }
    }


    @Override
    protected int layoutResource() {
        return R.layout.activity_user_bill;
    }

    @Override
    protected String topTitle() {
        return "账单";
    }

    @Override
    protected boolean screenOrientationPortrait() {
        return false;
    }

    private void createAdapter() {
        if(mAdapter != null) { return; }
        mAdapter = new MyExpandableAdapter();
        mLv.setAdapter(mAdapter);

        View view = LayoutInflater.from(this).inflate(R.layout.activity_user_bill_item_title, mLv, false);
        view.findViewById(R.id.ll_dl).setVisibility(mUserLevel < QXConstant.LEVEL_HUIYUAN ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.ll_zd).setVisibility(mUserLevel < QXConstant.LEVEL_DAILI ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.ll_gd).setVisibility(mUserLevel < QXConstant.LEVEL_ZHONGDAI ? View.VISIBLE : View.GONE);
        mLv.addHeaderView(view);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getPeriodCount();
            }
        });
        mRefreshLayout.setEnableLoadMore(false);
    }

    //获取我的账单
    public void getPeriodCount() {
        RequestParamsMap map = new RequestParamsMap();
        request(HttpApi.getUrlWithHost(HttpApi.getPeriodCountGroup), map, PeriodCountGroupEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                PeriodCountGroupEntity entity = (PeriodCountGroupEntity) object;
                if(entity.isSuccess()) {
//                    mAdapter.replaceAll(entity.getData());
                    mGroupData = entity.getData();
                    createAdapter();
                    mAdapter.notifyDataSetChanged();
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


    private class MyExpandableAdapter extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {
            return mGroupData.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return mGroupData.get(i).getPeriodCountDetail().size();
        }

        @Override
        public Object getGroup(int i) {
            return mGroupData.get(i).getPeriodCountGroup();
        }

        @Override
        public Object getChild(int i, int i1) {
            return mGroupData.get(i).getPeriodCountDetail().get(i1);
        }

        @Override
        public long getGroupId(int i) {
            return mGroupData.get(i).getPeriodCountGroup().getPeriodId();
        }

        @Override
        public long getChildId(int i, int i1) {
            return mGroupData.get(i).getPeriodCountDetail().get(i1).getHyId();
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            View convertView = view;
            ItemDataHolder holder = null;
            if(convertView == null){
                holder = new ItemDataHolder();
                convertView = LayoutInflater.from(UserBillActivity.this).inflate(R.layout.activity_user_bill_item, null);
                holder.tvDate = convertView.findViewById(R.id.tv_date);
                holder.tvHyTotal = convertView.findViewById(R.id.tv_hy_total);
                holder.tvHyRes = convertView.findViewById(R.id.tv_hy_res);
                holder.tvDlTotal = convertView.findViewById(R.id.tv_dl_total);
                holder.tvDlRes = convertView.findViewById(R.id.tv_dl_res);
                holder.tvZdRake = convertView.findViewById(R.id.tv_zd_rake);
                holder.tvZdRes = convertView.findViewById(R.id.tv_zd_res);
                holder.tvGdTotal = convertView.findViewById(R.id.tv_gd_total);
                holder.tvGdRes = convertView.findViewById(R.id.tv_gd_res);
                convertView.setTag(holder);

                convertView.findViewById(R.id.ll_dl).setVisibility(mUserLevel < QXConstant.LEVEL_HUIYUAN ? View.VISIBLE : View.GONE);
                convertView.findViewById(R.id.ll_zd).setVisibility(mUserLevel < QXConstant.LEVEL_DAILI ? View.VISIBLE : View.GONE);
                convertView.findViewById(R.id.ll_gd).setVisibility(mUserLevel < QXConstant.LEVEL_ZHONGDAI ? View.VISIBLE : View.GONE);
            }else{
                holder = (ItemDataHolder)convertView.getTag();
            }

            //判断是否已经打开列表
//            if(isExpanded){
//                holder.arrow.setBackgroundResource(R.drawable.dowm_arrow);
//            }else{
//                holder.arrow.setBackgroundResource(R.drawable.right_arrow);
//            }


            PeriodCount itemData = mGroupData.get(i).getPeriodCountGroup();
            holder.tvDate.setText("[" + (i+1) + "]  " + itemData.getpTimeShort() + "(" + itemData.getpId() + ")");

            //会员
            holder.tvHyTotal.setText(itemData.getStrPlayMoney());
            int k = itemData.getPayKf()*10 + itemData.getRakeHy() - itemData.getPlayMoney()*10;// 中奖+回水-收单总数
            holder.tvHyRes.setText(StrUtil.intLiToYuan(k));

            //代理
            holder.tvDlTotal.setText(itemData.getStrPlayMoney());
            if(k > 0) {
                k = k - itemData.getRakeDl();// k - 代理回水
            }else {
                k = k + itemData.getRakeDl();// k + 代理回水
            }
            holder.tvDlRes.setText(StrUtil.intLiToYuan(k));

            //总代
            holder.tvZdRake.setText(itemData.getStrRakeZd());
            if(k > 0) {
                k = k - itemData.getRakeZd();// k - 回水
            }else {
                k = k + itemData.getRakeZd();// k + 回水
            }
            holder.tvZdRes.setText(StrUtil.intLiToYuan(k));

            //股东
            holder.tvGdTotal.setText(itemData.getStrPlayMoney());
            k = k*-1;
            holder.tvGdRes.setText(StrUtil.intLiToYuan(k));

            return convertView;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            View convertView = view;
            ItemDataHolder holder = null;
            if(convertView == null){
                holder = new ItemDataHolder();
                convertView = LayoutInflater.from(UserBillActivity.this).inflate(R.layout.activity_user_bill_item, null);
                holder.tvDate = convertView.findViewById(R.id.tv_date);
                holder.tvHyTotal = convertView.findViewById(R.id.tv_hy_total);
                holder.tvHyRes = convertView.findViewById(R.id.tv_hy_res);
                holder.tvDlTotal = convertView.findViewById(R.id.tv_dl_total);
                holder.tvDlRes = convertView.findViewById(R.id.tv_dl_res);
                holder.tvZdRake = convertView.findViewById(R.id.tv_zd_rake);
                holder.tvZdRes = convertView.findViewById(R.id.tv_zd_res);
                holder.tvGdTotal = convertView.findViewById(R.id.tv_gd_total);
                holder.tvGdRes = convertView.findViewById(R.id.tv_gd_res);
                convertView.setTag(holder);
                convertView.findViewById(R.id.ll_dl).setVisibility(mUserLevel < QXConstant.LEVEL_HUIYUAN ? View.VISIBLE : View.GONE);
                convertView.findViewById(R.id.ll_zd).setVisibility(mUserLevel < QXConstant.LEVEL_DAILI ? View.VISIBLE : View.GONE);
                convertView.findViewById(R.id.ll_gd).setVisibility(mUserLevel < QXConstant.LEVEL_ZHONGDAI ? View.VISIBLE : View.GONE);

            }else{
                holder = (ItemDataHolder)convertView.getTag();
            }

            //判断是否已经打开列表
//            if(isExpanded){
//                holder.arrow.setBackgroundResource(R.drawable.dowm_arrow);
//            }else{
//                holder.arrow.setBackgroundResource(R.drawable.right_arrow);
//            }


            holder.tvDate.setBackgroundColor(getResources().getColor(R.color.orangeLight));

            PeriodCount itemData = mGroupData.get(i).getPeriodCountDetail().get(i1);
            holder.tvDate.setText(itemData.getHyName()+"(" + itemData.getHyNickname() + ")");

            //会员
            holder.tvHyTotal.setText(itemData.getStrPlayMoney());
            int k = itemData.getPayKf()*10 + itemData.getRakeHy() - itemData.getPlayMoney()*10;// 中奖+回水-收单总数
            holder.tvHyRes.setText(StrUtil.intLiToYuan(k));

            //代理
            holder.tvDlTotal.setText(itemData.getStrPlayMoney());
            if(k > 0) {
                k = k - itemData.getRakeDl();// k - 代理回水
            }else {
                k = k + itemData.getRakeDl();// k + 代理回水
            }
            holder.tvDlRes.setText(StrUtil.intLiToYuan(k));

            //总代
            holder.tvZdRake.setText(itemData.getStrRakeZd());
            if(k > 0) {
                k = k - itemData.getRakeZd();// k - 回水
            }else {
                k = k + itemData.getRakeZd();// k + 回水
            }
            holder.tvZdRes.setText(StrUtil.intLiToYuan(k));

            //股东
            holder.tvGdTotal.setText(itemData.getStrPlayMoney());
            k = k*-1;
            holder.tvGdRes.setText(StrUtil.intLiToYuan(k));

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return false;
        }
    }

    private class ItemDataHolder{
        public TextView tvDate;
        public TextView tvHyTotal;
        public TextView tvHyRes;
        public TextView tvDlTotal;
        public TextView tvDlRes;
        public TextView tvZdRake;
        public TextView tvZdRes;
        public TextView tvGdTotal;
        public TextView tvGdRes;
    }


}
