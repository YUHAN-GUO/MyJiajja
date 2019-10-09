
package com.base.gyh.baselib.data.remote.retrofit;

import com.base.gyh.baselib.BuildConfig;
import com.base.gyh.baselib.base.BaseApplication;
import com.base.gyh.baselib.data.remote.retrofit.cookie.CookieManager;

import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * created by taofu on 2018/11/27
 **/
public class BaseDataService {

    private static final long DEFAULT_TIMEOUT = 20000;

    private static volatile Object mRetrofitService;


    public static Object getService(Class zclas) {

        if (mRetrofitService == null) {
            synchronized (BaseDataService.class) {
                if (mRetrofitService == null) {
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

                    if (BuildConfig.isDebug) {
                        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
                    } else {
                        logging.setLevel(HttpLoggingInterceptor.Level.NONE);
                    }

                    OkHttpClient httpClient = new OkHttpClient.Builder()
                            .addInterceptor(logging).
                                    cookieJar(new CookieManager(BaseApplication.getContext()))
//                            .addInterceptor(new AddHeaderInterceptor())
//                            .addInterceptor(new SaveCookieInterceptor())
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .build();

                    Retrofit retrofit = new Retrofit.Builder()
                            .client(httpClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .baseUrl(BuildConfig.ApiUrl)
                            .build();

                    mRetrofitService = retrofit.create(zclas);
                }
            }
        }
        return mRetrofitService;
    }
}
/**
 * 使用：创建一个接口 WanAndroidService 是app下的 可以参考 package com.base.gyh.baselib.data.remote.retrofit;
 * 注意不是lib下的需要自己在app下创建
 * public class DataService {
 * <p>
 * public static WanAndroidService getService(){
 * return  (WanAndroidService) BaseDataService.getService(WanAndroidService.class);
 * }
 * }
 */
