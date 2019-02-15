package com.ouer.fbook.activity.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/5/22.
 */

public abstract  class BaseActivity extends AppCompatActivity {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Unbinder unbind;
    /**
     * 初始化布局
     */
    public abstract int getLayoutRes();

    protected abstract void initData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutRes());
        // 初始化View注入
        unbind = ButterKnife.bind(this);

        initData();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        unbind.unbind();
        super.onDestroy();

    }

    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
