package com.base.gyh.baselib.data.remote.okhttp;


import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import com.base.gyh.baselib.base.IBaseHttpResultCallBack;
import com.base.gyh.baselib.data.bean.ParamsBuilder;
import com.base.gyh.baselib.data.remote.okhttp.okcallback.NetWorkListener;
import com.base.gyh.baselib.data.remote.okhttp.okcallback.OnDownloadListener;
import com.base.gyh.baselib.data.remote.okhttp.okcallback.ResultMyCall;
import com.base.gyh.baselib.utils.mylog.Logger;
import com.base.gyh.baselib.widgets.dialog.pop.LoadingPop;

import java.io.IOException;
import java.util.HashMap;
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
 * on 2019/8/1.
 * 统一的操作方式都在这，解析，loading
 */
public class ModelBase {


    //        OkHttpClient okHttpClient = OkHttpUtils.getInstance().getOkHttpClient();
//        Handler handler = OkHttpUtils.getInstance().getmDelivery();
//        FormBody.Builder form = new FormBody.Builder();//表单对象，包含以input开始的对象,以html表单为主
//        if (!params.isEmpty()) {
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                Logger.d("%s+++++++++++++%s", "guoyuhan", entry.getKey() + "-----------" + entry.getValue());
//                form.add(entry.getKey(), entry.getValue());
//            }
//        }
//        RequestBody body = form.build();
//        Request okHttpRequest = new Request.Builder().url(url).post(body).build();//采用post提交数据
//        okHttpClient.newCall(okHttpRequest).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Logger.d("%s++++++++++++%s", "guoyuhan", e.getMessage());
//                    }
//                });
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String s = response.body().string();
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Logger.d("%s++++++++++++%s", "guoyuhan", s);
//                    }
//                });
//            }
//        });


    //okhttp get请求
    public void sendOkHttpGet(final ParamsBuilder paramsBuilder, final ResultMyCall callBack) {
        Logger.d("%s++++++++++%s", "guoyh", "---------------------");
        String url = paramsBuilder.getUrl();
        if (url == null) {
            throw new NullPointerException("URl  不能为空");
        }
        OkHttpUtils.getInstance().get().url(url).build().enqueue(callBack);
    }

    public void sendOkHttpPost(final ParamsBuilder paramsBuilder, final ResultMyCall callBack) {
        String url = paramsBuilder.getUrl();
        if (url == null) {
            throw new NullPointerException("URl  不能为空");
        }
        OkHttpUtils.getInstance().post().url(url).params(paramsBuilder.getParams()).build().enqueue(callBack);
    }
    public void sendOkHttpDownload(ParamsBuilder paramsBuilder, OnDownloadListener onDownloadListener) {
        String tag = paramsBuilder.getTag();
        if (TextUtils.isEmpty(tag)) {
            //不设置tag，默认用类路径名。
            tag = onDownloadListener.getClass().toString();
        }
        OkHttpUtils.getInstance().download().url(paramsBuilder.getUrl())
                .tag(tag)
                .path(paramsBuilder.getPath())
                .fileName(paramsBuilder.getFileName())
                .resume(paramsBuilder.isResume())
                .build()
                .enqueue(onDownloadListener);
    }
}
