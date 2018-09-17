package com.pax.mytestdemo.http;

import android.support.compat.BuildConfig;
import android.util.Log;

import com.pax.mytestdemo.Utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import app.MyApplication;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by linyd on 2018/9/11.
 */

public class RetrofitManager {

    public static final String TAG = "re";
    public static final long  CONNECTION_TIMEOUT = 60 ;
    public static final long  READ_TIMEOUT = 60;
    public static final long  WRETE_TIMEOUT = 60;
    public static final int cacheSize = 10 * 1024 * 1024;
    public String BASE_URL = "";

    public void getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkhttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public OkHttpClient getOkhttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设置超时
        builder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(READ_TIMEOUT,TimeUnit.SECONDS);
        builder.readTimeout(WRETE_TIMEOUT,TimeUnit.SECONDS);
        //设置缓存
        File file = new File(MyApplication.getContext().getCacheDir(), "Okcache");
        Log.i(TAG,"CacheDir = " + MyApplication.getContext().getCacheDir());
        Cache cache = new Cache(file, cacheSize);
        builder.cache(cache);
        builder.addInterceptor(new NoNetInterceptor());
        builder.addNetworkInterceptor(new NetInterceptor());
        //日志拦截器
        if(BuildConfig.DEBUG){
            builder.addInterceptor(getHttpLoggingInterceptor());
        }
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();

    }



    public HttpLoggingInterceptor getHttpLoggingInterceptor(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override public void log(String message) {
                Log.d("Test: " , message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }


    static class NetInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            if(Utils.isNetworkConnected()){
                int maxTime = 60;
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxTime)
                        .build();
            }
            Log.i(TAG,"NetInterceptor: response cache :"+ response.cacheResponse());
            Log.i(TAG,"NetInterceptor: response net :"+ response.networkResponse());
            return response;
        }
    }


    public class NoNetInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {

            if(!Utils.isNetworkConnected()){
                Request request = chain.request().newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Response response = chain.proceed(request).newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=3600")
                        .removeHeader("Pragma")
                        .build();
                Log.i(TAG,"CInterceptor: response cache :"+ response.cacheResponse());
                Log.i(TAG,"NoNetInterceptor: response net :"+ response.networkResponse());
                return response;
            }

            Request request = chain.request();
            Response response = chain.proceed(request);
            Log.i(TAG,"CInterceptor: response cache :"+ response.cacheResponse());
            Log.i(TAG,"NoNetInterceptor: response net :"+ response.networkResponse());
            return response;
        }
    }






}
