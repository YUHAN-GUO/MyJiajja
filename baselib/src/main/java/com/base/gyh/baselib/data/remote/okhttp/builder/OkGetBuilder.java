package com.base.gyh.baselib.data.remote.okhttp.builder;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.base.gyh.baselib.base.BaseApplication;
import com.base.gyh.baselib.data.remote.okhttp.OkHttpUtils;
import com.base.gyh.baselib.data.remote.okhttp.okcallback.ResultMyCall;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.just.agentweb.LogUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by leo
 * on 2019/7/22.
 * get builder
 */
public class OkGetBuilder {
    /**
     * 下面是解析参数，包括成功后 解析type
     */
    private String url;
    /**
     * okHttpUtils里单例里唯一
     */
    private OkHttpClient okHttpClient;
    private Handler mDelivery;
    private boolean onlyOneNet;
    private String tag;

    /**
     * 每次请求网络生成的请求request
     */
    private Request okHttpRequest;

    public OkGetBuilder() {
        this.okHttpClient = OkHttpUtils.getInstance().getOkHttpClient();
        this.mDelivery = OkHttpUtils.getInstance().getmDelivery();
    }

    public OkGetBuilder build() {
        //头部的builder
//        okHttpRequest = new Request.Builder().url(appendParams(url, params)).tag(tag).headers(appendHeaders(headers)).build();
        okHttpRequest = new Request.Builder()
                .url(url)
                .build();
        return this;
    }
    public OkGetBuilder url(String url){
        this.url = url;
        return this;
    }


    public void enqueue(final ResultMyCall resultMyCall) {
        if (resultMyCall == null){
            return;
        }
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                resultMyCall.onBefore();
            }
        });
        okHttpClient.newCall(okHttpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mDelivery.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resultMyCall.onError(e);
                    }
                },50);
                resultMyCall.onAfter();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Logger.d("%s+++++++++++++%s","guoyh",result);
                resultMyCall.onSuccess(result);

                mDelivery.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        resultMyCall.onAfter();
                    }
                }, 50);
            }
        });
    }
}
