package com.androidp.news_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class News_RVAdapter extends RecyclerView.Adapter<News_RVAdapter.ViewHolder> {


    private ArrayList<Articles> articlesArrayList;
    private Context context;

    public News_RVAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public News_RVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_rvi,parent, false);
        return new News_RVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull News_RVAdapter.ViewHolder holder, int position) {

        Articles articles = articlesArrayList.get(position);
        holder.subTitle.setText(articles.getDescription());
        holder.title.setText(articles.getTitle());
        Picasso.get().load(articles.getUrlToImage()).into(holder.news);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, NewsDetail.class);
                i.putExtra("title",articles.getTitle());
                i.putExtra("content", articles.getContent());
                i.putExtra("desc", articles.getDescription());
                i.putExtra("image", articles.getUrlToImage());
                i.putExtra("url", articles.getUrl());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, subTitle;
        private ImageView news;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.heading);
            subTitle = itemView.findViewById(R.id.subheading);
            news = itemView.findViewById(R.id.news_iv);
        }
    }
}
