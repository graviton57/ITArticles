package com.havryliuk.itarticles.data.remote;


import com.havryliuk.itarticles.data.remote.helper.error.ErrorHandlerHelper;
import com.havryliuk.itarticles.data.remote.model.ArticleResponse;
import com.havryliuk.itarticles.data.remote.model.LoginResponse;
import com.havryliuk.itarticles.data.remote.model.ServerLoginRequest;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Igor Havrylyuk on 20.10.2017
 */

public interface IApiHelper {

    Observable<ArticleResponse> getArticles(Map<String, String> options);

    Observable<LoginResponse> doServerLoginApiCall(ServerLoginRequest request);

    ErrorHandlerHelper getErrorHandlerHelper();

    void setErrorHandler(ErrorHandlerHelper errorHandler);

}
