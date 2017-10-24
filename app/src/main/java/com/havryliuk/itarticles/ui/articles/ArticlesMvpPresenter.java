package com.havryliuk.itarticles.ui.articles;


import com.havryliuk.itarticles.ui.base.Presenter;

/**
 * Created by Igor Havrylyuk on 30.08.2017.
 */

public interface ArticlesMvpPresenter<V extends ArticlesMvpView> extends Presenter<V> {

    void loadArticles(String searchTag, String category, int page);

    void updateArticleFavorite(int articleId, boolean value);
}
