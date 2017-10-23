package com.havryliuk.itarticles.injection.module;

import android.content.Context;

import com.havryliuk.itarticles.BuildConfig;
import com.havryliuk.itarticles.data.remote.ArticlesApiHelper;
import com.havryliuk.itarticles.data.remote.ArticlesApiService;
import com.havryliuk.itarticles.data.remote.IApiHelper;
import com.havryliuk.itarticles.data.remote.helper.HeaderHelper;
import com.havryliuk.itarticles.data.remote.helper.error.ErrorHandlerHelper;
import com.havryliuk.itarticles.injection.qualifier.ApplicationContext;
import com.havryliuk.itarticles.injection.scope.ArticlesAppScope;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */

@Module(includes = { ContextModule.class, RxModule.class })
public class NetworkModule {

    @Provides
    @ArticlesAppScope
    public Interceptor httpInterceptor() {
        return new Interceptor() {
            @Override public Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request()
                        .newBuilder()
                        .method(chain.request().method(), chain.request().body())
                        .headers(HeaderHelper.getAppHeaders());
                return chain.proceed(requestBuilder.build());
            }
        };
    }

    @Provides
    @ArticlesAppScope
    public HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor interceptor =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override public void log(String message) {
                        Timber.i(message);
                    }
                });
        interceptor.setLevel(BuildConfig.DEBUG ?
                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return interceptor;
    }

    @Provides
    @ArticlesAppScope
    public OkHttpClient okHttpClient(Interceptor interceptor) {
        return new OkHttpClient.Builder().addInterceptor(interceptor)
                .addInterceptor(loggingInterceptor())
                .build();
    }

    @Provides
    @ArticlesAppScope
    public Retrofit retrofit(OkHttpClient okHttpClient ) {
        return new Retrofit.Builder().baseUrl(BuildConfig.BASE_API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @ArticlesAppScope
    public ArticlesApiService apiArticlesInterface(Retrofit retrofit) {
        return retrofit.create(ArticlesApiService.class);
    }


    @Provides
    @ArticlesAppScope
    public ErrorHandlerHelper errorHandlerHelper(@ApplicationContext Context context,
                                                 Retrofit retrofit ) {
        return new ErrorHandlerHelper(context, retrofit);
    }

    @Provides
    @ArticlesAppScope
    public IApiHelper apiHelper( ArticlesApiService articlesApiService,
                                 ErrorHandlerHelper errorHandlerHelper) {
        return new ArticlesApiHelper(articlesApiService, errorHandlerHelper);
    }

}
