package com.havryliuk.itarticles.data.presenter;

import com.havryliuk.itarticles.ui.main.MainMvpView;
import com.havryliuk.itarticles.ui.main.MainPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest
    extends BasePresenterTest<MainPresenter<MainMvpView>, MainMvpView> {

    @Override
    MainPresenter<MainMvpView> createPresenter() {
        return new MainPresenter<>(compositeDisposableHelper, dataManager);
    }

    @Override
    MainMvpView createView() {
        return mock(MainMvpView.class);
    }

    @Test
    public void shareClick() {
        presenter.onDrawerShareClick();
        testScheduler.triggerActions();

        presenter.onDrawerAboutClick();
        testScheduler.triggerActions();
    }

}
