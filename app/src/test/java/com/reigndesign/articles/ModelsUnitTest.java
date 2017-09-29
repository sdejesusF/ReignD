package com.reigndesign.articles;

import com.google.gson.Gson;
import com.reigndesign.articles.data.model.Article;
import com.reigndesign.articles.network.ArticlesResponse;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ModelsUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void articleObjectTest(){
        Gson gson = new Gson();
        String json = "{\"objectID\": \"12344\", \"story_title\": \"TEST\", \"create_at\": 1506717692, \"url\": \"http://google.com\",\"author\": \"Sergio\"}";
        System.out.println(json);
        Article result = gson.fromJson(json, Article.class);
        assertTrue("Good", result.getTitle().equals("TEST"));
    }
    @Test
    public void articleObjectTestJustTitle(){
        Gson gson = new Gson();
        String json = "{\"objectID\": \"12344\", \"title\": \"TEST\", \"create_at\": 1506717692, \"url\": \"http://google.com\",\"author\": \"Sergio\"}";
        System.out.println(json);
        Article result = gson.fromJson(json, Article.class);
        assertTrue("Good", result.getTitle().equals("TEST"));
    }
    @Test
    public void serverResponseObjectTest(){
        Gson gson = new Gson();
        String json = "{\"hits\": [{\"objectID\": \"12344\", \"story_title\": \"TEST\", \"create_at\": 1506717692, \"url\": \"http://google.com\",\"author\": \"Sergio\"}]}";
        System.out.println(json);
        ArticlesResponse result = gson.fromJson(json, ArticlesResponse.class);
        assertTrue("Good", result.getData().size() == 1);
    }
}