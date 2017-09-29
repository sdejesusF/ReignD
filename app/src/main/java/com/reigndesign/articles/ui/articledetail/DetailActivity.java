package com.reigndesign.articles.ui.articledetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.reigndesign.articles.R;
import com.reigndesign.articles.data.model.Article;
import com.reigndesign.articles.ui.main.MainActivity;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    private static final String EXTRA_ARTICLE = "EXTRA_ARTICLE";
    private DetailContract.Presenter mPresenter;
    private WebView mWebView;
    public static Intent getStartIntent(Context context, Article article) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_ARTICLE, article);
        return intent;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mWebView = findViewById(R.id.webview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Article article = getIntent().getParcelableExtra(EXTRA_ARTICLE);
        if(article == null){
            finish();
        }
        new DetailPresenter(this, article);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void loadWebView(String url) {
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new webClient());
    }

    @Override
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }
    class webClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }
    }
}
