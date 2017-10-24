package com.havryliuk.itarticles.data.presenter;

import com.havryliuk.itarticles.data.TestModels;
import com.havryliuk.itarticles.data.remote.helper.error.ServerNotAvailableException;
import com.havryliuk.itarticles.data.remote.model.ArticleResponse;
import com.havryliuk.itarticles.data.remote.model.DouArticle;
import com.havryliuk.itarticles.ui.articles.ArticlesMvpView;
import com.havryliuk.itarticles.ui.articles.ArticlesPresenter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class ArticlesPresenterTest extends
    BasePresenterTest<ArticlesPresenter<ArticlesMvpView>, ArticlesMvpView> {

    @Override
    ArticlesPresenter<ArticlesMvpView> createPresenter() {
        return new ArticlesPresenter<>(compositeDisposableHelper, dataManager);
    }

    @Override
    ArticlesMvpView createView() {
        return mock(ArticlesMvpView.class);
    }

    @Test
    public void loadArticlesSuccessfully() {
        ArticleResponse responseModel = TestModels.getArticleResponseModel(3);
        stubDataManagerLoadArticles(Observable.just(responseModel));

        presenter.loadArticles(null, "all", 1);
        testScheduler.triggerActions();

        verify(view).showArticles(responseModel.getArticles());
        verify(view, never()).showEmptyView();
        verify(view, never()).onError(anyString());
    }

    @Test
    public void loadArticlesFail() {
        ServerNotAvailableException exception = new ServerNotAvailableException();

        when(dataManager.getErrorHandlerHelper()).thenReturn(errorHandlerHelper);
        when(errorHandlerHelper.getProperErrorMessage(exception)).thenReturn("Server not available");

        stubDataManagerLoadArticles(Observable.<ArticleResponse>error(exception));

        presenter.loadArticles(null, "all", 1);
        testScheduler.triggerActions();

        verify(view).onError("Server not available");
        verify(view, never()).showArticles(ArgumentMatchers.<DouArticle>anyList());

    }

    private void stubDataManagerLoadArticles(Observable observable) {
        when(view.isNetworkConnected()).thenReturn(true);
        when(dataManager.getArticles(ArgumentMatchers.<String, String>anyMap())).thenReturn(observable);
    }

}