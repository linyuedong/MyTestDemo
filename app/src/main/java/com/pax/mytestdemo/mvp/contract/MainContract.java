package com.pax.mytestdemo.mvp.contract;

import com.pax.mytestdemo.mvp.base.BaseView;
import com.pax.mytestdemo.mvp.base.BasePresenter;

/**
 * Created by linyd on 2018/9/11.
 */

public interface MainContract {

    public interface MainView extends BaseView{

    }

    public interface MainPresenter extends BasePresenter<MainView> {

    }
}
