package com.dcode.corelibrary.network;

import java.util.HashMap;

/**
 * Created by adev on 2017/4/12.
 */

public class RequestParamsMap {

    private HashMap<String, String> mData = new HashMap<>();

    public RequestParamsMap() {
    }

    public void add(String param, String value){
        if (param == null) return;
        mData.put(param, value);
    }

    public HashMap<String, String> getData(){
        return  mData;
    }
}
