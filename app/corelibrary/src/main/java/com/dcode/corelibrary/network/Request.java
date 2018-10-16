package com.dcode.corelibrary.network;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by July<pangcheinug@gmail.com> on 2014/12/11.
 */
public class Request {
    private OkHttpClient mOkHttpClient;
    private volatile static Request instance;

    /**
     * Returns singleton class instance
     */
    public static Request getInstance() {
        if (instance == null) {
            synchronized (Request.class) {
                if (instance == null) {
                    instance = new Request();
                }
            }
        }
        return instance;
    }

    protected Request() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();        //创建okHttpClient对象
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);
        mOkHttpClient = builder.build();
    }

    public OkHttpClient getRequest() {
        return mOkHttpClient;
    }

    public void post(final Context context, final String url, final String token, RequestParamsMap params, final Class<BaseEntity> clazz, final RequestCallbackSimply callback){
        Log.i("DCODE REQUEST,", "URL = " + url);
        FormBody.Builder builder = new FormBody.Builder();
        Iterator iter = params.getData().entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            builder.add((String)entry.getKey(), String.valueOf(entry.getValue()));
            Log.i("DCODE REQUEST,", entry.getKey()+" = " + entry.getValue());
        }
        RequestBody requestBody = builder.build();
        final okhttp3.Request request = new okhttp3.Request.Builder()
                .addHeader("token", token)
                .url(url).post(requestBody).tag(context).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("DCODE REQUEST, Err:", e.toString());
                if(e.toString().contains("closed")||e.toString().contains("Canceled")){
                    //主动取消的情况下(Socket closed;Canceled)
                }else {//其它情况
                    callback.onFailure(-1, e.toString());
                }
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(call.isCanceled()){
                    return;
                }
                Object object = null;
                String sJson = response.body().string();
                Log.i("DCODE REQUEST,", "REQUEST SUCCESS.....");
                Log.i("DCODE REQUEST, JSON=", sJson);
                try {
                    object = GsonFactory.fromJson(sJson, clazz);
                } catch (Exception e) {
                    try {
                        sJson = sJson.replace("\":[]", "\":null");
                        object = GsonFactory.fromJson(sJson, clazz);
                    } catch (Exception e1) {
                        callback.onFailure(-2, "对象解释错误");
                        return;
                    }
                }
                callback.onSuccess(object);
            }
        });
    }

    /*@Deprecated
    public void post(Context context, final String url, RequestParams params, final Class<BaseEntity> clazz, final IEntityRequest callback) {
        FormBody.Builder builder = new FormBody.Builder();
        //临时方案： 此处传入的是RequestParams是Async对象，需要转化， 待稳定后彻底修改；
        String s =  params.toString();
        String[] sArr = s.split("[&]");
        for (String sParam : sArr) {
            String[] aParam = sParam.split("[=]");
            if(aParam.length > 1) {
                builder.add(aParam[0], aParam[1]);
                Log.i("EMEMED REQUEST,", aParam[0] + " = " + aParam[1]);
            }else {
                if(aParam[0] != "") {
                    builder.add(aParam[0], "");
                    Log.i("EMEMED REQUEST,", aParam[0] + " = ");
                }
            }
        }
        RequestBody requestBody = builder.build();
        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url).post(requestBody).tag(context).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(e.toString().contains("closed")||e.toString().contains("Canceled")){
                    //主动取消的情况下(Socket closed;Canceled)
                }else {//其它情况
                    callback.onFailure(-1, e.toString());
                }
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Object object = null;
                String sJson = response.body().string();
                Log.i("EMEMED REQUEST,", "REQUEST SUCCESS.....");
                Log.i("EMEMED REQUEST,", "URL = " + url);
                Log.i("EMEMED REQUEST, JSON=", sJson);
                try {
                    object = GsonFactory.fromJson(sJson, clazz);
                } catch (Exception e) {
                    try {
                        sJson = sJson.replace("\":[]", "\":null");
                        object = GsonFactory.fromJson(sJson, clazz);
                    } catch (Exception e1) {
                        callback.onFailure(-2, "对象解释错误");
                        return;
                    }
                }
                callback.onSuccess(object);
            }
        });
    }*/

    public void cancelRequest(Context context) {
        cancelTag(context);
    }

    public void cancelTag(Object tag)
    {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

}
