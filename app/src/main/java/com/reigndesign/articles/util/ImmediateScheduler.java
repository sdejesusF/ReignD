package com.reigndesign.articles.util;

import android.support.annotation.NonNull;

import com.reigndesign.articles.ui.base.BaseScheduler;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public class ImmediateScheduler implements BaseScheduler {
    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.trampoline();
    }

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }
}
