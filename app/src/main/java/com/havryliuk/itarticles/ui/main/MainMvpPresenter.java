package com.havryliuk.itarticles.ui.main;


import com.havryliuk.itarticles.ui.base.Presenter;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */

public interface MainMvpPresenter<V extends MainMvpView> extends Presenter<V> {

    void onDrawerAboutClick();

    void onDrawerOptionLogoutClick();

    void onDrawerShareClick();

    void onNavMenuCreated();

}
