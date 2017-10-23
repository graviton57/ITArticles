package com.havryliuk.itarticles.ui.favorites;


import com.havryliuk.itarticles.ui.base.Presenter;

/**
 * Created by Igor Havrylyuk on 22.10.2017.
 */

public interface FavoritesMvpPresenter<V extends FavoritesMvpView> extends Presenter<V> {

    void loadFavorites();

    void updateArticleFavorite(int articleId, boolean value);
}
