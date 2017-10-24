package com.havryliuk.itarticles.injection.component;

import com.havryliuk.itarticles.injection.module.ActivityFragmentModule;
import com.havryliuk.itarticles.injection.scope.ActivityScope;
import com.havryliuk.itarticles.ui.main.MainActivity;
import com.havryliuk.itarticles.ui.articles.ArticlesFragment;
import com.havryliuk.itarticles.ui.favorites.FavoritesFragment;
import com.havryliuk.itarticles.ui.login.LoginActivity;
import com.havryliuk.itarticles.ui.search_dialog.FilterDialog;

import dagger.Component;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = ActivityFragmentModule.class)
public interface ActivityFragmentComponent {

    void inject(MainActivity mainActivity);

    void inject(LoginActivity loginActivity);

    void inject(ArticlesFragment articlesFragment);

    void inject(FavoritesFragment favoritesFragment);

    void inject(FilterDialog filterDialog);

}
