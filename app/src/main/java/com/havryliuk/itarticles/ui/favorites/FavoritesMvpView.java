package com.havryliuk.itarticles.ui.favorites;


import com.havryliuk.itarticles.data.remote.model.DouArticle;
import com.havryliuk.itarticles.ui.base.BaseMvpView;

import java.util.List;

/**
 * Created by Igor Havrylyuk on 22.10.2017.
 */

public interface FavoritesMvpView extends BaseMvpView {

    void showFavorites(List<DouArticle> favorites);

    void updateFavorite(int articleId, boolean isFavorite);

    void showEmptyView();


}
