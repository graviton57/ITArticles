package com.havryliuk.itarticles.data.presenter;

import android.support.annotation.CallSuper;

import com.havryliuk.itarticles.data.DataManager;
import com.havryliuk.itarticles.data.remote.helper.CompositeDisposableHelper;
import com.havryliuk.itarticles.data.remote.helper.error.ErrorHandlerHelper;
import com.havryliuk.itarticles.ui.base.BaseMvpView;
import com.havryliuk.itarticles.ui.base.BasePresenter;
import com.havryliuk.itarticles.utils.reactive.TestSchedulerProvider;

import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */

public abstract class BasePresenterTest<P extends BasePresenter<V>, V extends BaseMvpView> {

    @Mock
    ErrorHandlerHelper errorHandlerHelper;
    @Mock
    DataManager dataManager;

    public V view;
    public P presenter;
    public CompositeDisposableHelper compositeDisposableHelper;
    public TestScheduler testScheduler;

    @CallSuper
    @Before
    public void before() {
        initMocks(this);

        testScheduler = new TestScheduler();
        compositeDisposableHelper = new CompositeDisposableHelper(new CompositeDisposable(),
                new TestSchedulerProvider(testScheduler));

        view = createView();
        presenter = createPresenter();
        presenter.attachView(view);
    }

    @CallSuper
    @After
    public void tearDown() throws Exception {
        presenter.detachView();
    }

    abstract P createPresenter();
    abstract V createView();

}