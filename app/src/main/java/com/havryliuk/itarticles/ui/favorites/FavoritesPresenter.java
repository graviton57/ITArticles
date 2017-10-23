package com.havryliuk.itarticles.ui.favorites;


import com.havryliuk.itarticles.data.DataManager;
import com.havryliuk.itarticles.data.remote.helper.CompositeDisposableHelper;
import com.havryliuk.itarticles.data.remote.model.DouArticle;
import com.havryliuk.itarticles.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Articles Presenter Class
 * Created by Igor Havrylyuk on 22.10.2017.
 */

public class FavoritesPresenter<V extends FavoritesMvpView> extends BasePresenter<V>
    implements FavoritesMvpPresenter<V> {

    @Inject
    public FavoritesPresenter(CompositeDisposableHelper compositeDisposableHelper,
                              DataManager dataManager) {
        super(compositeDisposableHelper, dataManager);
    }

    @Override
    public void loadFavorites() {
        getCompositeDisposableHelper().addDisposable(getDataManager()
                .getFavorites()
                .compose(getCompositeDisposableHelper().<List<DouArticle>>applySchedulers())
                .subscribe(new Consumer<List<DouArticle>>() {
                    @Override
                    public void accept(List<DouArticle> list) throws Exception {
                        if (!list.isEmpty()) {
                            getMvpView().showFavorites(list);
                        } else {
                            getMvpView().showEmptyView();
                        }
                    }
                }));
    }

    @Override
    public void updateArticleFavorite(int articleId, boolean value) {
        getDataManager().updateArticles(articleId, value);
        Timber.d("FavoritesPresenter articleId=%d, favorite=%s", articleId, String.valueOf(value));
    }

}
