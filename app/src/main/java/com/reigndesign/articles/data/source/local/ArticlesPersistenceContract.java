package com.reigndesign.articles.data.source.local;

import android.provider.BaseColumns;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public class ArticlesPersistenceContract {
    private ArticlesPersistenceContract() {
    }

    public static abstract class ArticlePersistenceEntry implements BaseColumns {
        public static final String TABLE_NAME = "article";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_IS_DISABLE = "is_disable";
        public static final String COLUMN_NAME_CREATED_AT = "created_at";
    }
}

