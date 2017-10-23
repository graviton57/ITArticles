package com.havryliuk.itarticles.data.local.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;


/**
 * Created by Igor Havrylyuk on 20.10.2017.
 */

public class DouContract {

    public static final String CONTENT_AUTHORITY = "com.havryliuk.itarticles";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ARTICLES = "articles";

    public static final class ArticlesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_ARTICLES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTICLES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTICLES;

        // Table articles
        public static final String TABLE_NAME = "articles";

        public static final String ARTICLE_ID             = "article_id";
        public static final String ARTICLE_TITLE          = "title";
        public static final String ARTICLE_URL            = "url";
        public static final String ARTICLE_CATEGORY       = "category";
        public static final String ARTICLE_ANNOUNCEMENT   = "announcement";
        public static final String ARTICLE_TAGS           = "tags";
        public static final String ARTICLE_PAGEVIEWS      = "pageviews";
        public static final String ARTICLE_COMMENTS       = "comments_count";
        public static final String ARTICLE_IMAGE          = "img_big";
        public static final String ARTICLE_AUTHOR_NAME    = "author_name";
        public static final String ARTICLE_AUTHOR_URL     = "author_url";
        public static final String ARTICLE_PUBLISHED      = "published";
        public static final String ARTICLE_FAVORITE       = "favorite";

        public static Uri buildArticleUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static long getIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }
    }

}
