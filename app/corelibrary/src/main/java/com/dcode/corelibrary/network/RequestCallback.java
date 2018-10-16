package com.dcode.corelibrary.network;

/**
 * Created by xiaoming on 2017/8/7.
 */

public interface RequestCallback {

    public void onSuccess(Object object);

    public boolean onFailure(int statusCode, String response);
}
