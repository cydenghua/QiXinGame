package com.dcode.wanjin.activity.period;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.dcode.corelibrary.network.BaseEntity;
import com.dcode.corelibrary.network.RequestCallbackSimply;
import com.dcode.corelibrary.network.RequestParamsMap;
import com.dcode.wanjin.R;
import com.dcode.wanjin.activity.WanJinBaseActivity;
import com.dcode.wanjin.entity.QxPeriod;
import com.dcode.wanjin.event.EventPeriod;
import com.dcode.wanjin.network.HttpApi;
import com.dcode.wanjin.util.QXConstant;
import com.dcode.wanjin.util.StrUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

public class PeriodEditActivity extends WanJinBaseActivity {

    private static final int MODE_ADD = 1;
    private static final int MODE_EDIT = 2;
    private static final String PARAM_MODE = "p_mode";
    private static final String PARAM_PERIOD = "p_perid";

    @BindView(R.id.et_period_time)
    EditText mEtPeriodTime;
    @BindView(R.id.et_period_id)
    EditText mEtPeriodId;
    @BindView(R.id.et_p1)
    EditText mEtP1;
    @BindView(R.id.et_p2)
    EditText mEtP2;
    @BindView(R.id.et_p3)
    EditText mEtP3;
    @BindView(R.id.et_p4)
    EditText mEtP4;
    @BindView(R.id.et_p5)
    EditText mEtP5;
    @BindView(R.id.et_p6)
    EditText mEtP6;
    @BindView(R.id.et_p7)
    EditText mEtP7;
    @BindView(R.id.rb_ing)
    RadioButton mRbIng;
    @BindView(R.id.rb_close)
    RadioButton mRbClose;
    @BindView(R.id.rb_out)
    RadioButton mRbOut;
    @BindView(R.id.rb_end)
    RadioButton mRbEnd;

    private int mMode = MODE_ADD;
    private QxPeriod mPeriod;

    public static void startActivityAdd(Context context, QxPeriod period) {
        Intent intent = new Intent(context, PeriodEditActivity.class);
        intent.putExtra(PARAM_MODE, MODE_ADD);
        intent.putExtra(PARAM_PERIOD, period);
        context.startActivity(intent);
    }

