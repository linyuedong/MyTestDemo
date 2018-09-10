package com.pax.mytestdemo.mvp.base;

/**
 * Created by linyd on 2018/9/10.
 */

public interface IPresenter<V extends BaseView> {
    void attachView(V v);
    void detchView();


}
