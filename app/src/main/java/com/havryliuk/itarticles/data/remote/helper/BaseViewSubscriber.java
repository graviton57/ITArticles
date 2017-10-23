package com.havryliuk.itarticles.data.remote.helper;


import com.havryliuk.itarticles.data.remote.helper.error.ErrorHandlerHelper;
import com.havryliuk.itarticles.ui.base.BaseMvpView;

import io.reactivex.observers.DisposableObserver;


/**
 * Default DisposableObserver base class to be used whenever you want default error handling
 * Created by Igor Havrylyuk on 21.10.2017
 */

public class BaseViewSubscriber<V extends BaseMvpView, T> extends DisposableObserver<T> {

    private V view;
    private ErrorHandlerHelper errorHandlerHelper;

    public BaseViewSubscriber(V view, ErrorHandlerHelper errorHandlerHelper) {
        this.view = view;
        this.errorHandlerHelper = errorHandlerHelper;
    }

    public V getView() {
        return view;
    }

    public boolean shouldShowError() {
        return true;
    }


    @Override
    public void onError(Throwable e) {
        if (view == null) {
            return;
        }
        if (shouldShowError()) {
            view.onError(errorHandlerHelper.getProperErrorMessage(e));
        }
    }

    @Override
    public void onComplete() {
        if (view == null) {
            return;
        }
    }

    @SuppressWarnings("UnnecessaryReturnStatement")
    @Override
    public void onNext(T t) {
        if (view == null) {
            return;
        }
    }

}
