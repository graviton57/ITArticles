package com.havryliuk.itarticles.data.presenter;

import com.havryliuk.itarticles.data.remote.model.LoginResponse;
import com.havryliuk.itarticles.data.remote.model.ServerLoginRequest;
import com.havryliuk.itarticles.ui.login.LoginMvpView;
import com.havryliuk.itarticles.ui.login.LoginPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by Igor Havrylyuk on 22.10.2017.
 */

@RunWith(MockitoJUnitRunner.Silent.class)
public class LoginPresenterTest extends
        BasePresenterTest<LoginPresenter<LoginMvpView>, LoginMvpView>  {

    @Override
    LoginPresenter<LoginMvpView> createPresenter() {
        return new LoginPresenter<>(compositeDisposableHelper, dataManager);
    }

    @Override
    LoginMvpView createView() {
        return mock(LoginMvpView.class);
    }

    @Test
    public void testServerLoginSuccess() {

        String email = "email@gmail.com";
        String password = "password";

        LoginResponse loginResponse = new LoginResponse();

        doReturn(Observable.just(loginResponse))
                .when(dataManager)
                .doServerLoginApiCall(new ServerLoginRequest(email, password));

        presenter.onServerLoginClick(email, password);
        testScheduler.triggerActions();
        verify(view).openMainActivity();
    }

}