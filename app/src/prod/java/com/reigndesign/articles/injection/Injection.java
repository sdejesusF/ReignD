package com.reigndesign.articles.injection;

import android.content.Context;
import android.support.annotation.NonNull;

import com.reigndesign.articles.BuildConfig;
import com.reigndesign.articles.data.ArticlesRepository;
import com.reigndesign.articles.data.source.local.ArticlesDBHelper;
import com.reigndesign.articles.data.source.local.ArticlesLocalDataSource;
import com.reigndesign.articles.data.source.remote.ArticlesRemoteDataSource;
import com.reigndesign.articles.network.NetworkModule;
import com.reigndesign.articles.network.NetworkServices;
import com.reigndesign.articles.ui.base.BaseScheduler;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public class Injection {
    public static NetworkServices provideNetwork(Context context){
        return NetworkModule.getClient(BuildConfig.BASE_URL).create(NetworkServices.class);
    }
    public static BaseScheduler provideScheduler() {
        return Scheduler.getInstance();
    }
    public static ArticlesDBHelper provideDBHelper(Context context){
        return new ArticlesDBHelper(context);
    }
    public static ArticlesRepository provideArticlesRepository(@NonNull Context context) {
        return ArticlesRepository.getInstance(
                ArticlesLocalDataSource.getInstance(context, provideScheduler()),
                ArticlesRemoteDataSource.getInstance(provideNetwork(context)));
    }
}
