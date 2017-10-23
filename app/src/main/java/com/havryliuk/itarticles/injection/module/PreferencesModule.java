package com.havryliuk.itarticles.injection.module;

import android.content.Context;

import com.havryliuk.itarticles.data.local.preferences.AppPreferencesHelper;
import com.havryliuk.itarticles.data.local.preferences.CommonPreferencesHelper;
import com.havryliuk.itarticles.data.local.preferences.IPreferencesHelper;
import com.havryliuk.itarticles.injection.qualifier.ApplicationContext;
import com.havryliuk.itarticles.injection.scope.ArticlesAppScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */
@Module(includes = ContextModule.class)
public class PreferencesModule {

    @Provides
    @ArticlesAppScope
    CommonPreferencesHelper getPreferencesHelper(
            @ApplicationContext Context context) {
        return new CommonPreferencesHelper(context);
    }

    @Provides
    @ArticlesAppScope
    IPreferencesHelper getApplicationPreferences(
            CommonPreferencesHelper commonPreferencesHelper) {
        return new AppPreferencesHelper(commonPreferencesHelper);
    }

}
