package com.havryliuk.itarticles.data.local.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.havryliuk.itarticles.data.local.database.DouContract.ArticlesEntry;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */

public class DouDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dou_articles.db";

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;

    private Context context;

    private final String SQL_CREATE_ARTICLES_TABLE = "CREATE TABLE " +
            ArticlesEntry.TABLE_NAME + " (" +
            ArticlesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            ArticlesEntry.ARTICLE_ID + " INTEGER NOT NULL , " +
            ArticlesEntry.ARTICLE_TITLE + " TEXT NOT NULL DEFAULT '', " +
            ArticlesEntry.ARTICLE_URL + " TEXT NOT NULL DEFAULT '', " +
            ArticlesEntry.ARTICLE_CATEGORY + " TEXT NOT NULL DEFAULT '', " +
            ArticlesEntry.ARTICLE_ANNOUNCEMENT + " TEXT NOT NULL DEFAULT '', " +
            ArticlesEntry.ARTICLE_TAGS + " TEXT NOT NULL DEFAULT '', " +
            ArticlesEntry.ARTICLE_PAGEVIEWS + " INTEGER NOT NULL DEFAULT 0, " +
            ArticlesEntry.ARTICLE_COMMENTS + " INTEGER NOT NULL DEFAULT 0, " +
            ArticlesEntry.ARTICLE_IMAGE + " TEXT NOT NULL DEFAULT '', " +
            ArticlesEntry.ARTICLE_AUTHOR_NAME + " TEXT NOT NULL DEFAULT '', " +
            ArticlesEntry.ARTICLE_AUTHOR_URL + " TEXT NOT NULL DEFAULT '', " +
            ArticlesEntry.ARTICLE_PUBLISHED + " TEXT NOT NULL DEFAULT '', " +
            ArticlesEntry.ARTICLE_FAVORITE + " INTEGER NOT NULL DEFAULT 0, " +
            " UNIQUE (" + ArticlesEntry.ARTICLE_ID + ") ON CONFLICT IGNORE);"; //save favorites articles

    public DouDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ARTICLES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String deleteTable = "DROP TABLE IF EXISTS ";
        db.execSQL(deleteTable + ArticlesEntry.TABLE_NAME);
        onCreate(db);
    }

    public Context getContext() {
        return context;
    }
}
