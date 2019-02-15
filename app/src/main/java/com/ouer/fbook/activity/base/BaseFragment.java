/*
 * ========================================================
 * Copyright(c) 2014 杭州偶尔科技-版权所有
 * ========================================================
 * 本软件由杭州偶尔科技所有, 未经书面许可, 任何单位和个人不得以
 * 任何形式复制代码的部分或全部, 并以任何形式传播。
 * 公司网址
 * 
 * 			http://www.kkkd.com/
 * 
 * ========================================================
 */

package com.ouer.fbook.activity.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author : Zhenshui.Xia
 * @date   :  2014年11月19日
 * @desc   :
 */
public abstract class BaseFragment extends Fragment {

    protected View rootView;
    private Unbinder unbind;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(this.getLayoutResource(), container, false);
        }
        unbind = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    //获取布局文件
    protected abstract int getLayoutResource();
    protected abstract void initView();
    @Override
    public void onDestroyView() {
        compositeDisposable.clear();
        unbind.unbind();
        super.onDestroyView();
    }

    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    /**
     * 获取相关联的基类fragment activity
     * @return
     */
    public BaseActivity getBaseActivity() {
        FragmentActivity activity = getActivity();

        if(activity instanceof BaseActivity) {
            return (BaseActivity)activity;
        }

        return null;
    }

    /**
     * 处理广播消息
     */
    protected void onReceive(Intent intent) {

    }

}
