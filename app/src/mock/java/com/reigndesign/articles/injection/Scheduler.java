package com.reigndesign.articles.injection;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.reigndesign.articles.ui.base.BaseScheduler;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public class Scheduler implements BaseScheduler {

    @Nullable
    private static Scheduler INSTANCE;

    private Scheduler() {
    }

    public static synchronized Scheduler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Scheduler();
        }
        return INSTANCE;
    }

    @Override
    @NonNull
    public rx.Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    @NonNull
    public rx.Scheduler io() {
        return Schedulers.io();
    }

    @Override
    @NonNull
    public rx.Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}