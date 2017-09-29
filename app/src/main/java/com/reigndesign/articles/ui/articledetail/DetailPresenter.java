package com.reigndesign.articles.ui.articledetail;

import android.support.annotation.NonNull;
import android.util.Log;

import com.reigndesign.articles.data.model.Article;
import com.reigndesign.articles.ui.base.BaseScheduler;
import com.reigndesign.articles.ui.main.MainPresenter;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public class DetailPresenter implements DetailContract.Presenter{

    private static final String TAG = MainPresenter.class.getSimpleName();
    @NonNull
    private DetailContract.View mView;

    @NonNull
    private Article mArticle;

    public DetailPresenter(
            DetailContract.View view,
            Article article) {
        mView = checkNotNull(view, "view cannot be null");
        mArticle = checkNotNull(article, "article cannot be null!");
        mView.setPresenter(this);
    }
    @Override
    public void subscribe() {
        mView.loadWebView(mArticle.getUrl());
        mView.setTitle(mArticle.getTitle());
    }

    @Override
    public void unSubscribe() {

    }
}
