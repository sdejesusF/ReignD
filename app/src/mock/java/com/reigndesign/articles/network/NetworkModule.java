package com.reigndesign.articles.network;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sergiodejesus on 9/29/17.
 */

public class NetworkModule {
    private static Retrofit retrofit = null;
    private static final MediaType MEDIA_JSON = MediaType.parse("application/json");

    public static Retrofit getClient(String baseUrl, Context context) {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();

                            String path = request.url().encodedPath();

                            InputStream stream = context.getAssets().open(path.substring(1,path.length()));
                            String json = parseStream(stream);

                            Response response = new Response.Builder()
                                    .body(ResponseBody.create(MEDIA_JSON, json))
                                    .request(chain.request())
                                    .protocol(Protocol.HTTP_2)
                                    .message(json)
                                    .code(200)
                                    .build();
                            return response;
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
    private static String parseStream(InputStream stream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        String line;
        while ((line = in.readLine()) != null) {
            builder.append(line);
        }
        in.close();
        return builder.toString();
    }
}
