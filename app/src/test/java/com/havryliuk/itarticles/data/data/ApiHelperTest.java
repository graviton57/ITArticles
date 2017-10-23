package com.havryliuk.itarticles.data.data;

import com.havryliuk.itarticles.data.TestModels;
import com.havryliuk.itarticles.data.remote.ArticlesApiHelper;
import com.havryliuk.itarticles.data.remote.ArticlesApiService;
import com.havryliuk.itarticles.data.remote.helper.error.ErrorHandlerHelper;
import com.havryliuk.itarticles.data.remote.model.ArticleResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */

@RunWith(MockitoJUnitRunner.class) public class ApiHelperTest {

    @Mock
    ArticlesApiService apiInterface;

    @Mock
    ErrorHandlerHelper errorHandlerHelper;
    private Map<String, String> options;
    private ArticlesApiHelper appApiHelper;

    @Before
    public void setUp() {
        appApiHelper = new ArticlesApiHelper(apiInterface, errorHandlerHelper);
        options = new HashMap<>();
        options.put("page", "1");
    }

    @Test
    public void loadArticles() {
        ArticleResponse responseModel = TestModels.getArticleResponseModel(3);

        when(apiInterface.getArticles(ArgumentMatchers.<String, String>anyMap()))
                .thenReturn(Observable.just(responseModel));
        TestObserver<ArticleResponse> testSubscriber = new TestObserver<>();

        appApiHelper.getArticles(options).subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertComplete();
        testSubscriber.assertValueCount(1);
        testSubscriber.assertValue(responseModel);
    }

}
