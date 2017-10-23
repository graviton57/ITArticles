package com.havryliuk.itarticles.injection.module;

import android.app.Application;

import com.havryliuk.itarticles.R;
import com.havryliuk.itarticles.data.DataManager;
import com.havryliuk.itarticles.data.IDataManager;
import com.havryliuk.itarticles.data.local.database.IDataBaseHelper;
import com.havryliuk.itarticles.data.local.preferences.IPreferencesHelper;
import com.havryliuk.itarticles.data.remote.IApiHelper;
import com.havryliuk.itarticles.injection.scope.ArticlesAppScope;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Igor Havrylyuk on 20.10.2017.
 */

@Module(includes = {ContextModule.class, PreferencesModule.class,
                    NetworkModule.class, DatabaseModule.class})
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    Application getApplication() {
        return application;
    }

    @Provides
    @ArticlesAppScope
    CalligraphyConfig getCalligraphyConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }

    @Provides
    @ArticlesAppScope
    DataManager getAppDataManager(IPreferencesHelper preferencesHelper, IDataBaseHelper dbHelper,
                                  IApiHelper apiHelper) {
        return new DataManager(dbHelper, preferencesHelper, apiHelper);
    }

    @Provides
    @ArticlesAppScope
    IDataManager getDataManager(DataManager appDataManager) {
        return appDataManager;
    }

}


