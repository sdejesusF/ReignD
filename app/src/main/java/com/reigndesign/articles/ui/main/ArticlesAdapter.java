package com.reigndesign.articles.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.reigndesign.articles.R;
import com.reigndesign.articles.data.model.Article;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.reigndesign.articles.util.TimeUtils.getFormattedDate;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleHolder> {

    private ArticleItemListener mItemListener;
    private List<Article> mItems;
    private int mLayout;
    private Context mContext;

    public ArticlesAdapter(List<Article> items, ArticleItemListener itemListener, Context context, int layout) {
        setList(items);
        mItemListener = itemListener;
        mLayout = layout;
        mContext = context;
    }

    public ArticlesAdapter(List<Article> items, ArticleItemListener itemListener, Context context) {
        this(items, itemListener, context, R.layout.item_article);
    }

    public void replaceData(List<Article> articles) {
        setList(articles);
        notifyDataSetChanged();
    }

    private void setList(List<Article> articles) {
        mItems = checkNotNull(articles);
    }

    @Override
    public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ArticleHolder vh;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(mLayout, parent, false);
        vh = new ArticleHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ArticleHolder holder, int position) {
        ArticleHolder articlesHolder = (ArticleHolder) holder;
        Article article = mItems.get(position);
        articlesHolder.mTitle.setText(article.getTitle());
        articlesHolder.mAuthorDate.setText(
                String.format(mContext.getString(R.string.article_author_date), article.getAuthor(), getFormattedDate(article.getCreateAt())));
        articlesHolder.bind(article);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public interface ArticleItemListener {
        void onClick(Article article);
        void onDelete(Article article);
    }

    public class ArticleHolder extends RecyclerView.ViewHolder {

        private TextView mTitle;
        private SwipeRevealLayout mSwipeLayout;
        private TextView mAuthorDate;
        private View mDeleteLayout;
        private View mMainLayout;

        public ArticleHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.title);
            mSwipeLayout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe);
            mAuthorDate = (TextView) itemView.findViewById(R.id.subtitle);
            mDeleteLayout = (View) itemView.findViewById(R.id.delete);
            mMainLayout = (View) itemView.findViewById(R.id.main);
        }
        public void bind(Article article) {
            mDeleteLayout.setOnClickListener((View v) -> {
                mSwipeLayout.close(true);
                mItemListener.onDelete(article);
            });
            mMainLayout.setOnClickListener((View v) -> {
                mItemListener.onClick(article);
            });

        }
    }
}
