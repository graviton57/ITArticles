package com.havryliuk.itarticles.injection.module;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.havryliuk.itarticles.injection.qualifier.ActivityContext;
import com.havryliuk.itarticles.injection.scope.ActivityScope;
import com.havryliuk.itarticles.ui.articles.ArticlesAdapter;
import com.havryliuk.itarticles.ui.articles.ArticlesMvpPresenter;
import com.havryliuk.itarticles.ui.articles.ArticlesMvpView;
import com.havryliuk.itarticles.ui.articles.ArticlesPresenter;
import com.havryliuk.itarticles.ui.favorites.FavoritesAdapter;
import com.havryliuk.itarticles.ui.favorites.FavoritesMvpPresenter;
import com.havryliuk.itarticles.ui.favorites.FavoritesMvpView;
import com.havryliuk.itarticles.ui.favorites.FavoritesPresenter;
import com.havryliuk.itarticles.ui.login.LoginMvpPresenter;
import com.havryliuk.itarticles.ui.login.LoginMvpView;
import com.havryliuk.itarticles.ui.login.LoginPresenter;
import com.havryliuk.itarticles.ui.main.DouPagerAdapter;
import com.havryliuk.itarticles.ui.main.MainMvpPresenter;
import com.havryliuk.itarticles.ui.main.MainMvpView;
import com.havryliuk.itarticles.ui.main.MainPresenter;
import com.havryliuk.itarticles.ui.search_dialog.FilterDialogMvpPresenter;
import com.havryliuk.itarticles.ui.search_dialog.FilterDialogMvpView;
import com.havryliuk.itarticles.ui.search_dialog.FilterDialogPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Igor Havrylyuk on 20.10.2017.
 */

@Module(includes = RxModule.class)
public class ActivityFragmentModule {

    private Activity activity;

    public ActivityFragmentModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    Activity getActivity() {
        return activity;
    }

    @Provides
    @ActivityContext
    Context getActivityContext() {
        return activity;
    }

    //Activities

    @Provides
    @ActivityScope
    MainMvpPresenter<MainMvpView> getMainPresenter(
            MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    @ActivityScope
    LoginMvpPresenter<LoginMvpView> getWelcomePresenter(
            LoginPresenter<LoginMvpView> presenter){
        return presenter;
    }

    //Fragments

    @Provides
    @ActivityScope
    ArticlesMvpPresenter<ArticlesMvpView> getArticlesPresenter (
            ArticlesPresenter<ArticlesMvpView> presenter) {
        return presenter;
    }

    @Provides
    @ActivityScope
    FavoritesMvpPresenter<FavoritesMvpView> getFavoritesPresenter (
            FavoritesPresenter<FavoritesMvpView> presenter) {
        return presenter;
    }

    @Provides
    @ActivityScope
    FilterDialogMvpPresenter<FilterDialogMvpView> getSearchDialogPresenter(
            FilterDialogPresenter<FilterDialogMvpView> presenter) {
        return presenter;
    }

    // Adapters

    @Provides
    @ActivityScope
    DouPagerAdapter getArticlesPagerAdapter(@ActivityContext Context context) {
        return new DouPagerAdapter(((AppCompatActivity)context).getSupportFragmentManager());
    }

    @Provides
    @ActivityScope
    ArticlesAdapter getArticlesAdapter(@ActivityContext Context context) {
        return new ArticlesAdapter(context);
    }

    @Provides
    @ActivityScope
    FavoritesAdapter getFavoritesAdapter(@ActivityContext Context context) {
        return new FavoritesAdapter(context);
    }

}
