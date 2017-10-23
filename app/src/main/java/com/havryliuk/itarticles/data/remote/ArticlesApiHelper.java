package com.havryliuk.itarticles.data.remote;


import com.havryliuk.itarticles.data.remote.helper.error.ErrorHandlerHelper;
import com.havryliuk.itarticles.data.remote.model.ArticleResponse;
import com.havryliuk.itarticles.data.remote.model.LoginResponse;
import com.havryliuk.itarticles.data.remote.model.ServerLoginRequest;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Igor Havrylyuk on 21.10.2017
 */

public class ArticlesApiHelper implements IApiHelper {

    private ArticlesApiService apiInterface;
    private ErrorHandlerHelper errorHandlerHelper;

    public final static String CATEGORY = "category";
    public final static String AUTHOR = "author";
    public final static String TAG = "tag";
    public final static String DATE_FROM = "date_from";//2017-07-24
    public final static String DATE_TO = "date_to";//2017-07-24
    public final static String LIMIT = "limit";
    public final static int    LIMIT_DEFAULT = 20;
    public final static String OFFSET = "offset";

    @Inject
    public ArticlesApiHelper(ArticlesApiService articlesApiService,
                             ErrorHandlerHelper errorHandlerHelper) {
        this.errorHandlerHelper = errorHandlerHelper;
        this.apiInterface = articlesApiService;
    }

    @Override
    public ErrorHandlerHelper getErrorHandlerHelper() {
        return errorHandlerHelper;
    }

    @Override
    public void setErrorHandler(ErrorHandlerHelper errorHandler) {
        this.errorHandlerHelper = errorHandler;
    }

    @Override
    public Observable<ArticleResponse> getArticles(Map<String, String> options) {
        return apiInterface.getArticles(checkOptions(options));
    }

    @Override
    public Observable<LoginResponse> doServerLoginApiCall(ServerLoginRequest request) {
        return null;
    }

    /*
     * Check options
     * if options == null get first page from api (default 20 items)
     */
    private Map<String, String> checkOptions(Map<String, String> options){
        if (options == null) {
            options = new HashMap<>();
        }
        return options;
    }
}
