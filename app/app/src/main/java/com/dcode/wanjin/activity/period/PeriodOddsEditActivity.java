package com.dcode.wanjin.activity.period;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dcode.corelibrary.network.BaseEntity;
import com.dcode.corelibrary.network.RequestCallbackSimply;
import com.dcode.corelibrary.network.RequestParamsMap;
import com.dcode.wanjin.R;
import com.dcode.wanjin.activity.WanJinBaseActivity;
import com.dcode.wanjin.event.EventPeriodOdds;
import com.dcode.wanjin.network.HttpApi;
import com.dcode.wanjin.util.QXConstant;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class PeriodOddsEditActivity extends WanJinBaseActivity {


    private static final String PARAM_PERIOD_ID = "p_id";

    @BindView(R.id.rb_2p)
    RadioButton mRb2p;
    @BindView(R.id.rb_3p)
    RadioButton mRb3p;
    @BindView(R.id.rb_4p)
    RadioButton mRb4p;
    @BindView(R.id.rb_2px)
    RadioButton mRb2px;
    @BindView(R.id.rb_3px)
    RadioButton mRb3px;
    @BindView(R.id.rb_4px)
    RadioButton mRb4px;
    @BindView(R.id.et_input)
    EditText mEtInput;
    @BindView(R.id.et_odds)
    EditText mEtOdds;
    @BindView(R.id.btn1)
    Button mBtn1;
    @BindView(R.id.btn2)
    Button mBtn2;
    @BindView(R.id.btn3)
    Button mBtn3;
    @BindView(R.id.btn4)
    Button mBtn4;
    @BindView(R.id.btn5)
    Button mBtn5;
    @BindView(R.id.btn6)
    Button mBtn6;
    @BindView(R.id.btn7)
    Button mBtn7;
    @BindView(R.id.btn8)
    Button mBtn8;
    @BindView(R.id.btn9)
    Button mBtn9;
    @BindView(R.id.btn_x)
    Button mBtnX;
    @BindView(R.id.btn0)
    Button mBtn0;
    @BindView(R.id.btn_del)
    Button mBtnDel;
    @BindView(R.id.btn_save)
    Button mBtnSave;

    private String mPeriodId;
    private String mInputType;
    private int mInputLen;

    public static void startActivity(Context context, String periodId) {
        Intent intent = new Intent(context, PeriodOddsEditActivity.class);
        intent.putExtra(PARAM_PERIOD_ID, periodId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPeriodId = getIntent().getStringExtra(PARAM_PERIOD_ID);
        onClickRb(findViewById(R.id.rb_2p));
    }

    @Override
    protected int layoutResource() {
        return R.layout.activity_period_odds_edit;
    }

    @Override
    protected String topTitle() {
        return "赔率变动编辑";
    }

    @OnClick({R.id.rb_2p, R.id.rb_3p, R.id.rb_4p, R.id.rb_2px, R.id.rb_3px, R.id.rb_4px})
    public void onClickRb(View v) {
        mRb2p.setChecked(v.getId() == R.id.rb_2p);
        mRb3p.setChecked(v.getId() == R.id.rb_3p);
        mRb4p.setChecked(v.getId() == R.id.rb_4p);

        mRb2px.setChecked(v.getId() == R.id.rb_2px);
        mRb3px.setChecked(v.getId() == R.id.rb_3px);
        mRb4px.setChecked(v.getId() == R.id.rb_4px);

        mEtInput.getText().clear();

        if(v.getId() == R.id.rb_2px ||
                v.getId() == R.id.rb_3px ||
                v.getId() == R.id.rb_4px ) {
            mBtnX.setVisibility(View.INVISIBLE);
        }else {
            mBtnX.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn0, R.id.btn_x, R.id.btn_del })
    public void onClickBtn(View v) {
        resetInput();
        String sTmp = mEtInput.getText().toString();
        if(v.getId()== R.id.btn_del) {
            if(sTmp.length() > 0) {
                mEtInput.setText(sTmp.substring(0, sTmp.length()-1));
            }
            return;
        }

        if(sTmp.length() == mInputLen) {
            return;
        }

        if(v.getId()== R.id.btn1){ mEtInput.setText(sTmp+"1");}
        if(v.getId()== R.id.btn2){ mEtInput.setText(sTmp+"2");}
        if(v.getId()== R.id.btn3){ mEtInput.setText(sTmp+"3");}
        if(v.getId()== R.id.btn4){ mEtInput.setText(sTmp+"4");}
        if(v.getId()== R.id.btn5){ mEtInput.setText(sTmp+"5");}
        if(v.getId()== R.id.btn6){ mEtInput.setText(sTmp+"6");}
        if(v.getId()== R.id.btn7){ mEtInput.setText(sTmp+"7");}
        if(v.getId()== R.id.btn8){ mEtInput.setText(sTmp+"8");}
        if(v.getId()== R.id.btn9){ mEtInput.setText(sTmp+"9");}
        if(v.getId()== R.id.btn0){ mEtInput.setText(sTmp+"0");}
        if(v.getId()== R.id.btn_x){ mEtInput.setText(sTmp+"X");}

    }

    private void resetInput() {
        int inputLen = 0;
        String inputType = "";
        if(mRb2p.isChecked()) {
            inputType = QXConstant.CLASS_2P;
            inputLen = 4;
        }
        if(mRb3p.isChecked()) {
            inputType = QXConstant.CLASS_3P;
            inputLen = 4;
        }
        if(mRb4p.isChecked()) {
            inputType = QXConstant.CLASS_4P;
            inputLen = 4;
        }
        if(mRb2px.isChecked()) {
            inputType = QXConstant.CLASS_2PX;
            inputLen = 2;
        }
        if(mRb3px.isChecked()) {
            inputType = QXConstant.CLASS_3PX;
            inputLen = 3;
        }
        if(mRb4px.isChecked()) {
            inputType = QXConstant.CLASS_4PX;
            inputLen = 4;
        }

        mInputLen = inputLen;
        mInputType = inputType;
    }

    private boolean checkInput() {
        resetInput();
        String sTmp = mEtInput.getText().toString();
        if(sTmp.length()<mInputLen) {
            showToast("请继续输入");
            return false;
        }

        if(QXConstant.CLASS_2P.equals(mInputType)) {
            if(findCount(sTmp) != 2) {
                showToast("输入不正确");
                return false;
            }
        }
        if(QXConstant.CLASS_3P.equals(mInputType)) {
            if(findCount(sTmp) != 1) {
                showToast("输入不正确");
                return false;
            }
        }
        if(QXConstant.CLASS_4P.equals(mInputType)) {
            if(findCount(sTmp) != 0) {
                showToast("输入不正确");
                return false;
            }
        }

        sTmp = mEtOdds.getText().toString();
        if(sTmp.length() < 1) {
            showToast("请输入赔率");
            return false;
        }

        try {
            if(Integer.parseInt(sTmp) < 0) {
                showToast("请输入正确的赔率");
                return false;
            }
        }catch (NumberFormatException e) {
            showToast("赔率输入错误");
            return false;
        }
        return true;
    }


    private int findCount(String str) {
        return searchCount(str, "X");
    }

    private int searchCount(String str,String strRes) {
        int n = 0;//计数器
        int index = 0;//指定字符的长度
        index = str.indexOf(strRes);
        while(index!=-1) {
            n++;
            index = str.indexOf(strRes,index+1);
        }

        return n;
    }

    @OnClick(R.id.btn_save)
    public void saveOdds() {
        if(!checkInput()) {
            return;
        }

        RequestParamsMap map = new RequestParamsMap();
        map.add("periodId", mPeriodId);
        map.add("playClass", mInputType);
        map.add("pValue", mEtInput.getText().toString());
        map.add("oddsNew", mEtOdds.getText().toString());
        request(HttpApi.getUrlWithHost(HttpApi.addPeriodOdds), map, BaseEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                BaseEntity entity = (BaseEntity) object;
                showToast(entity.getMessage());
                if (entity.isSuccess()) {
                    EventBus.getDefault().post(new EventPeriodOdds(EventPeriodOdds.KEY_REFRESH));
                    finish();
                }
            }

            @Override
            public boolean onFailure(int statusCode, String response) {
                return super.onFailure(statusCode, response);
            }
        }, true);
    }


}
