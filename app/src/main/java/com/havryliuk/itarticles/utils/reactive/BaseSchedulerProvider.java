package com.havryliuk.itarticles.utils.reactive;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();

    @NonNull
    Scheduler computation();

}
