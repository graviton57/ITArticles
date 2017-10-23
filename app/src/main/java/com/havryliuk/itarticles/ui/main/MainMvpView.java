package com.havryliuk.itarticles.ui.main;


import com.havryliuk.itarticles.ui.base.BaseMvpView;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */

public interface MainMvpView extends BaseMvpView {

    void showAboutFragment();

    void showLoginActivity();

    void shareApp();

    void closeNavigationDrawer();

    void updateAppVersion();

}
