package com.havryliuk.itarticles.injection.component;

import android.content.Context;

import com.havryliuk.itarticles.ArticlesApp;
import com.havryliuk.itarticles.data.DataManager;
import com.havryliuk.itarticles.injection.module.ApplicationModule;
import com.havryliuk.itarticles.injection.qualifier.ApplicationContext;
import com.havryliuk.itarticles.injection.scope.ArticlesAppScope;

import dagger.Component;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */

@ArticlesAppScope
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent  {

    void inject(ArticlesApp application);

    @ApplicationContext
    Context context();

    DataManager getDataManager();

}
