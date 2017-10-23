package com.havryliuk.itarticles.data.data;

import com.havryliuk.itarticles.BuildConfig;
import com.havryliuk.itarticles.data.TestModels;
import com.havryliuk.itarticles.data.local.database.DouDataBaseHelper;
import com.havryliuk.itarticles.data.local.database.DouDbHelper;
import com.havryliuk.itarticles.data.remote.model.DouArticle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.TestObserver;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 25)
public class DbHelperTest {

    private DouDataBaseHelper dbHelper;
    private DouDbHelper douDbHelper;

    @Before
    public void setUp() {
        douDbHelper = new DouDbHelper(RuntimeEnvironment.application);
        dbHelper = new DouDataBaseHelper(douDbHelper);
    }

    @Test
    public void getArticleById() {
        TestObserver<DouArticle> listTestSubscriber = new TestObserver<>();
        dbHelper.saveArticles(TestModels.getDbArticles());
        dbHelper.getArticleById((long) 51203).subscribe(listTestSubscriber);
        listTestSubscriber.assertNoErrors();
        listTestSubscriber.assertComplete();
        listTestSubscriber.assertValueCount(1);

    }

    @Test
    public void deleteAllArticles() {
        TestObserver<Boolean> listTestSubscriber = new TestObserver<>();
        dbHelper.deleteAllArticles().subscribe(listTestSubscriber);
        listTestSubscriber.assertNoErrors();
        listTestSubscriber.assertComplete();
        listTestSubscriber.assertValueCount(1);
    }

    @Test
    public void listAllArticles() {
        TestObserver<List<DouArticle>> listTestSubscriber = new TestObserver<>();
        dbHelper.getArticles("all").subscribe(listTestSubscriber);
        listTestSubscriber.assertNoErrors();
        listTestSubscriber.assertComplete();
        listTestSubscriber.assertValueCount(1);
        listTestSubscriber.assertValue(new ArrayList<DouArticle>());
    }

    @Test
    public void listFavorites() {
        TestObserver<List<DouArticle>> listTestSubscriber = new TestObserver<>();
        dbHelper.getFavorites().subscribe(listTestSubscriber);
        listTestSubscriber.assertNoErrors();
        listTestSubscriber.assertComplete();
        listTestSubscriber.assertValueCount(1);
        listTestSubscriber.assertValue(new ArrayList<DouArticle>());
    }

}
