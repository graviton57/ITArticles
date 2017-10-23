package com.havryliuk.itarticles;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.havryliuk.itarticles.injection.component.ApplicationComponent;
import com.havryliuk.itarticles.injection.component.DaggerApplicationComponent;
import com.havryliuk.itarticles.injection.module.ApplicationModule;
import com.havryliuk.itarticles.injection.module.ContextModule;
import com.havryliuk.itarticles.injection.module.DatabaseModule;
import com.havryliuk.itarticles.injection.module.NetworkModule;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Igor Havrylyuk on 20.10.2017.
 */

public class ArticlesApp extends Application {

    @Inject
    CalligraphyConfig calligraphyConfig;

    public static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);//Init Fresco

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .contextModule(new ContextModule(this))
                .databaseModule(new DatabaseModule())
                .networkModule(new NetworkModule())
                .build();
        applicationComponent.inject(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // We should not init our app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
