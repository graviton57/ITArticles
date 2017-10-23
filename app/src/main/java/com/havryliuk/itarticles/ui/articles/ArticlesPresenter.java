package com.havryliuk.itarticles.ui.articles;


import com.havryliuk.itarticles.data.DataManager;
import com.havryliuk.itarticles.data.remote.ArticlesApiHelper;
import com.havryliuk.itarticles.data.remote.helper.BaseViewSubscriber;
import com.havryliuk.itarticles.data.remote.helper.CompositeDisposableHelper;
import com.havryliuk.itarticles.data.remote.helper.error.ErrorHandlerHelper;
import com.havryliuk.itarticles.data.remote.model.ArticleResponse;
import com.havryliuk.itarticles.data.remote.model.DouArticle;
import com.havryliuk.itarticles.ui.base.BasePresenter;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import timber.log.Timber;

/**
 * Articles Presenter Class
 * Created by Igor Havrylyuk on 21.10.2017.
 */

public class ArticlesPresenter<V extends ArticlesMvpView> extends BasePresenter<V>
    implements ArticlesMvpPresenter<V> {

    @Inject
    public ArticlesPresenter(CompositeDisposableHelper compositeDisposableHelper,
                             DataManager dataManager) {
        super(compositeDisposableHelper, dataManager);
    }

    private void loadArticlesFromDb(String category) {
        getCompositeDisposableHelper().addDisposable(getDataManager()
                    .getArticles(category)
                    .compose(getCompositeDisposableHelper().<List<DouArticle>>applySchedulers())
                    .subscribe(new Consumer<List<DouArticle>>() {
                        @Override
                        public void accept(List<DouArticle> list) throws Exception {
                            if (!list.isEmpty()) {
                                getMvpView().showArticles(list);
                            } else {
                                getMvpView().showEmptyView();
                            }
                        }
                    }));
    }

    @Override
    public void loadArticles(String category, int page) {
        if (getMvpView().isNetworkConnected()) {
            getCompositeDisposableHelper().addDisposable(getDataManager()
                    .getArticles(buildOptions(category, page))
                    .flatMap(new Function<ArticleResponse, Observable<ArticleResponse>>() {
                        @Override
                        public Observable<ArticleResponse> apply(
                                final ArticleResponse response) throws Exception {
                            return Observable.fromCallable(new Callable<ArticleResponse>() {
                                @Override
                                public ArticleResponse call() throws Exception {
                                    getDataManager().saveArticles(response.getArticles());
                                    return response;
                                }
                            });
                        }
                    })
                    .compose(getCompositeDisposableHelper().<ArticleResponse>applySchedulers())
                    .subscribeWith(new ArticlesObserver(getMvpView(),
                            getDataManager().getErrorHandlerHelper())));
        } else {
            loadArticlesFromDb(category);
        }
    }

    @Override
    public void updateArticleFavorite(int articleId, boolean value) {
        getDataManager().updateArticles(articleId, value);
        Timber.d("ArticlesPresenter articleId=%d, favorite=%s", articleId, String.valueOf(value));
    }


    public class ArticlesObserver extends BaseViewSubscriber<ArticlesMvpView, ArticleResponse> {

        public ArticlesObserver(ArticlesMvpView view, ErrorHandlerHelper errorHandlerHelper) {
            super(view, errorHandlerHelper);
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            getMvpView().showEmptyView();
        }

        @Override
        public void onNext(ArticleResponse articleResponse) {
            super.onNext(articleResponse);
            if (articleResponse.getArticles().isEmpty() &
                    articleResponse.getNext() != null) {//if last data page not call showEmptyView()
                getMvpView().showEmptyView();
            } else {
                   getMvpView().showArticles(articleResponse.getArticles());
             }
        }
    }

    private HashMap<String, String> buildOptions(String category, int page) {
        HashMap<String, String> options = new HashMap<>();
        options.put(ArticlesApiHelper.OFFSET,
                String.valueOf((page - 1) * ArticlesApiHelper.LIMIT_DEFAULT));
        options.put(ArticlesApiHelper.LIMIT, String.valueOf(ArticlesApiHelper.LIMIT_DEFAULT));
        if (category != null) {
            options.put(ArticlesApiHelper.CATEGORY, category);
        }
        return options;
    }
}
