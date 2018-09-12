package com.pax.mytestdemo;


import android.util.Log;

import com.pax.mytestdemo.mvp.base.BaseMvpActivity;
import com.pax.mytestdemo.mvp.contract.MainContract;
import com.pax.mytestdemo.mvp.presenter.MainPresenter;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.MainView {

    public static final String TAG = "main";

    @Override
    protected void init() {
        testCache();
    }


    private void testCache(){
        //缓存文件夹
        File cacheFile = new File(getExternalCacheDir().toString(),"cache");
        Log.i(TAG,"cacheFile = " + getExternalCacheDir().toString());
        //缓存大小为10M
        int cacheSize = 10 * 1024 * 1024;
        //创建缓存对象
        final Cache cache = new Cache(cacheFile,cacheSize);

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient.Builder()
                        .cache(cache)
                        .build();
                //官方的一个示例的url
                String url = "http://publicobject.com/helloworld.txt";

                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Call call1 = client.newCall(request);
                Response response1 = null;
                try {
                    //第一次网络请求
                    response1 = call1.execute();
                    Log.i(TAG, "testCache: response1 :"+response1.body().string());
                    Log.i(TAG, "testCache: response1 cache :"+response1.cacheResponse());
                    Log.i(TAG, "testCache: response1 network :"+response1.networkResponse());
                    response1.body().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Call call12 = client.newCall(request);

                try {
                    //第二次网络请求
                    Response response2 = call12.execute();
                    Log.i(TAG, "testCache: response2 :"+response2.body().string());
                    Log.i(TAG, "testCache: response2 cache :"+response2.cacheResponse());
                    Log.i(TAG, "testCache: response2 network :"+response2.networkResponse());
                    Log.i(TAG, "testCache: response1 equals response2:"+response2.equals(response1));
                    response2.body().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }
}
