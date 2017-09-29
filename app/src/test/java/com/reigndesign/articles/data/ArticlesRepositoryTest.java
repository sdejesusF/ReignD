package com.reigndesign.articles.data;

import android.content.Context;

import com.google.common.collect.Lists;
import com.reigndesign.articles.data.model.Article;
import com.reigndesign.articles.data.source.local.ArticlesLocalDataSource;
import com.reigndesign.articles.data.source.remote.ArticlesRemoteDataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import rx.Observable;
import rx.observers.TestSubscriber;
import static org.mockito.Mockito.when;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public class ArticlesRepositoryTest {
    private final static String ARTICLE_ID = "123";

    private final static String ARTICLE_ID2 = "1234";

    private final static String ARTICLE_ID3 = "12345";

    private static List<Article> ARTICLES =
            Lists.newArrayList(
                    new Article(
                            "ARTICLE_ID",
                            "Title1",
                            1506717692,
                            "https://google.com",
                            false,
                            "Sergio"
                    ),
                    new Article(
                            "ARTICLE_ID",
                            "Title1",
                            1506717691,
                            "https://google2.com",
                            false,
                            "Sergio2"
                    ),
                    new Article(
                            "ARTICLE_ID",
                            "Title1",
                            1506717690,
                            "https://google3.com",
                            false,
                            "Sergio3"
                    ));

    private ArticlesRepository mRepository;

    private TestSubscriber<List<Article>> mArticlesTestSubscriber;

    @Mock
    private ArticlesLocalDataSource mArticlesLocalDataSource;

    @Mock
    private ArticlesRemoteDataSource mArticlesRemoteDataSource;

    @Mock
    private Context mContext;

    @Before
    public void setupArticlesRepository() {
        MockitoAnnotations.initMocks(this);

        mRepository = ArticlesRepository.getInstance(
                mArticlesLocalDataSource, mArticlesRemoteDataSource);

        mArticlesTestSubscriber = new TestSubscriber<>();
    }
    @After
    public void destroyRepositoryInstance() {
        ArticlesRepository.destroyInstance();
    }

    @Test
    public void getArticles_repository_whenArticles() {

        setArticlesAvailable(mArticlesLocalDataSource, ARTICLES);
        setArticlesAvailable(mArticlesRemoteDataSource, ARTICLES);

        TestSubscriber<List<Article>> testSubscriber1 = new TestSubscriber<>();
        mRepository.getArticles().subscribe(testSubscriber1);

        TestSubscriber<List<Article>> testSubscriber2 = new TestSubscriber<>();
        mRepository.getArticles().subscribe(testSubscriber2);

        testSubscriber1.assertValue(ARTICLES);
        testSubscriber2.assertValue(ARTICLES);
    }

    @Test
    public void getArticles_repository_whenNoArticles() {

        setArticlesNotAvailable(mArticlesLocalDataSource);
        setArticlesNotAvailable(mArticlesRemoteDataSource);

        TestSubscriber<List<Article>> testSubscriber1 = new TestSubscriber<>();
        mRepository.getArticles().subscribe(testSubscriber1);

        TestSubscriber<List<Article>> testSubscriber2 = new TestSubscriber<>();
        mRepository.getArticles().subscribe(testSubscriber2);

        testSubscriber1.assertValue(Collections.emptyList());
        testSubscriber2.assertValue(Collections.emptyList());
    }

    @Test
    public void getArticles_repository_noDataInRemoteButLocal() {

        setArticlesAvailable(mArticlesLocalDataSource, ARTICLES);
        setArticlesNotAvailable(mArticlesRemoteDataSource);

        TestSubscriber<List<Article>> testSubscriber1 = new TestSubscriber<>();
        mRepository.getArticles().subscribe(testSubscriber1);

        TestSubscriber<List<Article>> testSubscriber2 = new TestSubscriber<>();
        mRepository.getArticles().subscribe(testSubscriber2);

        testSubscriber1.assertValue(ARTICLES);
        testSubscriber2.assertValue(ARTICLES);
    }

    private void setArticlesNotAvailable(ArticlesDataSource dataSource) {
        when(dataSource.getArticles()).thenReturn(Observable.just(Collections.emptyList()));
    }

    private void setArticlesAvailable(ArticlesDataSource dataSource, List<Article> articles) {
        when(dataSource.getArticles()).thenReturn(Observable.just(articles));
    }
}
