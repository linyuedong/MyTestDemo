package com.pax.mytestdemo.mvp.base;

public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity implements BaseView{

    public T mPresenter;

    @Override
    public void onViewCreate() {
        super.onViewCreate();
        mPresenter = createPresenter();
        if(mPresenter == null){
            throw new IllegalStateException("Please call mPresenter in BaseMVPActivity(createPresenter) to create!");
        }else{
            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        if(mPresenter != null){
            mPresenter.detchView();
        }
        super.onDestroy();
    }

    protected abstract T createPresenter();
}
