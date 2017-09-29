package com.reigndesign.articles.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.reigndesign.articles.data.model.Article;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

/**
 * Created by sergiodejesus on 9/29/17.
 */

@Generated("org.jsonschema2pojo")
public class ArticlesResponse {

    @SerializedName("hits")
    @Expose
    private List<Article> data = new ArrayList<Article>();

    public List<Article> getData() {
        return data;
    }

    public void setData(List<Article> data) {
        this.data = data;
    }

}