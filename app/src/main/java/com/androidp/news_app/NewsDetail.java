package com.androidp.news_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URI;

public class NewsDetail extends AppCompatActivity {

    String title;
    String desc, content, url, imageurl;


    private ImageView ivnews;
    private TextView  headingtv, desctv, contentTV;
    private Button fullnews;
    private Button sharebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        content = getIntent().getStringExtra("content");
        url = getIntent().getStringExtra("url");
        imageurl = getIntent().getStringExtra("image");


        ivnews = findViewById(R.id.ivnews);
        desctv = findViewById(R.id.desctv);
        headingtv = findViewById(R.id.headingtv);
        contentTV = findViewById(R.id.contentTV);
        fullnews = findViewById(R.id.fullnews);

        headingtv.setText(title);
        desctv.setText(desc);
        contentTV.setText(content);

        Picasso.get().load(imageurl).into(ivnews);
        fullnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });


        sharebtn = findViewById(R.id.sharebtn);
        
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/Plain");
                intent.putExtra(Intent.EXTRA_TEXT, url);
                startActivity(Intent.createChooser(intent, "Share to"));
            }
        });

    }


}