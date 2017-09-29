package com.reigndesign.articles.ui;

import com.google.common.collect.Lists;
import com.reigndesign.articles.data.ArticlesRepository;
import com.reigndesign.articles.data.model.Article;
import com.reigndesign.articles.ui.articledetail.DetailContract;
import com.reigndesign.articles.ui.articledetail.DetailPresenter;
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

public class DetailPresenterTest {

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

    @Mock
    private DetailContract.View mView;

    private BaseScheduler mSchedulerProvider;

    private DetailPresenter mPresenter;

    @Before
    public void setupArticlesPresenter() {

        MockitoAnnotations.initMocks(this);

        mSchedulerProvider = new ImmediateScheduler();

        mPresenter = new DetailPresenter(mView, ARTICLES.get(0));

    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        mPresenter = new DetailPresenter(mView, ARTICLES.get(0));

        verify(mView).setPresenter(mPresenter);
    }

    @Test
    public void loadAllArticlesIntoView() {
        mPresenter.subscribe();

        verify(mView).setTitle(ARTICLES.get(0).getTitle());
        verify(mView).loadWebView(ARTICLES.get(0).getUrl());
    }
}