    public static void startActivityEdit(Context context, QxPeriod period) {
        Intent intent = new Intent(context, PeriodEditActivity.class);
        intent.putExtra(PARAM_MODE, MODE_EDIT);
        intent.putExtra(PARAM_PERIOD, period);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMode = getIntent().getIntExtra(PARAM_MODE, MODE_ADD);
        mPeriod = getIntent().getParcelableExtra(PARAM_PERIOD);

        setCurrStatus();
        mEtPeriodTime.setText(mPeriod.getpTime());
        mEtPeriodId.setText(StrUtil.integerToStr(mPeriod.getpId()));
        mEtP1.setText(StrUtil.shortToStr(mPeriod.getP1()));
        mEtP2.setText(StrUtil.shortToStr(mPeriod.getP2()));
        mEtP3.setText(StrUtil.shortToStr(mPeriod.getP3()));
        mEtP4.setText(StrUtil.shortToStr(mPeriod.getP4()));
        mEtP5.setText(StrUtil.shortToStr(mPeriod.getP5()));
        mEtP6.setText(StrUtil.shortToStr(mPeriod.getP6()));
        mEtP7.setText(StrUtil.shortToStr(mPeriod.getP7()));

        mEtPeriodTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });
        mEtPeriodTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDatePickDlg();
                }
            }
        });

    }

    @Override
    protected int layoutResource() {
        return R.layout.activity_period_edit;
    }

    @Override
    protected String topTitle() {
        return "奖期设置";
    }

    protected void showDatePickDlg() {
        //不允许修改时间，安全风险
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(StrUtil.strToDate(mPeriod.getpTime()));
//        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                PeriodEditActivity.this.mEtPeriodTime.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
//            }
//        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//        datePickerDialog.show();
    }

    @OnClick(R.id.btn_save)
    public void clickSave() {
        if (MODE_ADD == mMode) {
            addPeriod();
        }
        if (MODE_EDIT == mMode) {
            updatePeriod();
        }
    }

    private void addPeriod() {
        RequestParamsMap map = new RequestParamsMap();
        map.add("pId", mEtPeriodId.getText().toString());
        map.add("pTime", mEtPeriodTime.getText().toString());
        map.add("status", "" + getCurrStatus());
        request(HttpApi.getUrlWithHost(HttpApi.addPeriod), map, BaseEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                BaseEntity entity = (BaseEntity) object;
                showToast(entity.getMessage());
                EventBus.getDefault().post(new EventPeriod(EventPeriod.KEY_REFRESH));
                if (entity.isSuccess()) {
                    PeriodEditActivity.this.finish();
                }
            }
            @Override
            public boolean onFailure(int statusCode, String response) {
                return super.onFailure(statusCode, response);
            }
        }, true);
    }

    private void updatePeriod() {
        if(getCurrStatus() > QXConstant.PERIOD_STATUS_CLOSE) {
            if(mEtP1.getText().toString().isEmpty()
                    || mEtP2.getText().toString().isEmpty()
                    || mEtP3.getText().toString().isEmpty()
                    || mEtP4.getText().toString().isEmpty()
                    || mEtP5.getText().toString().isEmpty()
                    || mEtP6.getText().toString().isEmpty()
                    || mEtP7.getText().toString().isEmpty() ) {
                showToast("请输入开奖号码");
                return;
            }
        }

        RequestParamsMap map = new RequestParamsMap();
        map.add("sysId", mPeriod.getSysId().toString());
        map.add("pId", mEtPeriodId.getText().toString());
//        map.add("pTime", "");
        map.add("p1", mEtP1.getText().toString());
        map.add("p2", mEtP2.getText().toString());
        map.add("p3", mEtP3.getText().toString());
        map.add("p4", mEtP4.getText().toString());
        map.add("p5", mEtP5.getText().toString());
        map.add("p6", mEtP6.getText().toString());
        map.add("p7", mEtP7.getText().toString());
        map.add("status", "" + getCurrStatus());
//        map.add("status", "");
        request(HttpApi.getUrlWithHost(HttpApi.updatePeriod), map, BaseEntity.class, new RequestCallbackSimply() {
            @Override
            public void onSuccess(Object object) {
                BaseEntity entity = (BaseEntity) object;
                showToast(entity.getMessage());
                EventBus.getDefault().post(new EventPeriod(EventPeriod.KEY_REFRESH));
                if (entity.isSuccess()) {
                    PeriodEditActivity.this.finish();
                }
            }

            @Override
            public boolean onFailure(int statusCode, String response) {
                return super.onFailure(statusCode, response);
            }
        }, true);
    }

    public int getCurrStatus() {
        if(mRbIng.isChecked()) {     return QXConstant.PERIOD_STATUS_ING; }
        if(mRbClose.isChecked()) {   return QXConstant.PERIOD_STATUS_CLOSE; }
        if(mRbOut.isChecked()) {     return QXConstant.PERIOD_STATUS_OUT; }
        if(mRbEnd.isChecked()) {    return QXConstant.PERIOD_STATUS_END; }
        return 0;
    }

    public void setCurrStatus() {
        if(null == mPeriod.getStatus()) {
            return;
        }
        mRbIng.setChecked(mPeriod.getStatus() == QXConstant.PERIOD_STATUS_ING);
        mRbClose.setChecked(mPeriod.getStatus() == QXConstant.PERIOD_STATUS_CLOSE);
        mRbOut.setChecked(mPeriod.getStatus() == QXConstant.PERIOD_STATUS_OUT);
        mRbEnd.setChecked(mPeriod.getStatus() == QXConstant.PERIOD_STATUS_END);
    }


}
