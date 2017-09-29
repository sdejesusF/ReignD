package com.reigndesign.articles.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.reigndesign.articles.R;
import com.reigndesign.articles.data.SyncService;
import com.reigndesign.articles.data.model.Article;
import com.reigndesign.articles.injection.Injection;
import com.reigndesign.articles.ui.articledetail.DetailActivity;
import com.reigndesign.articles.ui.base.BaseActivity;
import com.reigndesign.articles.util.DialogFactory;
import com.reigndesign.articles.util.ui.ScrollChildSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public class MainActivity extends BaseActivity implements MainContract.View, ArticlesAdapter.ArticleItemListener{
    MainContract.Presenter mPresenter;

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "EXTRA_TRIGGER_SYNC_FLAG";

    private ArticlesAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ScrollChildSwipeRefreshLayout mRefresh;
    private LinearLayout mNoDataAvailable;
    private Paint p = new Paint();

    public static Intent getStartIntent(Context context, boolean triggerDataSyncOnCreate) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_TRIGGER_SYNC_FLAG, triggerDataSyncOnCreate);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRefresh = (ScrollChildSwipeRefreshLayout) findViewById(R.id.list_loading);
        mNoDataAvailable = (LinearLayout) findViewById(R.id.list_error);

        mAdapter = new ArticlesAdapter(new ArrayList<>(), this, this);
        LinearLayoutManager layoutGrid = new LinearLayoutManager(this);
        layoutGrid.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(layoutGrid);
        mRefresh.setScrollUpChild(mRecyclerView);
        mRefresh.setOnRefreshListener(() -> startService(SyncService.getStartIntent(this)));
        new MainPresenter(this, Injection.provideArticlesRepository(this), Injection.provideScheduler());

        if (getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
            startService(SyncService.getStartIntent(this));
        }
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
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

    @Override
    public void showArticles(List<Article> articleList) {
        mRecyclerView.setVisibility(View.VISIBLE);
        mNoDataAvailable.setVisibility(View.GONE);
        mAdapter.replaceData(articleList);
    }

    @Override
    public void showNoArticles() {
        mRecyclerView.setVisibility(View.GONE);
        mNoDataAvailable.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading))
                .show();
    }

    @Override
    public void showLoading(boolean show) {
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.list_loading);
        swipeRefreshLayout.post(() -> swipeRefreshLayout.setRefreshing(show));
    }

    @Override
    public void showDetail(Article article) {
        startActivity(DetailActivity.getStartIntent(this, article));
    }

    @Override
    public void showURLNotFound() {
        Toast.makeText(getApplicationContext(), getString(R.string.error_url_not_found), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(Article article) {
        mPresenter.clicked(article);
    }

    @Override
    public void onDelete(Article article) {
        mPresenter.delete(article);
    }
}
