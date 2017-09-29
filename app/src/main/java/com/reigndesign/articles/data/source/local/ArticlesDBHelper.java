package com.reigndesign.articles.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public class ArticlesDBHelper extends SQLiteOpenHelper {

    private static final String TAG = ArticlesDBHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "articles.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String REAL_TYPE = " REAL";

    private static final String TINY_INT_TYPE = " TINYINT";

    private static final String COMMA_SEP = ",";

    private static final String[] ENTRIES = new String[]{

            "CREATE TABLE " + ArticlesPersistenceContract.ArticlePersistenceEntry.TABLE_NAME + " (" +
                    ArticlesPersistenceContract.ArticlePersistenceEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
                    ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_AUTHOR + TEXT_TYPE + COMMA_SEP +
                    ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_IS_DISABLE + TINY_INT_TYPE + COMMA_SEP +
                    ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_CREATED_AT + INTEGER_TYPE +
                    " );",

    };

    public ArticlesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        for (String entry : ENTRIES) {
            db.execSQL(entry);
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
