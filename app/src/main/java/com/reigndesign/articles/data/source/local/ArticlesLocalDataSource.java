package com.reigndesign.articles.data.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.reigndesign.articles.data.ArticlesDataSource;
import com.reigndesign.articles.data.model.Article;
import com.reigndesign.articles.ui.base.BaseScheduler;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public class ArticlesLocalDataSource implements ArticlesDataSource {

    @Nullable
    private static ArticlesLocalDataSource INSTANCE;

    @NonNull
    private final BriteDatabase mDatabaseHelper;

    private String[] ARTICLE_PROJECTION = {
            ArticlesPersistenceContract.ArticlePersistenceEntry._ID,
            ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_TITLE,
            ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_URL,
            ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_AUTHOR,
            ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_IS_DISABLE,
            ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_CREATED_AT
    };

    private ArticlesLocalDataSource(@NonNull Context context, @NonNull BaseScheduler scheduler) {
        checkNotNull(context, "context cannot be null");
        checkNotNull(scheduler, "scheduler cannot be null");
        ArticlesDBHelper database = new ArticlesDBHelper(context);
        SqlBrite sqlBrite = SqlBrite.create();
        mDatabaseHelper = sqlBrite.wrapDatabaseHelper(database, scheduler.io());
    }

    public static ArticlesLocalDataSource getInstance(@NonNull Context context, @NonNull BaseScheduler scheduler) {
        if (INSTANCE == null) {
            INSTANCE = new ArticlesLocalDataSource(context, scheduler);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<List<Article>> getArticles() {
        String sql = String.format(
                "SELECT %s FROM %s WHERE %s LIKE %s",
                TextUtils.join(",", ARTICLE_PROJECTION),
                ArticlesPersistenceContract.ArticlePersistenceEntry.TABLE_NAME,
                ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_IS_DISABLE,
                0);
        return mDatabaseHelper.createQuery(ArticlesPersistenceContract.ArticlePersistenceEntry.TABLE_NAME, sql)
                .mapToList(this::getArticle);
    }

    @Override
    public Observable<Article> getArticle(String id) {
        String sql = String.format(
                "SELECT %s FROM %s WHERE %s LIKE '%s'",
                TextUtils.join(",", ARTICLE_PROJECTION),
                ArticlesPersistenceContract.ArticlePersistenceEntry.TABLE_NAME,
                ArticlesPersistenceContract.ArticlePersistenceEntry._ID,
                id);
        return mDatabaseHelper.createQuery(ArticlesPersistenceContract.ArticlePersistenceEntry.TABLE_NAME, sql)
                .mapToOneOrDefault(this::getArticle, null)
                .first();
    }

    @Override
    public void addArticle(Article article) {
        save(article);
    }

    @Override
    public void addArticles(List<Article> articles) {
        for (Article article : articles) {
            save(article);
        }
    }

    @Override
    public Observable<List<Article>> requireSync() {
        return null;
    }

    @Override
    public void deleteArticle(Article article) {
        article.setDisable(true);
        ContentValues values = new ContentValues();
        values.put(ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_IS_DISABLE,
                article.getIsDisable());
        String selection = ArticlesPersistenceContract.ArticlePersistenceEntry._ID + " LIKE ?";
        String[] selectionArgs = {article.getId()};
        mDatabaseHelper
                .update(ArticlesPersistenceContract.ArticlePersistenceEntry.TABLE_NAME, values, selection, selectionArgs);
    }

    @NonNull
    private Article getArticle(@NonNull Cursor c) {
        String itemId = c.getString(c.getColumnIndexOrThrow(ArticlesPersistenceContract.ArticlePersistenceEntry._ID));
        String url = c.getString(c.getColumnIndexOrThrow(ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_URL));
        String title = c.getString(c.getColumnIndexOrThrow(ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_TITLE));
        String author = c.getString(c.getColumnIndexOrThrow(ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_AUTHOR));
        boolean isEnable = c.getInt(c.getColumnIndexOrThrow(ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_IS_DISABLE)) == 1;
        long createdAt = c.getLong(c.getColumnIndexOrThrow(ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_CREATED_AT));
        return new Article(itemId, title, createdAt, url, isEnable, author);
    }

    private void save(final Article article) {

        getArticle(article.getId())
                .first()
                .subscribe(article1 -> {
                    checkNotNull(article, "article cannot be null!");
                    ContentValues values = new ContentValues();
                    values.put(ArticlesPersistenceContract.ArticlePersistenceEntry._ID, article.getId());
                    values.put(ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_URL, article.getUrl());
                    values.put(ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_TITLE, article.getTitle());
                    values.put(ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_AUTHOR, article.getAuthor());
                    values.put(ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_IS_DISABLE,
                            article1 == null ? article.getIsDisable() : article1.getIsDisable());
                    values.put(ArticlesPersistenceContract.ArticlePersistenceEntry.COLUMN_NAME_CREATED_AT, article.getCreateAt());
                    mDatabaseHelper
                            .insert(ArticlesPersistenceContract.ArticlePersistenceEntry.TABLE_NAME, values, SQLiteDatabase.CONFLICT_REPLACE);
                });

    }

    @Override
    public void deleteAll() {
        mDatabaseHelper.delete(ArticlesPersistenceContract.ArticlePersistenceEntry.TABLE_NAME, null);
    }

}
