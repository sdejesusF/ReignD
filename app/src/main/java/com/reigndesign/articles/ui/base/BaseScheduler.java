package com.reigndesign.articles.ui.base;

import android.support.annotation.NonNull;

import rx.Scheduler;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public interface BaseScheduler {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}