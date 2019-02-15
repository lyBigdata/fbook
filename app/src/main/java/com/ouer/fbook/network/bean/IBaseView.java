package com.ouer.fbook.network.bean;


import io.reactivex.disposables.Disposable;

/**
 * @author hetao
 */

public interface IBaseView {
    void addDisposable(Disposable disposable);
}