package com.reigndesign.articles.ui;

import com.google.common.collect.Lists;
import com.reigndesign.articles.data.ArticlesRepository;
import com.reigndesign.articles.data.model.Article;
import com.reigndesign.articles.ui.base.BaseScheduler;
import com.reigndesign.articles.ui.main.MainContract;
import com.reigndesign.articles.ui.main.MainPresenter;
import com.reigndesign.articles.util.ImmediateScheduler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import rx.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public class MainPresenterTest {
    private static List<Article> ARTICLES;

    @Mock
    private ArticlesRepository mArticlesRepository;

    @Mock
    private MainContract.View mArticlesView;

    private BaseScheduler mSchedulerProvider;

    private MainPresenter mPresenter;

    @Before
    public void setupArticlesPresenter() {

        MockitoAnnotations.initMocks(this);

        mSchedulerProvider = new ImmediateScheduler();

        mPresenter = new MainPresenter(mArticlesView, mArticlesRepository, mSchedulerProvider);

        ARTICLES = Lists.newArrayList(Lists.newArrayList(
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
                )));
    }
    @Test
    public void createPresenter_setsThePresenterToView() {
        mPresenter = new MainPresenter(mArticlesView, mArticlesRepository, mSchedulerProvider);

        verify(mArticlesView).setPresenter(mPresenter);
    }
    @Test
    public void loadAllArticlesIntoView() {
        when(mArticlesRepository.getArticles()).thenReturn(Observable.just(ARTICLES));
        mPresenter.subscribe();

        verify(mArticlesView).showArticles(ARTICLES);
    }
    @Test
    public void clickArticle_ShowsArticleUi() {
        mPresenter.clicked(ARTICLES.get(0));

        verify(mArticlesView).showDetail(ARTICLES.get(0));
    }
}
