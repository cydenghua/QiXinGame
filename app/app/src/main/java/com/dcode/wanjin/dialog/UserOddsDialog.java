package com.dcode.wanjin.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dcode.wanjin.R;
import com.dcode.wanjin.util.StrUtil;

import java.util.List;

public class UserOddsDialog extends Dialog {

    //定义回调事件，用于dialog的点击事件
    public interface OnChooseListener{
        void getValue(String value);
    }

    private Context mContext;
    private String mCurrOdds;
    private List<String> mData;
    private ListView mListView;
    private String mTitle;
    private OnChooseListener mListener;

    public UserOddsDialog(@NonNull Context context, String title, String currOdds, List<String> data, OnChooseListener listener) {
        super(context);
        mContext = context;
        mCurrOdds = currOdds;
        mData = data;
        mTitle = title;
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_odds_dia);
        setTitle("设置回水");
        mListView = findViewById(R.id.lv);
        TextView tv = findViewById(R.id.tv_title);
        tv.setText(mTitle);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, mData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mListView.setAdapter(adapter);

        int pos = mData.indexOf(StrUtil.numTrim(mCurrOdds));
        if(pos > -1) {
            mListView.setSelection(pos);
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListener.getValue(mData.get(i));
                UserOddsDialog.this.dismiss();
            }
        });
    }

}
