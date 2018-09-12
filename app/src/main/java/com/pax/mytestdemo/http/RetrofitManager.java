package com.pax.mytestdemo.http;

import android.support.compat.BuildConfig;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by linyd on 2018/9/11.
 */

public class RetrofitManager {

    public static final long  CONNECTION_TIMEOUT = 60 ;

    public void getRetrofit(){

    }

    public void getOkhttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        if(BuildConfig.DEBUG){
            builder.addInterceptor(getHttpLoggingInterceptor());
        }

        builder.build();

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



}
