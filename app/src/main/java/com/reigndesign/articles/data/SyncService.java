package com.reigndesign.articles.data;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.util.Log;

import com.reigndesign.articles.BuildConfig;
import com.reigndesign.articles.data.model.Article;
import com.reigndesign.articles.injection.Injection;
import com.reigndesign.articles.ui.base.BaseScheduler;
import com.reigndesign.articles.util.AndroidComponentUtil;
import com.reigndesign.articles.util.NetworkUtils;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by sergiodejesus on 9/29/17.
 */

public class SyncService extends Service {

    private static final String TAG = SyncService.class.getSimpleName();
    private ArticlesRepository mRepository;
    private BaseScheduler mScheduler;
    private CompositeSubscription mSubscriptions;


    public static Intent getStartIntent(Context context) {
        return new Intent(context, SyncService.class);
    }

    public static boolean isRunning(Context context) {
        return AndroidComponentUtil.isServiceRunning(context, SyncService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRepository = Injection.provideArticlesRepository(this);
        mScheduler = Injection.provideScheduler();
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        Log.i(TAG, "Starting sync...");

        if (!NetworkUtils.isNetworkConnected(this) && BuildConfig.CHECK_INTERNET) {
            Log.i(TAG, "Sync canceled, connection not available");
            AndroidComponentUtil.toggleComponent(this, SyncOnConnectionAvailable.class, true);
            stopSelf(startId);
            return START_NOT_STICKY;
        }

        Subscription subscription =
                mRepository
                        .requireSync()
                        .subscribeOn(mScheduler.io())
                        .subscribe(new Subscriber<List<Article>>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.w(TAG, "Error syncing. " + e.toString());
                                stopSelf(startId);
                            }

                            @Override
                            public void onNext(List<Article> articles) {
                                Log.i(TAG, "Synced successfully! " + articles.size());
                                stopSelf(startId);
                            }
                        });
        mSubscriptions.add(subscription);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mSubscriptions != null) mSubscriptions.clear();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class SyncOnConnectionAvailable extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)
                    && NetworkUtils.isNetworkConnected(context)) {
                Log.i(TAG, "Connection is now available, triggering sync...");
                AndroidComponentUtil.toggleComponent(context, this.getClass(), false);
                context.startService(getStartIntent(context));
            }
        }
    }

}
