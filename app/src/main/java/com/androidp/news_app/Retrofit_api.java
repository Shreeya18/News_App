package com.androidp.news_app;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Retrofit_api {
    @GET
    Call<NewsModal> getAllNews (@Url String url);

    @GET
    Call<NewsModal> getNewsByCategory(@Url String url);

}
