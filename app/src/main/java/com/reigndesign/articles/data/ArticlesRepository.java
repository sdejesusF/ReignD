package com.reigndesign.articles.data;

import android.support.annotation.NonNull;

import com.reigndesign.articles.data.model.Article;
import com.reigndesign.articles.data.source.local.ArticlesLocalDataSource;
import com.reigndesign.articles.data.source.remote.ArticlesRemoteDataSource;

import java.util.List;

import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public class ArticlesRepository implements ArticlesDataSource {

    private static ArticlesRepository INSTANCE;
    private ArticlesLocalDataSource mLocalDataSource;
    private ArticlesRemoteDataSource mRemoteDataSource;

    private ArticlesRepository(
            @NonNull ArticlesLocalDataSource localDataSource,
            @NonNull ArticlesRemoteDataSource remoteDataSource) {
        mLocalDataSource = checkNotNull(localDataSource, "Local source cannot be null!");
        mRemoteDataSource = checkNotNull(remoteDataSource, "Remote source cannot be null!");
    }

    public static ArticlesRepository getInstance(
            @NonNull ArticlesLocalDataSource localDataSource,
            @NonNull ArticlesRemoteDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ArticlesRepository(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<List<Article>> getArticles() {
        return mLocalDataSource.getArticles()
                .flatMap((List<Article> articles) ->
                        Observable.from(articles)
                                .filter(article -> article.getTitle() != null)
                                .toList()
                );
    }

    @Override
    public Observable<Article> getArticle(String id) {
        return null;
    }

    @Override
    public void addArticle(Article article) {

    }

    @Override
    public void addArticles(List<Article> articles) {
        mLocalDataSource.addArticles(articles);
    }

    @Override
    public Observable<List<Article>> requireSync() {
        return mRemoteDataSource
                .getArticles()
                .doOnNext(articles -> {
                    mLocalDataSource.addArticles(articles);
                })
                .doOnCompleted(() -> {
                });

    }

    @Override
    public void deleteArticle(Article article) {
        mLocalDataSource.deleteArticle(article);
    }

    @Override
    public void deleteAll() {

    }
}
