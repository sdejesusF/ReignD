package com.reigndesign.articles.data;

import com.reigndesign.articles.data.model.Article;

import java.util.List;

import rx.Observable;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public interface ArticlesDataSource {

    Observable<List<Article>> getArticles();

    Observable<Article> getArticle(String id);

    void addArticle(Article article);

    void addArticles(List<Article> articles);

    Observable<List<Article>> requireSync();

    void deleteArticle(Article article);

    void deleteAll();
}
