package com.reigndesign.articles.util;

import android.text.format.DateUtils;

import java.util.Date;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public class TimeUtils {

    public static String getFormattedDate(long time){
        return DateUtils.getRelativeTimeSpanString(time * 1000,
                new Date().getTime(), DateUtils.SECOND_IN_MILLIS).toString();
    }
}
