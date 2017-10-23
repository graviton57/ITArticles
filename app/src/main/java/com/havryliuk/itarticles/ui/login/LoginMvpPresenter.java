package com.havryliuk.itarticles.ui.login;


import com.havryliuk.itarticles.ui.base.Presenter;

/**
 * Created by Igor Havrylyuk on 22.10.2017.
 */

public interface LoginMvpPresenter<V extends LoginMvpView> extends Presenter<V> {

  void onServerLoginClick(String email, String password);

  void checkIfLoggedIn();
}
