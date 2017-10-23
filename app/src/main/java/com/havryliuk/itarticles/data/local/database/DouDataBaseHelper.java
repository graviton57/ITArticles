package com.havryliuk.itarticles.data.local.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import com.havryliuk.itarticles.data.local.database.DouContract.ArticlesEntry;
import com.havryliuk.itarticles.data.remote.model.DouArticle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import timber.log.Timber;

import static com.facebook.common.internal.Preconditions.checkNotNull;

/**
 * DataBase Helper class
 * Created by Igor Havrylyuk on 21.10.2017.
 */

@Singleton
public class DouDataBaseHelper implements IDataBaseHelper {

    private final ContentResolver contentResolver;

    @Inject
    public DouDataBaseHelper(DouDbHelper douDbHelper) {
        Timber.d("Create DouDataBaseHelper");
        this.contentResolver = douDbHelper.getContext().getContentResolver();
    }

    @Override
    public Integer saveArticles(List<DouArticle> articles) {
        checkNotNull(articles);
        ContentValues[] values = new ContentValues[articles.size()];
        for (int i = 0; i < articles.size(); i++) {
            values[i] = articleToContentValues(articles.get(i));
        }
        int insertCount = contentResolver.bulkInsert(ArticlesEntry.CONTENT_URI, values);
        return  insertCount;
    }

    @Override
    public Observable<List<DouArticle>> getArticles(String category) {
        return Observable.fromCallable(new Callable<List<DouArticle>>() {
            @Override public List<DouArticle> call() throws Exception {
                Cursor cursor = contentResolver.query(ArticlesEntry.CONTENT_URI, null,
                        null,
                        null,
                        ArticlesEntry.ARTICLE_PUBLISHED + " DESC ");
                List<DouArticle> list = toArticlesList(cursor);
                if (cursor != null) {
                    cursor.close();
                }
                return list;
            }
        });
    }

