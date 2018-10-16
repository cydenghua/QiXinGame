package com.dcode.wanjin.activity.bet;

import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dcode.wanjin.R;
import com.dcode.wanjin.activity.WanjinBaseFragment;
import com.dcode.wanjin.util.QXConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BetChoseFragment extends WanjinBaseFragment {

    @BindView(R.id.btn_2p)
    Button mBtn2p;
    @BindView(R.id.btn_3p)
    Button mBtn3p;
    @BindView(R.id.btn_4p)
    Button mBtn4p;
    @BindView(R.id.btn_2px)
    Button mBtn2px;
    @BindView(R.id.btn_3px)
    Button mBtn3px;
    @BindView(R.id.btn_4px)
    Button mBtn4px;
    @BindView(R.id.et_pei_1)
    EditText mEtPei1;
    @BindView(R.id.tv_pei_1)
    TextView mTvPei1;
    @BindView(R.id.et_pei_2)
    EditText mEtPei2;
    @BindView(R.id.tv_pei_2)
    TextView mTvPei2;
    @BindView(R.id.et_pei_3)
    EditText mEtPei3;
    @BindView(R.id.tv_pei_3)
    TextView mTvPei3;
    @BindView(R.id.et_pei_4)
    EditText mEtPei4;
    @BindView(R.id.ll_ding_wei_zhi)
    LinearLayout mLlDingWeiZhi;
    @BindView(R.id.ll_he_fen)
    LinearLayout mLlHeFen;

    private View mView;
    private String mPlayClassOld = "";
    private String mPlayClass = "";
    private int mPlayLen = 0;

    public BetChoseFragment() {
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
//        return inflater.inflate(R.layout.fragment_bet_chose, container, false);
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mView = view;
        ButterKnife.bind(this, view);

        ((CheckBox)mView.findViewById(R.id.cb_ding_qu)).setChecked(true);
        clickBtn(mBtn2p);

        removeRepeate();
        return view;
    }

    @Override
    protected int layoutResource() {
        return R.layout.fragment_bet_chose;
    }

    @Override
    protected String getTitle() {
        return "快选";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.btn_2p, R.id.btn_3p, R.id.btn_4p, R.id.btn_2px, R.id.btn_3px, R.id.btn_4px})
    public void clickBtn(View v) {

        mBtn2p.setSelected(v.getId() == R.id.btn_2p);
        mBtn3p.setSelected(v.getId() == R.id.btn_3p);
        mBtn4p.setSelected(v.getId() == R.id.btn_4p);
        mBtn2px.setSelected(v.getId() == R.id.btn_2px);
        mBtn3px.setSelected(v.getId() == R.id.btn_3px);
        mBtn4px.setSelected(v.getId() == R.id.btn_4px);

        if (v.getId() == R.id.btn_2p) {
            mPlayClass = QXConstant.CLASS_2P;
            mPlayLen = 2;
        }
        if (v.getId() == R.id.btn_3p) {
            mPlayClass = QXConstant.CLASS_3P;
            mPlayLen = 3;
        }
        if (v.getId() == R.id.btn_4p) {
            mPlayClass = QXConstant.CLASS_4P;
            mPlayLen = 4;
        }
        if (v.getId() == R.id.btn_2px) {
            mPlayClass = QXConstant.CLASS_2PX;
            mPlayLen = 2;
        }
        if (v.getId() == R.id.btn_3px) {
            mPlayClass = QXConstant.CLASS_3PX;
            mPlayLen = 3;
        }
        if (v.getId() == R.id.btn_4px) {
            mPlayClass = QXConstant.CLASS_4PX;
            mPlayLen = 4;
        }

//        mLlDingWeiZhi.setVisibility(mPlayClass.contains("x") ? View.GONE : View.VISIBLE);
//        mLlHeFen.setVisibility(mPlayClass.contains("x") ? View.GONE : View.VISIBLE);
        setDisplay();
    }

    private void setDisplay() {
        setDingPeiDisplay();
        mView.findViewById(R.id.ll_he_fen).setVisibility(mPlayClass.contains("x") ? View.GONE : View.VISIBLE);//合分只需要：定位
        setBuDingHeFenDisplay();
        setBuDingQuanzhuanDisplay();
        setChuquDisplay();
        setDanShuangDisplay();
        mPlayClassOld = mPlayClass;
    }

    @OnClick({R.id.cb_ding_chu, R.id.cb_ding_qu, R.id.cb_pei_chu, R.id.cb_pei_qu})
    public void onDingPeiCheckBoxClick(View v) {
        boolean b = ((CheckBox)v).isChecked();
        ((CheckBox)mView.findViewById(R.id.cb_ding_chu)).setChecked(v.getId() == R.id.cb_ding_chu);
        ((CheckBox)mView.findViewById(R.id.cb_ding_qu)).setChecked(v.getId() == R.id.cb_ding_qu);
        ((CheckBox)mView.findViewById(R.id.cb_pei_chu)).setChecked(v.getId() == R.id.cb_pei_chu);
        ((CheckBox)mView.findViewById(R.id.cb_pei_qu)).setChecked(v.getId() == R.id.cb_pei_qu);
        ((CheckBox)v).setChecked(b);
        setDingPeiDisplay();
    }

    @OnClick({R.id.cb_hefen_chu, R.id.cb_hefen_qu})
    public void onHefenCheckBoxClick(View v) {
        clickCheckBoxGroup(v, R.id.cb_hefen_chu, R.id.cb_hefen_qu);
    }

    @OnClick({R.id.cb_liangshuhe, R.id.cb_sanshuhe})
    public void onBuDingHefenCheckBoxClick(View v) {
        clickCheckBoxGroup(v, R.id.cb_liangshuhe, R.id.cb_sanshuhe);
    }

    @OnClick({R.id.cb_han_chu, R.id.cb_han_qu})
    public void onHanFushiCheckBoxClick(View v) {
        clickCheckBoxGroup(v, R.id.cb_han_chu, R.id.cb_han_qu);
    }

    @OnClick({R.id.cb_shuangchong_chu, R.id.cb_shuangchong_qu})
    public void onShuangchongCheckBoxClick(View v) {
        clickCheckBoxGroup(v, R.id.cb_shuangchong_chu, R.id.cb_shuangchong_qu);
    }

    @OnClick({R.id.cb_shuangshuangchong_chu, R.id.cb_shuangshuangchong_qu})
    public void onShuangshuangchongCheckBoxClick(View v) {
        clickCheckBoxGroup(v, R.id.cb_shuangshuangchong_chu, R.id.cb_shuangshuangchong_qu);
    }

    @OnClick({R.id.cb_sanchong_qu, R.id.cb_sanchong_chu})
    public void onSanchongCheckBoxClick(View v) {
        clickCheckBoxGroup(v, R.id.cb_sanchong_qu, R.id.cb_sanchong_chu);
    }

    @OnClick({R.id.cb_sichong_chu, R.id.cb_sichong_qu})
    public void onSichongCheckBoxClick(View v) {
        clickCheckBoxGroup(v, R.id.cb_sichong_chu, R.id.cb_sichong_qu);
    }

    @OnClick({R.id.cb_erxiaongdi_chu, R.id.cb_erxiaongdi_qu})
    public void onErxiongdiCheckboxClick(View v) {
        clickCheckBoxGroup(v, R.id.cb_erxiaongdi_chu, R.id.cb_erxiaongdi_qu);
    }

    @OnClick({R.id.cb_sanxiongdi_chu, R.id.cb_sanxiongdi_qu})
    public void onSanxiongdiCheckboxClick(View v) {
        clickCheckBoxGroup(v, R.id.cb_sanxiongdi_chu, R.id.cb_sanxiongdi_qu);
    }

    @OnClick({R.id.cb_sixiongdi_chu, R.id.cb_sixiongdi_qu})
    public void onSixiongdiCheckboxClick(View v) {
        clickCheckBoxGroup(v, R.id.cb_sixiongdi_chu, R.id.cb_sixiongdi_qu);
    }

    @OnClick({R.id.cb_duishu_chu, R.id.cb_duishu_qu})
    public void onDuishuCheckBoxClick(View v) {
        clickCheckBoxGroup(v, R.id.cb_duishu_chu, R.id.cb_duishu_qu);
    }

    @OnClick({R.id.cb_dan_chu, R.id.cb_dan_qu})
    public void onDanCheckBoxClick(View v) {
        clickCheckBoxGroup(v, R.id.cb_dan_chu, R.id.cb_dan_qu);
    }

    @OnClick({R.id.cb_shuang_chu, R.id.cb_shuang_qu})
    public void onShuangCheckBoxClick(View v) {
        clickCheckBoxGroup(v, R.id.cb_shuang_chu, R.id.cb_shuang_qu);
    }

    private void clickCheckBoxGroup(View v, int cbId1, int cbId2) {
        if(v.getId() == cbId1) {
            ((CheckBox)mView.findViewById(cbId2)).setChecked(false);
        }else {
            ((CheckBox)mView.findViewById(cbId1)).setChecked(false);
        }
    }

    private void setDingPeiDisplay() {
        mEtPei4.setVisibility(mPlayLen<4 ? View.GONE : View.VISIBLE);
        mEtPei3.setVisibility(mPlayLen<3 ? View.GONE : View.VISIBLE);
        mTvPei3.setVisibility(mEtPei4.getVisibility());
        mTvPei2.setVisibility(mEtPei3.getVisibility());

        if(((CheckBox)mView.findViewById(R.id.cb_ding_chu)).isChecked()
                || ((CheckBox)mView.findViewById(R.id.cb_ding_qu)).isChecked()) {
            mView.findViewById(R.id.ll_ding).setVisibility(View.VISIBLE);
            mView.findViewById(R.id.ll_pei).setVisibility(View.GONE);
        }

        if(((CheckBox)mView.findViewById(R.id.cb_pei_chu)).isChecked()
                || ((CheckBox)mView.findViewById(R.id.cb_pei_qu)).isChecked()) {
            mView.findViewById(R.id.ll_ding).setVisibility(View.GONE);
            mView.findViewById(R.id.ll_pei).setVisibility(View.VISIBLE);
        }

        //**现，只需要配数
        if(mPlayClass.contains("x")) {
            ((TextView)mView.findViewById(R.id.tv_pei)).setText("配数");
            mView.findViewById(R.id.ll_ding_wei_zhi).setVisibility(View.GONE);
            if(mView.findViewById(R.id.ll_ding).getVisibility() == View.VISIBLE){
                mView.findViewById(R.id.ll_ding).setVisibility(View.GONE);
                mView.findViewById(R.id.ll_pei).setVisibility(View.VISIBLE);
            }
        }else {
            ((TextView)mView.findViewById(R.id.tv_pei)).setText("配数全转");
            mView.findViewById(R.id.ll_ding_wei_zhi).setVisibility(View.VISIBLE);
        }

    }

    private void setBuDingHeFenDisplay() {
        mView.findViewById(R.id.cb_sanshuhe).setVisibility(mPlayLen > 2 ? View.VISIBLE : View.INVISIBLE);
        mView.findViewById(R.id.ll_zhifanwei).setVisibility(mPlayLen < 4 || mPlayClass.contains("x") ? View.GONE : View.VISIBLE);
    }

    private void setBuDingQuanzhuanDisplay() {
        mView.findViewById(R.id.ll_quanzhuan).setVisibility(mPlayClass.contains("x") ? View.GONE : View.VISIBLE);
        mView.findViewById(R.id.ll_chenghaoweizhi).setVisibility(mPlayLen < 4 ? View.VISIBLE : View.GONE);
    }

    private void setChuquDisplay() {
        mView.findViewById(R.id.ll_shuangshuangchong).setVisibility(mPlayClass.equals(QXConstant.CLASS_4P) ? View.VISIBLE : View.GONE);
        mView.findViewById(R.id.ll_sanchong).setVisibility(mPlayLen > 2 ? View.VISIBLE : View.GONE);
        mView.findViewById(R.id.ll_sichong).setVisibility(mPlayLen > 3 ? View.VISIBLE : View.GONE);

        mView.findViewById(R.id.ll_sanxiaongdi).setVisibility(mPlayLen > 2 ? View.VISIBLE : View.GONE);
        mView.findViewById(R.id.ll_sixiaongdi).setVisibility(mPlayLen > 3 ? View.VISIBLE : View.GONE);
    }

    private void setDanShuangDisplay() {
        if(mPlayClass.contains("x")) {
            (mView.findViewById(R.id.cb_dan_3)).setVisibility(mPlayLen > 2 ? View.VISIBLE : View.GONE);
            (mView.findViewById(R.id.cb_dan_4)).setVisibility(mPlayLen > 3 ? View.VISIBLE : View.GONE);
            (mView.findViewById(R.id.cb_shuang_3)).setVisibility(mPlayLen > 2 ? View.VISIBLE : View.GONE);
            (mView.findViewById(R.id.cb_shuang_4)).setVisibility(mPlayLen > 3 ? View.VISIBLE : View.GONE);
        }else {
            (mView.findViewById(R.id.cb_dan_3)).setVisibility(View.VISIBLE );
            (mView.findViewById(R.id.cb_dan_4)).setVisibility(View.VISIBLE);
            (mView.findViewById(R.id.cb_shuang_3)).setVisibility(View.VISIBLE);
            (mView.findViewById(R.id.cb_shuang_4)).setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btn_create)
    public void clickCreate() {
        CreateChoseNumber createChoseNumber = new CreateChoseNumber(this);
        createChoseNumber.mPlayClass = mPlayClass;
        createChoseNumber.mPlayLen = mPlayLen;

        // 设置定配模式
        if(((CheckBox)mView.findViewById(R.id.cb_ding_chu)).isChecked()) { createChoseNumber.mModeDingPei = CreateChoseNumber.ModeDingPei.DING_CHU; }
        if(((CheckBox)mView.findViewById(R.id.cb_ding_qu)).isChecked()) { createChoseNumber.mModeDingPei = CreateChoseNumber.ModeDingPei.DING_QU; }
        if(((CheckBox)mView.findViewById(R.id.cb_pei_chu)).isChecked()) { createChoseNumber.mModeDingPei = CreateChoseNumber.ModeDingPei.PEI_CHU; }
        if(((CheckBox)mView.findViewById(R.id.cb_pei_qu)).isChecked()) { createChoseNumber.mModeDingPei = CreateChoseNumber.ModeDingPei.PEI_QU; }

        createChoseNumber.mDingQian = getInputById(R.id.et_dingqian);
        createChoseNumber.mDingBai = getInputById(R.id.et_dingbai);
        createChoseNumber.mDingShi = getInputById(R.id.et_dingshi);
        createChoseNumber.mDingGe = getInputById(R.id.et_dingge);

        createChoseNumber.mPei1 = getInputById(R.id.et_pei_1);
        createChoseNumber.mPei2 = getInputById(R.id.et_pei_2);
        createChoseNumber.mPei3 = getInputById(R.id.et_pei_3);
        createChoseNumber.mPei4 = getInputById(R.id.et_pei_4);

        //合分设置
        createChoseNumber.mHefenQu =  ((CheckBox)mView.findViewById(R.id.cb_hefen_qu)).isChecked();
        createChoseNumber.mHefenChu =  ((CheckBox)mView.findViewById(R.id.cb_hefen_chu)).isChecked();
        createChoseNumber.mHefenCb11 = ((CheckBox)mView.findViewById(R.id.cb_hefen11)).isChecked();
        createChoseNumber.mHefenCb12 = ((CheckBox)mView.findViewById(R.id.cb_hefen12)).isChecked();
        createChoseNumber.mHefenCb13 = ((CheckBox)mView.findViewById(R.id.cb_hefen13)).isChecked();
        createChoseNumber.mHefenCb14 = ((CheckBox)mView.findViewById(R.id.cb_hefen14)).isChecked();
        createChoseNumber.mHefen1Str = getInputById(R.id.et_hefen1);
        createChoseNumber.mHefenCb21 = ((CheckBox)mView.findViewById(R.id.cb_hefen21)).isChecked();
        createChoseNumber.mHefenCb22 = ((CheckBox)mView.findViewById(R.id.cb_hefen22)).isChecked();
        createChoseNumber.mHefenCb23 = ((CheckBox)mView.findViewById(R.id.cb_hefen23)).isChecked();
        createChoseNumber.mHefenCb24 = ((CheckBox)mView.findViewById(R.id.cb_hefen24)).isChecked();
        createChoseNumber.mHefen2Str = getInputById(R.id.et_hefen2);
        createChoseNumber.mHefenCb31 = ((CheckBox)mView.findViewById(R.id.cb_hefen31)).isChecked();
        createChoseNumber.mHefenCb32 = ((CheckBox)mView.findViewById(R.id.cb_hefen32)).isChecked();
        createChoseNumber.mHefenCb33 = ((CheckBox)mView.findViewById(R.id.cb_hefen33)).isChecked();
        createChoseNumber.mHefenCb34 = ((CheckBox)mView.findViewById(R.id.cb_hefen34)).isChecked();
        createChoseNumber.mHefen3Str = getInputById(R.id.et_hefen3);
        createChoseNumber.mHefenCb41 = ((CheckBox)mView.findViewById(R.id.cb_hefen41)).isChecked();
        createChoseNumber.mHefenCb42 = ((CheckBox)mView.findViewById(R.id.cb_hefen42)).isChecked();
        createChoseNumber.mHefenCb43 = ((CheckBox)mView.findViewById(R.id.cb_hefen43)).isChecked();
        createChoseNumber.mHefenCb44 = ((CheckBox)mView.findViewById(R.id.cb_hefen44)).isChecked();
        createChoseNumber.mHefen4Str = getInputById(R.id.et_hefen4);

        //不定合分
        createChoseNumber.mHeshuLiang = ((CheckBox)mView.findViewById(R.id.cb_liangshuhe)).isChecked();
        createChoseNumber.mHeshuSan = ((CheckBox)mView.findViewById(R.id.cb_sanshuhe)).isChecked();
        createChoseNumber.mHeshuStr = getInputById(R.id.et_heshu);
        createChoseNumber.mHeshuMinStr = getInputById(R.id.et_heshu_min);
        createChoseNumber.mHeshuMaxStr = getInputById(R.id.et_heshu_max);

        //全转
        createChoseNumber.mQuanzhuanStr = getInputById(R.id.et_quanzhuan);
        createChoseNumber.mShangjiangStr = getInputById(R.id.et_shangjiang);
        createChoseNumber.mPaichuStr = getInputById(R.id.et_paichu);
        createChoseNumber.mXPos1 = ((CheckBox)mView.findViewById(R.id.cb_x_pos1)).isChecked();
        createChoseNumber.mXPos2 = ((CheckBox)mView.findViewById(R.id.cb_x_pos2)).isChecked();
        createChoseNumber.mXPos3 = ((CheckBox)mView.findViewById(R.id.cb_x_pos3)).isChecked();
        createChoseNumber.mXPos4 = ((CheckBox)mView.findViewById(R.id.cb_x_pos4)).isChecked();
        int k = 0;
        if(createChoseNumber.mXPos1){ k++; }
        if(createChoseNumber.mXPos2){ k++; }
        if(createChoseNumber.mXPos3){ k++; }
        if(createChoseNumber.mXPos4){ k++; }
        if(k >0 && k != (4-mPlayLen)){
            showToast("乘号位置不正确");
            return;
        }

        //含  复式
        createChoseNumber.mHanChu = ((CheckBox)mView.findViewById(R.id.cb_han_chu)).isChecked();
        createChoseNumber.mHanQu = ((CheckBox)mView.findViewById(R.id.cb_han_qu)).isChecked();
        createChoseNumber.mHanStr = getInputById(R.id.et_han);
        createChoseNumber.mFushiStr = getInputById(R.id.et_fushi);

        //双重， 双双重， 三重， 四重
        createChoseNumber.mChongShuangQu = ((CheckBox)mView.findViewById(R.id.cb_shuangchong_qu)).isChecked();
        createChoseNumber.mChongShuangChu = ((CheckBox)mView.findViewById(R.id.cb_shuangchong_chu)).isChecked();
        createChoseNumber.mChongShuangShuangQu = ((CheckBox)mView.findViewById(R.id.cb_shuangshuangchong_qu)).isChecked();
        createChoseNumber.mChongShuangShuangChu = ((CheckBox)mView.findViewById(R.id.cb_shuangshuangchong_chu)).isChecked();
        createChoseNumber.mChongSanQu = ((CheckBox)mView.findViewById(R.id.cb_sanchong_qu)).isChecked();
        createChoseNumber.mChongSanChu = ((CheckBox)mView.findViewById(R.id.cb_sanchong_chu)).isChecked();
        createChoseNumber.mChongSiQu = ((CheckBox)mView.findViewById(R.id.cb_sichong_qu)).isChecked();
        createChoseNumber.mChongSiChu = ((CheckBox)mView.findViewById(R.id.cb_sichong_chu)).isChecked();

        //兄弟
        createChoseNumber.mErXiongdiQu = ((CheckBox)mView.findViewById(R.id.cb_erxiaongdi_qu)).isChecked();
        createChoseNumber.mErXiongdiChu = ((CheckBox)mView.findViewById(R.id.cb_erxiaongdi_chu)).isChecked();
        createChoseNumber.mSanXiongdiQu = ((CheckBox)mView.findViewById(R.id.cb_sanxiongdi_qu)).isChecked();
        createChoseNumber.mSanXiongdiChu = ((CheckBox)mView.findViewById(R.id.cb_sanxiongdi_chu)).isChecked();
        createChoseNumber.mSiXiongdiQu = ((CheckBox)mView.findViewById(R.id.cb_sixiongdi_qu)).isChecked();
        createChoseNumber.mSiXiongdiChu = ((CheckBox)mView.findViewById(R.id.cb_sixiongdi_chu)).isChecked();

        //对数
        createChoseNumber.mDuishuQu = ((CheckBox)mView.findViewById(R.id.cb_duishu_qu)).isChecked();
        createChoseNumber.mDuishuChu = ((CheckBox)mView.findViewById(R.id.cb_duishu_chu)).isChecked();
        createChoseNumber.mDuishu1 = getInputById(R.id.et_duishu_1);
        createChoseNumber.mDuishu2 = getInputById(R.id.et_duishu_2);
        createChoseNumber.mDuishu3 = getInputById(R.id.et_duishu_3);
        if(createChoseNumber.mDuishu1.length() < 2) { createChoseNumber.mDuishu1 = ""; }
        if(createChoseNumber.mDuishu2.length() < 2) { createChoseNumber.mDuishu2 = ""; }
        if(createChoseNumber.mDuishu3.length() < 2) { createChoseNumber.mDuishu3 = ""; }

        //单双
        createChoseNumber.mDanQu = ((CheckBox)mView.findViewById(R.id.cb_dan_qu)).isChecked();
        createChoseNumber.mDanChu = ((CheckBox)mView.findViewById(R.id.cb_dan_chu)).isChecked();
        createChoseNumber.mDan1 = ((CheckBox)mView.findViewById(R.id.cb_dan_1)).isChecked();
        createChoseNumber.mDan2 = ((CheckBox)mView.findViewById(R.id.cb_dan_2)).isChecked();
        createChoseNumber.mDan3 = ((CheckBox)mView.findViewById(R.id.cb_dan_3)).isChecked();
        createChoseNumber.mDan4 = ((CheckBox)mView.findViewById(R.id.cb_dan_4)).isChecked();
        createChoseNumber.mShuangQu = ((CheckBox)mView.findViewById(R.id.cb_shuang_qu)).isChecked();
        createChoseNumber.mShuangChu = ((CheckBox)mView.findViewById(R.id.cb_shuang_chu)).isChecked();
        createChoseNumber.mShuang1 = ((CheckBox)mView.findViewById(R.id.cb_shuang_1)).isChecked();
        createChoseNumber.mShuang2 = ((CheckBox)mView.findViewById(R.id.cb_shuang_2)).isChecked();
        createChoseNumber.mShuang3 = ((CheckBox)mView.findViewById(R.id.cb_shuang_3)).isChecked();
        createChoseNumber.mShuang4 = ((CheckBox)mView.findViewById(R.id.cb_shuang_4)).isChecked();

        createChoseNumber.createNumber();
        if(createChoseNumber.mResList.size() > 0){
            BetQuickChoseResActivity.startActivity(mActivity, createChoseNumber.mResList, mPlayClass);
        }else {
            showToast("沒有这样的号码");
        }

    }

    private String getInputById(int id) {
        return ((EditText)mView.findViewById(id)).getText().toString();
    }

    private void removeRepeate() {
        mEtPei1.addTextChangedListener(mTextWatcher);
        mEtPei2.addTextChangedListener(mTextWatcher);
        mEtPei3.addTextChangedListener(mTextWatcher);
        mEtPei4.addTextChangedListener(mTextWatcher);
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void afterTextChanged(Editable editable) {
            String sInput= editable.toString();
            if(sInput.length() > 1) {
                String sBefore = sInput.substring(0, sInput.length()-1);
                String sLast = sInput.substring(sInput.length()-1, sInput.length());
                if(sBefore.contains(sLast)) {
                    editable.delete(sInput.length()-1, sInput.length());
                }
            }
        }
    };

    @OnClick(R.id.btn_clear)
    public void resetView() {
        LinearLayout llContain = mView.findViewById(R.id.ll_contain);
        doResetView(llContain);
//        for (int i = 0; i < llContain.getChildCount(); i++) {
//            View v = llContain.getChildAt(i);
//            Log.e("ddddddddddd s = ", v.getClass().getSimpleName());
//        }
    }

    private void doResetView(LinearLayout llView) {
        for (int i = 0; i < llView.getChildCount(); i++) {

            View v = llView.getChildAt(i);
            if(v.getClass().getSimpleName().equals("LinearLayout")) {
                doResetView((LinearLayout) v);
            }else {
                if(v.getClass().getSimpleName().equals("AppCompatCheckBox")) {
                    ((AppCompatCheckBox)v).setChecked(false);
                }
                if(v.getClass().getSimpleName().equals("AppCompatEditText")) {
                    ((AppCompatEditText)v).getText().clear();
                }
            }
//            AppCompatCheckBox
//                    AppCompatEditText
        }
    }

}
