package com.havryliuk.itarticles.injection.module;

import android.content.Context;

import com.havryliuk.itarticles.data.local.database.DouDataBaseHelper;
import com.havryliuk.itarticles.data.local.database.DouDbHelper;
import com.havryliuk.itarticles.data.local.database.IDataBaseHelper;
import com.havryliuk.itarticles.injection.qualifier.ApplicationContext;
import com.havryliuk.itarticles.injection.scope.ArticlesAppScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */

@Module(includes = ContextModule.class)
public class DatabaseModule {

    @Provides
    @ArticlesAppScope
    public DouDbHelper douDbHelper(@ApplicationContext Context context) {
        return new DouDbHelper(context);
    }

    @Provides
    @ArticlesAppScope
    public IDataBaseHelper dbHelper(DouDbHelper dbHelper) {
        return new DouDataBaseHelper(dbHelper);
    }

}
