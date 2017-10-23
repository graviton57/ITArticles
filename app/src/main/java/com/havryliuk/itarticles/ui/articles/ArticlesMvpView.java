package com.havryliuk.itarticles.ui.articles;


import com.havryliuk.itarticles.data.remote.model.DouArticle;
import com.havryliuk.itarticles.ui.base.BaseMvpView;

import java.util.List;

/**
 * Created by Igor Havrylyuk on 20.10.2017.
 */

public interface ArticlesMvpView extends BaseMvpView {

    void showArticles(List<DouArticle> articles);

    void updateArticle(int articleId, boolean isFavorite);

    void showEmptyView();


}
