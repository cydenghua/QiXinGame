package com.dcode.wanjin.activity.bet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.dcode.corelibrary.network.RequestCallbackSimply;
import com.dcode.corelibrary.network.RequestParamsMap;
import com.dcode.wanjin.R;
import com.dcode.wanjin.WanjinApplication;
import com.dcode.wanjin.activity.WanJinBaseActivity;
import com.dcode.wanjin.entity.PeriodUserOdds;
import com.dcode.wanjin.entity.PeriodUserOddsEntity;
import com.dcode.wanjin.event.EventBetQuick;
import com.dcode.wanjin.network.HttpApi;
import com.dcode.wanjin.util.QXConstant;
import com.dcode.wanjin.util.StrUtil;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class BetQuickChoseResActivity extends WanJinBaseActivity {

    private static final String PARAM_RES_LIST = "reslist";
    private static final String PARAM_PLAY_CLASS = "playclass";

    @BindView(R.id.gv)
    GridView mGridView;
    @BindView(R.id.tv_count)
    TextView mTvCount;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.et_money)
    EditText mEtMoney;

    private PeriodUserOdds mPeriodUserOdds;
    private ArrayList<String> mResList;
    private QuickAdapter<String> mAdapter;

    private String mPlayClass = "";
    private int mCreditLeft = 0;


    public static void startActivity(Context context, ArrayList<String> list, String playClass) {
        Intent intent = new Intent(context, BetQuickChoseResActivity.class);
        intent.putStringArrayListExtra(PARAM_RES_LIST, list);
        intent.putExtra(PARAM_PLAY_CLASS, playClass);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPlayClass = getIntent().getStringExtra(PARAM_PLAY_CLASS);
        mResList = getIntent().getStringArrayListExtra(PARAM_RES_LIST);
        mAdapter.replaceAll(mResList);
        mTvCount.setText("" + mResList.size());

        getOdds();
        mEtMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    int k = Integer.parseInt(mEtMoney.getText().toString());
                    mTvMoney.setText("" + k*mResList.size());
                }catch (Exception e) {
                }
            }
        });
    }

    @Override
    protected int layoutResource() {
        return R.layout.activity_bet_quick_chose_res;
    }

    @Override
    protected String topTitle() {
        return "快选";
    }

    @Override
    protected void initAdapter() {
        mAdapter = new QuickAdapter<String>(this, R.layout.activity_bet_quick_chose_res_item) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                helper.setText(R.id.tv_num, item + "");
            }
        };
        mGridView.setAdapter(mAdapter);
    }

    private void getOdds() {
        String playType = StrUtil.getPlayType(mResList.get(0));
        if (TextUtils.isEmpty(playType)) {
            showToast("输入有误，请重新输入");
            return;
        }
        RequestParamsMap map = new RequestParamsMap();
        map.add("playType", playType);
        map.add("playValue", mResList.get(0));
        map.add("playClass", StrUtil.getPlayClass(playType));
        request(HttpApi.getUrlWithHost(HttpApi.getUsePeriodOdds), map, PeriodUserOddsEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                PeriodUserOddsEntity entity = (PeriodUserOddsEntity) object;
                if (entity.isSuccess()) {
                    mCreditLeft = entity.getData().getCreditLeft(); //fen
                    mPeriodUserOdds = entity.getData();
                } else {
                    showToast(entity.getMessage());
                }
            }

            @Override
            public boolean onFailure(int statusCode, String response) {
                return super.onFailure(statusCode, response);
            }
        }, false);
    }

    @OnClick(R.id.btn_ok)
    public void clickOk() {
        WanjinApplication app = WanjinApplication.getInstance();
        if(!app.getCurrUser().getLevel().equals(QXConstant.LEVEL_HUIYUAN)) {
            showToast("请用会员账号登录");
            return;
        }
//        if(mPeriodUserOdds == null){
//            showToast("请稍后");
//            return;
//        }
        try {
            int k = Integer.parseInt(mEtMoney.getText().toString());
            k = k*mResList.size()*100;
            if(mCreditLeft < k) {
                showToast("信用额度不足，请减少投注");
                return;
            }

            String tmpStr = "";
            for (int a = 0; a < mResList.size(); a++) {
                if(a == 0) {
                    tmpStr = mResList.get(a);
                }else {
                    tmpStr = tmpStr + "," + mResList.get(a);
                }
            }

            String playType = StrUtil.getPlayType(mResList.get(0));
            if (mPlayClass.equals(QXConstant.CLASS_4PX)) {
                playType = QXConstant.CLASS_TYPE_4PX;
            }

            EventBus.getDefault().post(new EventBetQuick(EventBetQuick.KEY_ADD, tmpStr, playType, mEtMoney.getText().toString(), mPeriodUserOdds));
            finish();
        }catch (Exception e) {
            return;
        }


    }

}