    @Override
    public Observable<Boolean> deleteAllArticles() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                int rowsDeleted = contentResolver.delete(ArticlesEntry.CONTENT_URI, null, null);
                return rowsDeleted != 0;
            }
        });
    }

    @Override
    public Observable<DouArticle> getArticleById(final Long id) {
        return Observable.fromCallable(new Callable<DouArticle>() {
            @Override
            public DouArticle call() throws Exception {
                DouArticle article = new DouArticle();
                Cursor cursor = contentResolver.query(ArticlesEntry.CONTENT_URI,
                        null,
                        ArticlesEntry.ARTICLE_ID + " = ? ",
                        new String[]{String.valueOf(id)},
                        null);
                if (!toArticlesList(cursor).isEmpty()){
                    article = toArticlesList(cursor).get(0);
                }
                if (cursor != null) {
                    cursor.close();
                }
                return article;
            }
        });
    }

    @Override
    public Observable<List<DouArticle>> getFavorites() {
        return Observable.fromCallable(new Callable<List<DouArticle>>() {
            @Override public List<DouArticle> call() throws Exception {
                Cursor cursor = contentResolver.query(ArticlesEntry.CONTENT_URI, null,
                        ArticlesEntry.ARTICLE_FAVORITE + " = ? ",
                        new String[]{"1"},
                        ArticlesEntry.ARTICLE_PUBLISHED + " DESC ");
                List<DouArticle> list = toArticlesList(cursor);
                if (cursor != null) {
                    Timber.d("getFavorites() count=%d", cursor.getCount());
                    cursor.close();
                }
                return list;
            }
        });
    }

    @Override
    public Integer updateArticles(int articleId, boolean isFavorite) {
        ContentValues cv = new ContentValues();
        cv.put(ArticlesEntry.ARTICLE_FAVORITE, isFavorite ? 0 : 1);
        int updateCount = contentResolver.update(ArticlesEntry.CONTENT_URI, cv,
                ArticlesEntry.ARTICLE_ID + " = ? ",
                new String[]{String.valueOf(articleId)});
        Timber.d("DouDataBaseHelper articleId=%d, isFavorite=%s , updateCount=%d",
                articleId, String.valueOf(isFavorite), updateCount);
        return updateCount;
    }

    private List<DouArticle> toArticlesList(Cursor cursor){
        List<DouArticle> result = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                DouArticle article = new DouArticle();
                article.setId(getInt(cursor, ArticlesEntry.ARTICLE_ID));
                article.setTitle(getString(cursor, ArticlesEntry.ARTICLE_TITLE));
                article.setUrl(getString(cursor, ArticlesEntry.ARTICLE_URL));
                article.setCategory(getString(cursor, ArticlesEntry.ARTICLE_CATEGORY));
                article.setAnnouncement(getString(cursor, ArticlesEntry.ARTICLE_ANNOUNCEMENT));
                article.setTags(getString(cursor, ArticlesEntry.ARTICLE_TAGS));
                article.setPageviews(getInt(cursor, ArticlesEntry.ARTICLE_PAGEVIEWS));
                article.setCommentsCount(getString(cursor, ArticlesEntry.ARTICLE_COMMENTS));
                article.setImgBig(getString(cursor, ArticlesEntry.ARTICLE_IMAGE));
                article.setAuthorName(getString(cursor, ArticlesEntry.ARTICLE_AUTHOR_NAME));
                article.setAuthorUrl(getString(cursor, ArticlesEntry.ARTICLE_AUTHOR_URL));
                article.setPublished(getString(cursor, ArticlesEntry.ARTICLE_PUBLISHED));
                article.setFavorite(getInt(cursor, ArticlesEntry.ARTICLE_FAVORITE) == 1);
                result.add(article);
            } while (cursor.moveToNext());
        } else {
            Timber.w("No articles!");
        }
        return result;
    }

    private ContentValues articleToContentValues(DouArticle douArticle){
        ContentValues cv = new ContentValues();
        cv.put(ArticlesEntry.ARTICLE_ID,  douArticle.getId());
        cv.put(ArticlesEntry.ARTICLE_TITLE, douArticle.getTitle());
        cv.put(ArticlesEntry.ARTICLE_URL, douArticle.getUrl());
        cv.put(ArticlesEntry.ARTICLE_CATEGORY, douArticle.getCategory());
        cv.put(ArticlesEntry.ARTICLE_ANNOUNCEMENT, douArticle.getAnnouncement());
        cv.put(ArticlesEntry.ARTICLE_TAGS, douArticle.getTags());
        cv.put(ArticlesEntry.ARTICLE_PAGEVIEWS, douArticle.getPageviews());
        cv.put(ArticlesEntry.ARTICLE_COMMENTS, douArticle.getCommentsCount());
        if (douArticle.getImgBig() == null || douArticle.getImgBig().isEmpty()) {
            if (douArticle.getImgSmall() == null || douArticle.getImgSmall().isEmpty()) {
                cv.put(ArticlesEntry.ARTICLE_IMAGE, "");
            } else {
                cv.put(ArticlesEntry.ARTICLE_IMAGE, douArticle.getImgSmall());
            }
        } else {
            cv.put(ArticlesEntry.ARTICLE_IMAGE, douArticle.getImgSmall());
        }
        cv.put(ArticlesEntry.ARTICLE_AUTHOR_NAME, douArticle.getAuthorName());
        cv.put(ArticlesEntry.ARTICLE_AUTHOR_URL, douArticle.getAuthorUrl());
        cv.put(ArticlesEntry.ARTICLE_PUBLISHED, douArticle.getPublished());
        return cv;
    }

    //helper methods

    private long getLong(Cursor cursor, String colName){
        int colInd = cursor.getColumnIndex(colName);
        return cursor.getLong(colInd);
    }

    private int getInt(Cursor cursor, String colName){
        int colInd = cursor.getColumnIndex(colName);
        return cursor.getInt(colInd);
    }

    private String getString(Cursor cursor, String colName){
        int colInd = cursor.getColumnIndex(colName);
        return cursor.getString(colInd);
    }

    private float getFloat(Cursor cursor, String colName){
        int colInd = cursor.getColumnIndex(colName);
        return cursor.getFloat(colInd);
    }
}
