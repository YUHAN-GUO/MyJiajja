package com.base.gyh.baselib.data.remote.okhttp.builder;


import android.content.Context;
import android.os.Handler;

import com.base.gyh.baselib.data.remote.okhttp.OkHttpUtils;
import com.base.gyh.baselib.data.remote.okhttp.okcallback.ResultMyCall;
import com.base.gyh.baselib.utils.mylog.Logger;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by leo
 * on 2019/7/23.
 * post builder
 */
public class OkPostBuilder {
    /**
     * 下面是解析参数，包括成功后 解析type
     */
    private String url;
    /**
     * okHttpUtils里单例里唯一
     */
    private OkHttpClient okHttpClient;
    private Handler mDelivery;
    private Map<String, String> params;

    /**
     * 每次请求网络生成的请求request
     */
    private Request okHttpRequest;

    public OkPostBuilder() {
        this.okHttpClient = OkHttpUtils.getInstance().getOkHttpClient();
        this.mDelivery = OkHttpUtils.getInstance().getmDelivery();
    }


    public OkPostBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public OkPostBuilder build() {
        //遍历Map集合
        FormBody.Builder form = new FormBody.Builder();//表单对象，包含以input开始的对象,以html表单为主
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                Logger.d("%s+++++++++++++%s", "guoyuhan", entry.getKey() + "-----------" + entry.getValue());
                form.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody body = form.build();
        okHttpRequest = new Request.Builder().url(url).post(body).build();//采用post提交数据
        return this;
    }

    public OkPostBuilder url(String url) {
        this.url = url;
        return this;
    }

    public void enqueue(final ResultMyCall resultMyCall) {
        if (resultMyCall == null) {
            return;
        }
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                resultMyCall.onBefore();
            }
        });
        if (okHttpRequest != null) {
            Logger.d("%s++++++++++%s", "guoyuhan", "----------");
            okHttpClient.newCall(okHttpRequest).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mDelivery.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            resultMyCall.onError(e);
                        }
                    }, 50);
                    Logger.d("%s+++++++++++++%s", "guoyuhan", e.getMessage());
                    resultMyCall.onAfter();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful() && response != null) {
                        String result = response.body().string();
                        Logger.d("%s+++++++++++++%s", "guoyuhan", result);
                        resultMyCall.onSuccess(result);
                        mDelivery.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                resultMyCall.onAfter();
                            }
                        }, 50);
                    }
                }
            });
        }
    }
}
