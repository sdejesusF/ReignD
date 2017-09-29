package com.reigndesign.articles.network;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public interface NetworkServices {
    @GET("search_by_date?query=android")
    Observable<ArticlesResponse> getArticlesList();
}

