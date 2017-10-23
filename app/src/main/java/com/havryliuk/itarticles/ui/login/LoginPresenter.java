package com.havryliuk.itarticles.ui.login;

import com.havryliuk.itarticles.R;
import com.havryliuk.itarticles.data.DataManager;
import com.havryliuk.itarticles.data.remote.helper.CompositeDisposableHelper;
import com.havryliuk.itarticles.ui.base.BasePresenter;
import com.havryliuk.itarticles.utils.AppUtils;

import javax.inject.Inject;

/**
 * Created by Igor Havrylyuk on 22.10.2017.
 */

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V>
    implements LoginMvpPresenter<V> {

    @Inject
    public LoginPresenter(CompositeDisposableHelper compositeDisposableHelper,
                          DataManager dataManager) {
        super(compositeDisposableHelper, dataManager);
    }

    @Override
    public void onServerLoginClick(String email, String password) {
        if (email == null || email.isEmpty()) {
            getMvpView().onError(R.string.empty_email);
            return;
        }
        if (!AppUtils.isEmailValid(email)) {
            getMvpView().onError(R.string.invalid_email);
            return;
        }
        if (password == null || password.isEmpty()) {
            getMvpView().onError(R.string.empty_password);
            return;
        }
        getDataManager().setLoggedIn(true);
        getMvpView().openMainActivity();
    }

    @Override
    public void checkIfLoggedIn() {
        if (getDataManager().isLoggedIn()){
            getMvpView().openMainActivity();
        }
    }


}
