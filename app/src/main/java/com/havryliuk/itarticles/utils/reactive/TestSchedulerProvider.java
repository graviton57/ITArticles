package com.havryliuk.itarticles.utils.reactive;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.TestScheduler;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */

public class TestSchedulerProvider implements BaseSchedulerProvider {

    private TestScheduler testScheduler;

    public TestSchedulerProvider(TestScheduler testScheduler) {
        this.testScheduler = testScheduler;
    }

    @NonNull
    @Override
    public Scheduler io() {
        return testScheduler;
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return testScheduler;
    }

    @NonNull
    @Override
    public Scheduler computation() {
        return testScheduler;
    }

    public TestScheduler getTestScheduler() {
        return testScheduler;
    }

}
