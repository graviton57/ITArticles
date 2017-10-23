package com.havryliuk.itarticles.data.remote.helper.error;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.JsonSyntaxException;
import com.havryliuk.itarticles.R;
import com.havryliuk.itarticles.data.remote.model.ArticleResponse;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.HttpURLConnection;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * Created by Igor Havrylyuk on 20.10.2017.
 */

public class ErrorHandlerHelper {

    private final Context context;
    private final Retrofit retrofit;

    @Inject
    public ErrorHandlerHelper(Context context ,Retrofit retrofit) {
        this.context = context;
        this.retrofit = retrofit;
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    public String getProperErrorMessage(Throwable throwable) {
        Throwable properException = getProperException(throwable);
        if (properException instanceof ServerException) {
            return context.getString(R.string.error_server);
        } else if (properException instanceof ServerNotAvailableException) {
            return context.getString(R.string.error_server_not_available);
        } else if (properException instanceof InternetConnectionException) {
            return context.getString(R.string.error_connection);
        } else if (properException instanceof NotFoundException) {
            return context.getString(R.string.error_not_found);
        } else if (properException instanceof UnauthorizedException) {
            return context.getString(R.string.error_unauthorized);
        } else if (properException instanceof ArticlesResponseException) {
            //return properException.getMessage();
            return throwable.getMessage();

        } else {
            return String.format(context.getString(R.string.error_default), throwable.getMessage());
        }
    }

    public Throwable getProperException(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            Response response = httpException.response();
            // try to parse the error
            Converter<ResponseBody, ArticleResponse> converter =
                    retrofit.responseBodyConverter(ArticleResponse.class, new Annotation[0]);
            ArticleResponse error = null;
            try {
                error = converter.convert(response.errorBody());
            } catch (IOException | JsonSyntaxException e) {
                e.printStackTrace();
            }
            if (error == null || error.getArticles() == null ) {
                return getThrowable(response.message(), response.code(), throwable);
            } else {
                return new ArticlesResponseException(error.toString());
            }
        } else if (throwable instanceof IOException) {
            return new InternetConnectionException();
        } else if (throwable instanceof NetworkErrorException) {
            return new InternetConnectionException();
        }
        return throwable;
    }

    @NonNull
    private Throwable getThrowable(String message, int code, Throwable throwable) {
        Throwable exception;
        switch (code) {
            case  HttpURLConnection.HTTP_NOT_FOUND:
                exception = new NotFoundException();
                break;
            case HttpURLConnection.HTTP_FORBIDDEN:
                exception = new UnauthorizedException();
                break;
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                exception = new UncheckedException(message);
                break;
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                exception = new ServerNotAvailableException();
                break;
            case HttpURLConnection.HTTP_NOT_IMPLEMENTED:
            case HttpURLConnection.HTTP_BAD_GATEWAY:
            case HttpURLConnection.HTTP_UNAVAILABLE:
            case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
                exception = new ServerException(throwable);
                break;
            default:
                exception = new UncheckedException(message);
                break;
        }
        return exception;
    }
}
