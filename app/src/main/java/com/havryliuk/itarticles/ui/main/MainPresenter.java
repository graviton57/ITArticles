package com.havryliuk.itarticles.ui.main;


import com.havryliuk.itarticles.data.DataManager;
import com.havryliuk.itarticles.data.remote.helper.CompositeDisposableHelper;
import com.havryliuk.itarticles.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V>
                                                  implements MainMvpPresenter<V> {

    @Inject
    public MainPresenter(CompositeDisposableHelper compositeDisposableHelper,
                         DataManager dataManager) {
        super(compositeDisposableHelper, dataManager);
    }

    @Override
    public void onDrawerAboutClick() {
        getMvpView().closeNavigationDrawer();
        getMvpView().showAboutFragment();
    }

    @Override
    public void onDrawerOptionLogoutClick() {
        getDataManager().setLoggedIn(false);
        getMvpView().showLoginActivity();
    }

    @Override
    public void onDrawerShareClick() {
        getMvpView().closeNavigationDrawer();
        getMvpView().shareApp();
    }

    @Override
    public void onNavMenuCreated() {
        if (!isViewAttached()) {
            return;
        }
        getMvpView().updateAppVersion();
    }

}
