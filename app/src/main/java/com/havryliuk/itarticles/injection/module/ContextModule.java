package com.havryliuk.itarticles.injection.module;

import android.content.Context;

import com.havryliuk.itarticles.injection.qualifier.ApplicationContext;
import com.havryliuk.itarticles.injection.scope.ArticlesAppScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context.getApplicationContext();
    }

    @Provides
    @ArticlesAppScope
    @ApplicationContext
    public Context getApplicationContext() {
        return context;
    }
}
