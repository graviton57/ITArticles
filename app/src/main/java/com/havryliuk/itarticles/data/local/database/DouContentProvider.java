package com.havryliuk.itarticles.data.local.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.havryliuk.itarticles.data.local.database.DouContract.ArticlesEntry;

/**
 * Created by Igor Havrylyuk on 21.10.2017.
 */
public class DouContentProvider extends ContentProvider {
    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private DouDbHelper openHelper;

    static final int ARTICLES = 1000;
    static final int ARTICLE_WITH_ID = 1001;

    private static final SQLiteQueryBuilder sArticleByIdQueryBuilder;

    static {
        sArticleByIdQueryBuilder = new SQLiteQueryBuilder();
        sArticleByIdQueryBuilder.setTables(ArticlesEntry.TABLE_NAME);
    }

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DouContract.CONTENT_AUTHORITY;
        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, DouContract.PATH_ARTICLES, ARTICLES);
        matcher.addURI(authority, DouContract.PATH_ARTICLES +"/#", ARTICLE_WITH_ID);
        return matcher;
    }

    private static final String articleByIdSelection =
            ArticlesEntry.TABLE_NAME + "." + ArticlesEntry._ID + " = ? ";


    private Cursor geArticleById(@NonNull Uri uri, String[] projection, String sortOrder) {
        String selectionArticleId = String.valueOf(ArticlesEntry.getIdFromUri(uri));
        String selection = articleByIdSelection;
        String[] selectionArgs = new String[]{selectionArticleId};
        return sArticleByIdQueryBuilder.query(openHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    public DouContentProvider() {
    }

    @Override
    public boolean onCreate() {
        openHelper = new DouDbHelper(getContext());
        return true;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = openHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
            case ARTICLES:
                rowsDeleted = db.delete(
                        ArticlesEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ARTICLES:
                return ArticlesEntry.CONTENT_TYPE;
            case ARTICLE_WITH_ID:
                return ArticlesEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = openHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case ARTICLES: {
                long _id = db.insert(ArticlesEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = ArticlesEntry.buildArticleUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case ARTICLES: {
                retCursor = openHelper.getReadableDatabase().query(
                        ArticlesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case ARTICLE_WITH_ID: {
                retCursor = geArticleById(uri, projection, sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        final SQLiteDatabase db = openHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;
        switch (match) {
            case ARTICLES:
                rowsUpdated = db.update(ArticlesEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = openHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount = 0;
        switch (match) {
            case ARTICLES:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(ArticlesEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
