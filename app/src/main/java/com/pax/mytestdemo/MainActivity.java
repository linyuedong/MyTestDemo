package com.pax.mytestdemo;


import com.pax.mytestdemo.mvp.base.BaseMvpActivity;
import com.pax.mytestdemo.mvp.contract.MainContract;
import com.pax.mytestdemo.mvp.presenter.MainPresenter;


public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.MainView {

    @Override
    protected void init() {

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
