package com.pax.mytestdemo.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.pax.mytestdemo.R;

public abstract class BaseMvpActivity<T extends  BasePresenter> extends BaseActivity implements BaseView{

    public T presenter;

    @Override
    public void onViewCreate() {
        super.onViewCreate();
        presenter = createPresenter();
        if(presenter == null){
            throw new IllegalStateException("Please call mPresenter in BaseMVPActivity(createPresenter) to create!");
        }else{
            presenter.attachView(this);
        }
    }

    protected abstract T createPresenter();
}
