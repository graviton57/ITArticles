package com.havryliuk.itarticles.data.local.database;

import com.havryliuk.itarticles.data.remote.model.DouArticle;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Igor Havrylyuk on 20.10.2017.
 */

public interface IDataBaseHelper {

    Integer saveArticles(List<DouArticle> articles);

    Observable<List<DouArticle>> getArticles(String category);

    Observable<Boolean> deleteAllArticles();

    Observable<DouArticle> getArticleById(Long id);

    Observable<List<DouArticle>> getFavorites();

    Integer updateArticles(int articleId, boolean value);

}
