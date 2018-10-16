package com.dcode.corelibrary.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

/**
 * Created by ASIMO on 2017/4/20.
 * CommonDialog 不是dailog，只是包装AlertDialog.Builder
 *
 */

public class CommonDialog {

    private AlertDialog.Builder  mAlertDialog;

    public interface PositiveListener{
        void positiveAction();
    }

    public CommonDialog(Context context, String message, String negativeMsg, String positiveMsg, final PositiveListener listener){
        mAlertDialog = new AlertDialog.Builder(context);
        mAlertDialog.setMessage(message);
        mAlertDialog.setCancelable(false);
        if(!TextUtils.isEmpty(negativeMsg)) {
            mAlertDialog.setNegativeButton(negativeMsg, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        mAlertDialog.setPositiveButton(positiveMsg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(null != listener) {
                    listener.positiveAction();
                }
            }
        });
        mAlertDialog.show();
    }

}


