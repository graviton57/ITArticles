package com.havryliuk.itarticles.injection.module;


import com.havryliuk.itarticles.data.remote.helper.CompositeDisposableHelper;
import com.havryliuk.itarticles.utils.reactive.SchedulerProvider;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;


/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */

@Module
public class RxModule {

    @Provides
    CompositeDisposable getCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider getSchedulerProvider() {
        return new SchedulerProvider();
    }

    @Provides
    CompositeDisposableHelper getCompositeDisposableHelper(
            CompositeDisposable compositeDisposable, SchedulerProvider schedulerProvider) {
        return new CompositeDisposableHelper(compositeDisposable, schedulerProvider);
    }

}
