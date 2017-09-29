package com.reigndesign.articles.ui.main;

import android.support.annotation.NonNull;

import com.reigndesign.articles.data.ArticlesRepository;
import com.reigndesign.articles.data.model.Article;
import com.reigndesign.articles.ui.base.BaseScheduler;

import java.util.Collections;
import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public class MainPresenter implements MainContract.Presenter {

    private static final String TAG = MainPresenter.class.getSimpleName();
    @NonNull
    private MainContract.View mView;

    @NonNull
    private ArticlesRepository mRepository;

    @NonNull
    private CompositeSubscription mSubscriptions;

    @NonNull
    private BaseScheduler mSchedulerProvider;


    public MainPresenter(
            MainContract.View view,
            ArticlesRepository repository,
            BaseScheduler schedulerProvider) {
        mView = checkNotNull(view, "View cannot be null");
        mRepository = checkNotNull(repository, "Repository cannot be null!");
        mSchedulerProvider = checkNotNull(schedulerProvider, "Scheduler cannot be null!");
        mSubscriptions = new CompositeSubscription();
        mView.setPresenter(this);
    }
    @Override
    public void subscribe() {
        mView.showLoading(true);
        mSubscriptions.clear();
        Subscription subscription = mRepository
                .getArticles()
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(
                        this::processArticles,
                        error -> {
                            mView.showLoading(false);
                            mView.showError();
                        },
                        () -> {
                            mView.showLoading(false);
                        }
                );
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
    private void processArticles(List<Article> articles) {
        mView.showLoading(false);
        Collections.sort(articles, (article, t1) -> article.getCreateAt() > t1.getCreateAt() ? -1 : article.getCreateAt() == t1.getCreateAt() ? 0 : 1);
        if (articles.isEmpty()) {
            mView.showNoArticles();
        } else {
            mView.showArticles(articles);
        }
    }

    @Override
    public void delete(Article article) {
        mRepository.deleteArticle(article);
    }

    @Override
    public void clicked(Article article) {
        if(article != null && article.getUrl() != null)
            mView.showDetail(article);
        else
            mView.showURLNotFound();
    }
}
