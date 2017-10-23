package com.havryliuk.itarticles.data;

import com.havryliuk.itarticles.data.local.database.IDataBaseHelper;
import com.havryliuk.itarticles.data.local.preferences.IPreferencesHelper;
import com.havryliuk.itarticles.data.remote.IApiHelper;
import com.havryliuk.itarticles.data.remote.helper.error.ErrorHandlerHelper;
import com.havryliuk.itarticles.data.remote.model.ArticleResponse;
import com.havryliuk.itarticles.data.remote.model.DouArticle;
import com.havryliuk.itarticles.data.remote.model.LoginResponse;
import com.havryliuk.itarticles.data.remote.model.ServerLoginRequest;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * App Data Manager class
 * Created by Igor Havrylyuk on 21.10.2017.
 */

public class DataManager implements IDataManager {

    private final IDataBaseHelper dbHelper;
    private final IPreferencesHelper applicationPreferences;
    private final IApiHelper apiHelper;

    @Inject
    public DataManager(IDataBaseHelper dbHelper,
                       IPreferencesHelper applicationPreferences, IApiHelper apiHelper) {
        this.dbHelper = dbHelper;
        this.applicationPreferences = applicationPreferences;
        this.apiHelper = apiHelper;
    }

    @Override
    public void setLoggedIn(boolean value) {
        applicationPreferences.setLoggedIn(value);
    }

    @Override
    public boolean isLoggedIn() {
        return applicationPreferences.isLoggedIn();
    }

    @Override
    public void setUserName(String userName) {
        applicationPreferences.setUserName(userName);
    }

    @Override
    public String getUserName() {
        return applicationPreferences.getUserName();
    }

    @Override
    public String getAccessToken() {
        return applicationPreferences.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        applicationPreferences.setAccessToken(accessToken);
    }

    @Override
    public Integer saveArticles(List<DouArticle> articles) {
        return dbHelper.saveArticles(articles);
    }

    @Override
    public Observable<List<DouArticle>> getArticles(String category) {
        return dbHelper.getArticles(category);
    }

    @Override
    public Observable<Boolean> deleteAllArticles() {
        return dbHelper.deleteAllArticles();
    }

    @Override
    public Observable<DouArticle> getArticleById(Long id) {
        return dbHelper.getArticleById(id);
    }

    @Override
    public Observable<List<DouArticle>> getFavorites() {
        return dbHelper.getFavorites();
    }

    @Override
    public Integer updateArticles(int articleId, boolean value) {
        return dbHelper.updateArticles(articleId, value);
    }

    @Override
    public Observable<ArticleResponse> getArticles(Map<String, String> options) {
        return apiHelper.getArticles(options);
    }

    @Override
    public Observable<LoginResponse> doServerLoginApiCall(ServerLoginRequest request) {
        return apiHelper.doServerLoginApiCall(request);
    }

    @Override
    public ErrorHandlerHelper getErrorHandlerHelper() {
        return apiHelper.getErrorHandlerHelper();
    }

    @Override
    public void setErrorHandler(ErrorHandlerHelper errorHandler) {
        apiHelper.setErrorHandler(errorHandler);
    }
}
