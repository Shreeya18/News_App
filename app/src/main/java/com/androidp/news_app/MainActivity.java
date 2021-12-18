package com.androidp.news_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Category_RVAdapter.CategoryClickInterfacer {

    private RecyclerView news, category;
    private ProgressBar loading;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<Category_rvModal> categoryRvModalArrayList;
    private Category_RVAdapter category_rvAdapter;
    private News_RVAdapter news_rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        news = findViewById(R.id.rv_news);
        category = findViewById(R.id.rv_category);
        loading = findViewById(R.id.loading);

        articlesArrayList = new ArrayList<>();
        categoryRvModalArrayList = new ArrayList<>();
        news_rvAdapter = new News_RVAdapter(articlesArrayList, this);
        category_rvAdapter = new Category_RVAdapter(categoryRvModalArrayList,this,this::onCategoryClick);
        news.setLayoutManager(new LinearLayoutManager(this));
        news.setAdapter(news_rvAdapter);
        category.setAdapter(category_rvAdapter);

        getCategories();
        getNews("All");
        news_rvAdapter.notifyDataSetChanged();
    }


    private void getCategories(){
        categoryRvModalArrayList.add(new Category_rvModal("All", "https://images.unsplash.com/photo-1485827404703-89b55fcc595e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80"));
        categoryRvModalArrayList.add(new Category_rvModal("Technology", "https://images.unsplash.com/photo-1485827404703-89b55fcc595e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80"));
        categoryRvModalArrayList.add(new Category_rvModal("Science", "https://images.unsplash.com/photo-1451187580459-43490279c0fa?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTZ8fHNjaWVuY2V8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"));
        categoryRvModalArrayList.add(new Category_rvModal("Sports", "https://images.unsplash.com/photo-1517649763962-0c623066013b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=870&q=80"));
        categoryRvModalArrayList.add(new Category_rvModal("General", "https://images.unsplash.com/photo-1512314889357-e157c22f938d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8Z2VuZXJhbHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRvModalArrayList.add(new Category_rvModal("Business", "https://images.unsplash.com/photo-1591696205602-2f950c417cb9?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTl8fGJ1c2luZXNzfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRvModalArrayList.add(new Category_rvModal("Entertainment", "https://media.istockphoto.com/photos/red-carpet-entrance-picture-id1066484598?b=1&k=20&m=1066484598&s=170667a&w=0&h=a7f2EfxEAYx_C0pb3r0UTkwsvstFbG9UDEaMLqvy5KU="));
        categoryRvModalArrayList.add(new Category_rvModal("Health", "https://images.unsplash.com/photo-1505751172876-fa1923c5c528?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8aGVhbHRofGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));

    }

    private void getNews(String category){
        loading.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryUrl = "https://newsapi.org/v2/top-headlines?country=in&category="+ category +"&apiKey=f0445e4bed1d4acdbae997e27de6560a";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=f0445e4bed1d4acdbae997e27de6560a";
        String baseurl = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Retrofit_api retrofit_api = retrofit.create(Retrofit_api.class);
        Call<NewsModal> call;
        if(category.equals("All")){
            call = retrofit_api.getAllNews(url);

        }else {
            call = retrofit_api.getNewsByCategory(categoryUrl);
        }


        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal newsModal = response.body();
                loading.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModal.getArticles();
                for(int i=0;i<articles.size();i++){
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrlToImage()
                    ,articles.get(i).getUrl(),articles.get(i).getContent()));

                    news_rvAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {

            }
        });

    }
    @Override
    public void onCategoryClick(int position) {

        String category = categoryRvModalArrayList.get(position).getCategory();
        getNews(category);


    }
}