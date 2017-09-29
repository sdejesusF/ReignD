package com.reigndesign.articles.data;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.reigndesign.articles.data.source.local.ArticlesDBHelper;
import com.reigndesign.articles.data.source.local.ArticlesLocalDataSource;
import com.reigndesign.articles.injection.Scheduler;
import com.reigndesign.articles.ui.base.BaseScheduler;
import com.reigndesign.articles.util.ImmediateScheduler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by sergiodejesus on 9/29/17.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ArticlesLocalDataSourceTest {

    private BaseScheduler mSchedulerProvider;

    private ArticlesLocalDataSource mLocalDataSource;

    @Before
    public void setup() {
        ArticlesLocalDataSource.destroyInstance();
        mSchedulerProvider = new ImmediateScheduler();

        mLocalDataSource = ArticlesLocalDataSource.getInstance(
                new ArticlesDBHelper(InstrumentationRegistry.getContext()), mSchedulerProvider);
    }

    @After
    public void cleanUp() {
        mLocalDataSource.deleteAll();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(mLocalDataSource);
    }
}
