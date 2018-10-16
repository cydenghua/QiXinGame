package com.dcode.wanjin.activity.bet;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import com.dcode.corelibrary.network.BaseEntity;
import com.dcode.corelibrary.network.RequestCallbackSimply;
import com.dcode.corelibrary.network.RequestParamsMap;
import com.dcode.wanjin.R;
import com.dcode.wanjin.WanjinApplication;
import com.dcode.wanjin.activity.WanjinBaseFragment;
import com.dcode.wanjin.entity.AddUserPeriodBetEntity;
import com.dcode.wanjin.entity.PeriodBet;
import com.dcode.wanjin.entity.PeriodUserOdds;
import com.dcode.wanjin.entity.PeriodUserOddsEntity;
import com.dcode.wanjin.event.EventBetQuick;
import com.dcode.wanjin.event.EventFragmentDetail;
import com.dcode.wanjin.network.HttpApi;
import com.dcode.wanjin.util.QXConstant;
import com.dcode.wanjin.util.StrUtil;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import butterknife.BindView;
import butterknife.OnClick;

public class BetQuickFragment extends WanjinBaseFragment {

    private static final int INPUT_NUM = 1;
    private static final int INPUT_MONEY = 2;

    @BindView(R.id.lv)
    ListView mLv;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.tv_num_line)
    TextView mTvNumLine;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.tv_line_money)
    TextView mTvLineMoney;
    @BindView(R.id.cb_4px)
    CheckBox mCb4px;
    @BindView(R.id.cb_zhuan)
    CheckBox mCbZhuan;
    @BindView(R.id.tv_odds)
    TextView mTvOdds;
    @BindView(R.id.tv_left)
    TextView mTvLeft;
    @BindView(R.id.tv_credit_left)
    TextView mTvLeftCredit;
    @BindView(R.id.btn_dot)
    Button mBtnDot;
    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.btn5)
    Button btn5;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.btn7)
    Button btn7;
    @BindView(R.id.btn8)
    Button btn8;
    @BindView(R.id.btn9)
    Button btn9;
    @BindView(R.id.btn0)
    Button btn0;
    @BindView(R.id.btn_ok)
    Button btnOk;

    private QuickAdapter<PeriodBet> mAdapter;
    private PeriodUserOdds mPeriodUserOdds;
    private int mInputType = INPUT_NUM;

    public BetQuickFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);
        clickInput(mTvNum);
        return view;
    }

    @Override
    protected int layoutResource() {
        return R.layout.fragment_bet_quick;
    }

    @Override
    protected String getTitle() {
        return "快打";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected boolean isRegisterEventbus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive(EventBetQuick event) {
        if(event.getKeyEvent().equals(EventBetQuick.KEY_ADD)) {
            addUserBet(event.getPlayValue(), event.getPlayType(), event.getPlayMoney(), event.getPeriodUserOdds());
        }
        if(event.getKeyEvent().equals(EventBetQuick.KEY_BACK_NUMBER)) {
            if(mAdapter.isEmpty()) { return; }
            int k = 0;
            String arr[] = event.getBackNum().split(",");
            for (int i = arr.length-1; i > -1; i--) {
                String sId = arr[i];
                while (!mAdapter.getItem(k).getSysId().equals(Integer.parseInt(sId))) {
                    k++;
                }
                mAdapter.getItem(k).setDeleted(new Short("1"));
                mAdapter.getItem(k).setTmpDeleted(1);
            }
            removeDel();
            mAdapter.notifyDataSetChanged();
        }
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
                helper.setVisible(R.id.tv_back, !item.getDeleted().equals(Short.parseShort("0")));
                helper.setChecked(R.id.cb_back, item.getTmpDeleted() == 1);
                helper.setVisible(R.id.cb_back, item.getDeleted().equals(Short.parseShort("0")));
                if(!item.isCancelable() && !item.getDeleted().equals(Short.parseShort("1"))) {
                    helper.getView(R.id.cb_back).setVisibility(View.INVISIBLE);
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

    }

    @OnClick({R.id.tv_money, R.id.tv_num})
    public void clickInput(View v) {
        if (v.getId() == R.id.tv_num) {
            mInputType = INPUT_NUM;
            mTvLineMoney.setBackgroundColor(getResources().getColor(R.color.stroke_grey));
            mTvNumLine.setBackgroundColor(getResources().getColor(R.color.red));
            mTvNum.setText("");
            mTvMoney.setText("");
            mBtnDot.setText("X");
        }
        if (v.getId() == R.id.tv_money) {
            mInputType = INPUT_MONEY;
            mTvNumLine.setBackgroundColor(getResources().getColor(R.color.stroke_grey));
            mTvLineMoney.setBackgroundColor(getResources().getColor(R.color.red));
            mTvMoney.setText("");
            mBtnDot.setText(".");
        }
    }

    @OnClick({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn_dot})
    public void clickNum(View v) {
        String sTmp = "";
        if (v.getId() == R.id.btn0) { sTmp = "0"; }
        if (v.getId() == R.id.btn1) { sTmp = "1"; }
        if (v.getId() == R.id.btn2) { sTmp = "2"; }
        if (v.getId() == R.id.btn3) { sTmp = "3"; }
        if (v.getId() == R.id.btn4) { sTmp = "4"; }
        if (v.getId() == R.id.btn5) { sTmp = "5"; }
        if (v.getId() == R.id.btn6) { sTmp = "6"; }
        if (v.getId() == R.id.btn7) { sTmp = "7"; }
        if (v.getId() == R.id.btn8) { sTmp = "8"; }
        if (v.getId() == R.id.btn9) { sTmp = "9"; }
        TextView tv = mTvNum;
        if (mInputType == INPUT_NUM) {
            if (v.getId() == R.id.btn_dot) {
                sTmp = "X";
            }
            tv = mTvNum;
        }
        if (mInputType == INPUT_MONEY) {
            if (v.getId() == R.id.btn_dot) {
                sTmp = ".";
            }
            tv = mTvMoney;
        }

        if (tv.getText().length() < 4) {
            tv.setText(tv.getText().toString() + sTmp);
        } else {
            tv.setText(sTmp);
        }

        if (mInputType == INPUT_NUM && tv.getText().length() == 4) {
            clickInput(mTvMoney);
            getOdds();
        }
    }

    @OnClick({R.id.cb_4px, R.id.cb_zhuan})
    public void clickCheckBox(View v) {
        if (v.getId() == R.id.cb_4px) {
            mCbZhuan.setChecked(false);
        }
        if (v.getId() == R.id.cb_zhuan) {
            mCb4px.setChecked(false);
        }
    }

    private void getOdds() {
        mPeriodUserOdds = null;
        mTvOdds.setText("");
        mTvLeft.setText("");
        mTvLeftCredit.setText("");
        String playType = StrUtil.getPlayType(mTvNum.getText().toString());
        if (QXConstant.CLASS_TYPE_4P1.equals(playType) && mCb4px.isChecked()) {
            playType = QXConstant.CLASS_TYPE_4PX;
        }
        if (TextUtils.isEmpty(playType)) {
            showToast("输入有误，请重新输入");
            return;
        }
        RequestParamsMap map = new RequestParamsMap();
        map.add("playType", playType);
        map.add("playValue", mTvNum.getText().toString());
        map.add("playClass", StrUtil.getPlayClass(playType));
        request(HttpApi.getUrlWithHost(HttpApi.getUsePeriodOdds), map, PeriodUserOddsEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                PeriodUserOddsEntity entity = (PeriodUserOddsEntity) object;
                if (entity.isSuccess()) {
                    mPeriodUserOdds = entity.getData();
                    mTvOdds.setText(StrUtil.decimalToStr(entity.getData().getOdds()));
                    mTvLeft.setText(StrUtil.intFenToYuan(entity.getData().getTypeLeft()));
                    mTvLeftCredit.setText(StrUtil.intFenToYuan(entity.getData().getCreditLeft())); //信用额度剩余
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
    public void clickOk(View v) {
        WanjinApplication app = WanjinApplication.getInstance();
        if(!app.getCurrUser().getLevel().equals(QXConstant.LEVEL_HUIYUAN)) {
            showToast("请用会员账号登录");
            return;
        }

        String playType = StrUtil.getPlayType(mTvNum.getText().toString());
        if (QXConstant.CLASS_TYPE_4P1.equals(playType) && mCb4px.isChecked()) {
            playType = QXConstant.CLASS_TYPE_4PX;
        }
        if (TextUtils.isEmpty(playType)) {
            showToast("输入有误，请重新输入");
            return;
        }
        String playValue = mTvNum.getText().toString();
        if(mCbZhuan.isChecked() && !playType.contains("x")) {  //全转
            CreateChoseNumber createChoseNumber = new CreateChoseNumber(this);
            playValue = createChoseNumber.getQuanzhuan(playValue);
        }
        addUserBet(playValue, playType, mTvMoney.getText().toString(), mPeriodUserOdds);
    }

    private void addUserBet(String playValue, String playType, String playMoney, PeriodUserOdds periodUserOdds) {
        if (playValue.length() < 1) {
            showToast("请填写号码");
            return;
        }
        if (playMoney.length() < 1) {
            showToast("请填写金额");
            return;
        }
        if (TextUtils.isEmpty(playType)) {
            showToast("输入有误，请重新输入");
            return;
        }

        int money;
        try {
            BigDecimal inputMoney = new BigDecimal(playMoney);
            money = inputMoney.multiply(new BigDecimal("100")).intValue();
        } catch (Exception e) {
            showToast("金额填写不正确");
            return;
        }

        if (periodUserOdds != null) {
//            showToast("请稍后");
//            return;
            if (money < periodUserOdds.getMinPlay()) {
                showToast("不能低于" + StrUtil.intFenToYuan(periodUserOdds.getMinPlay()));
                return;
            }
            if (money > periodUserOdds.getMaxPlay()) {
                showToast("不能超出单注上限" + StrUtil.intFenToYuan(periodUserOdds.getMaxPlay()));
                return;
            }
            if (money > periodUserOdds.getCreditLeft()) {
                showToast("信用额度不足，当前信用额度剩余：" + StrUtil.intFenToYuan(periodUserOdds.getCreditLeft()));
                return;
            }
            if (money > periodUserOdds.getTypeLeft()) {
                showToast("当前可下只剩：" + StrUtil.intFenToYuan(periodUserOdds.getTypeLeft()));
                return;
            }
        }

        RequestParamsMap map = new RequestParamsMap();
        map.add("playType", playType);
        map.add("playValue", playValue);
        map.add("playClass", StrUtil.getPlayClass(playType));
        map.add("playMoney", "" + money);
        request(HttpApi.getUrlWithHost(HttpApi.addUserPeriodBet), map, AddUserPeriodBetEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                AddUserPeriodBetEntity entity = (AddUserPeriodBetEntity) object;
                showToast(entity.getMessage());
                if (entity.isSuccess()) {
//                    if (mPeriodUserOdds.getOdds().compareTo(entity.getData().getPlayOdds()) != 0) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
//                        builder.setTitle("提示");
//                        builder.setMessage("请注意！最新赔率为：" + StrUtil.decimalToStr(entity.getData().getPlayOdds()) + "，如有异议请退码");
//                        builder.setPositiveButton("确定", null);
//                        builder.setCancelable(false);
//                        builder.show();
//                    }
                    mTvNum.setText("");
                    mAdapter.addAll(entity.getData());
                    mLv.setSelection(mAdapter.getCount() - 1);
                    clickInput(mTvNum);
                    EventBus.getDefault().post(new EventFragmentDetail(EventFragmentDetail.KEY_REFRESH));
                }
            }

            @Override
            public boolean onFailure(int statusCode, String response) {
                return super.onFailure(statusCode, response);
            }
        }, true);
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

        RequestParamsMap map = new RequestParamsMap();
        map.add("idArr", sid);
        request(HttpApi.getUrlWithHost(HttpApi.deleteUserPeriodBetBatch), map, BaseEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                BaseEntity entity = (BaseEntity) object;
                showToast(entity.getMessage());
                if (entity.isSuccess()) {
                    removeDel();
                }
                EventBus.getDefault().post(new EventFragmentDetail(EventFragmentDetail.KEY_REFRESH));
            }

            @Override
            public boolean onFailure(int statusCode, String response) {
                return super.onFailure(statusCode, response);
            }
        }, true);

    }

    private void removeDel() {
        for (int i = mAdapter.getCount()-1; i >= 0; i--) {
            if (mAdapter.getItem(i).getTmpDeleted() == 1) {
                mAdapter.remove(i);
            }
        }
    }

}


//https://blog.csdn.net/maoyuanming0806/article/details/77720754