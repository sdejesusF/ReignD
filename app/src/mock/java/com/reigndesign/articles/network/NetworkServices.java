package com.reigndesign.articles.network;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public interface NetworkServices {
    @GET("hits.json")
    Observable<ArticlesResponse> getArticlesList();
}
