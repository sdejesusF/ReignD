package com.reigndesign.articles.ui.main;

import com.reigndesign.articles.data.model.Article;
import com.reigndesign.articles.ui.base.BasePresenter;
import com.reigndesign.articles.ui.base.BaseView;

import java.util.List;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public class MainContract {
    public interface View extends BaseView<Presenter>{
        void showArticles(List<Article> articleList);
        void showNoArticles();
        void showError();
        void showLoading(boolean show);
        void showDetail(Article article);
        void showURLNotFound();

    }

    public interface Presenter extends BasePresenter{
        void delete(Article article);
        void clicked(Article article);
    }
}
