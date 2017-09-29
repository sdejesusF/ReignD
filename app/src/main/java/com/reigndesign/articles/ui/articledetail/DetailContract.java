package com.reigndesign.articles.ui.articledetail;

import com.reigndesign.articles.ui.base.BasePresenter;
import com.reigndesign.articles.ui.base.BaseView;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public class DetailContract {
    public interface View extends BaseView<Presenter>{
        void loadWebView(String url);
        void setTitle(String title);
    }
    public interface Presenter extends BasePresenter{}
}
