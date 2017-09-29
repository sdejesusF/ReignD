package com.reigndesign.articles.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.reigndesign.articles.data.ArticlesDataSource;
import com.reigndesign.articles.data.model.Article;
import com.reigndesign.articles.network.ArticlesResponse;
import com.reigndesign.articles.network.NetworkServices;

import java.util.List;

import rx.Observable;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public class ArticlesRemoteDataSource implements ArticlesDataSource {

    @Nullable
    private static ArticlesRemoteDataSource INSTANCE;

    @NonNull
    private static NetworkServices mNetworkServices;

    private ArticlesRemoteDataSource(@NonNull NetworkServices networkServices) {
        mNetworkServices = networkServices;
    }

    public static ArticlesRemoteDataSource getInstance(@NonNull NetworkServices networkServices) {
        if (INSTANCE == null) {
            INSTANCE = new ArticlesRemoteDataSource(networkServices);
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<Article>> getArticles() {
        return getFromServer()
                .flatMap(
                        (ArticlesResponse response) ->
                                Observable.from(response.getData()).toList());
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

    }

    @Override
    public Observable<List<Article>> requireSync() {
        return null;
    }

    @Override
    public void deleteArticle(Article article) {

    }

    @Override
    public void deleteAll() {

    }

    public Observable<ArticlesResponse> getFromServer() {
        return mNetworkServices.getArticlesList();
    }

}
