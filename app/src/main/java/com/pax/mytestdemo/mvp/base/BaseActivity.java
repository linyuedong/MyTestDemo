package com.pax.mytestdemo.mvp.base;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pax.mytestdemo.R;

import app.MyApplication;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        onViewCreate();
        MyApplication.addActivity(this);
        ButterKnife.bind(this);
        init();
    }

    public void onViewCreate() {
    }


    protected abstract void init();
    public abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.removeActivty(this);
    }
}
