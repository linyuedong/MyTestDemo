package com.pax.mytestdemo.mvp.base;

/**
 * Created by linyd on 2018/9/10.
 */

public class BasePresenter<V extends BaseView> implements IPresenter<V> {

    protected V mView;


    @Override
    public void attachView(V v) {
        this.mView = v;
    }

    @Override
    public void detchView() {
        this.mView = null;
    }
}

