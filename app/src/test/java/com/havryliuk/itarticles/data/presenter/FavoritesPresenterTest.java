package com.havryliuk.itarticles.data.presenter;

import com.havryliuk.itarticles.data.TestModels;
import com.havryliuk.itarticles.data.remote.model.DouArticle;
import com.havryliuk.itarticles.ui.favorites.FavoritesMvpView;
import com.havryliuk.itarticles.ui.favorites.FavoritesPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class FavoritesPresenterTest extends
    BasePresenterTest<FavoritesPresenter<FavoritesMvpView>, FavoritesMvpView> {

    @Override
    FavoritesPresenter<FavoritesMvpView> createPresenter() {
        return new FavoritesPresenter<>(compositeDisposableHelper, dataManager);
    }

    @Override
    FavoritesMvpView createView() {
        return mock(FavoritesMvpView.class);
    }

    @Test
    public void loadFavoritesSuccessfully() {
        List<DouArticle> articles = TestModels.getArticles();
        when(dataManager.getFavorites()).thenReturn(Observable.just(articles));

        presenter.loadFavorites();
        testScheduler.triggerActions();

    }

}