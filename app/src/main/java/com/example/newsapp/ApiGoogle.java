package com.example.newsapp;

import com.example.newsapp.DataModel.NewsData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiGoogle {
    @GET("top-headlines?sources=google-news&apiKey=9f9aae0ba8744956a6246ad241aa56aa")
    Call<NewsData> getNewsData();
}
