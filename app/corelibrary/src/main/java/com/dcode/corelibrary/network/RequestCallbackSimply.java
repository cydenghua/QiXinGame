package com.dcode.corelibrary.network;

/**
 * Created by xiaoming on 2017/8/7.
 */

public abstract class RequestCallbackSimply implements RequestCallback {

    public abstract void onSuccess(Object object);

    @Override
    public boolean onFailure(int statusCode, String response) {
        return false;
    }

}
