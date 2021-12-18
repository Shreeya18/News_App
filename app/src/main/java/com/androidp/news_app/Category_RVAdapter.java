package com.androidp.news_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Category_RVAdapter extends RecyclerView.Adapter<Category_RVAdapter.ViewHolder> {


    private ArrayList<Category_rvModal> category_rvModals;
    private Context context;
    private CategoryClickInterfacer categoryClickInterfacer;

    public Category_RVAdapter(ArrayList<Category_rvModal> category_rvModals, Context context, CategoryClickInterfacer categoryClickInterfacer) {
        this.category_rvModals = category_rvModals;
        this.context = context;
        this.categoryClickInterfacer = categoryClickInterfacer;
    }

    @NonNull
    @Override
    public Category_RVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories, parent, false);
        return new Category_RVAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Category_RVAdapter.ViewHolder holder, int position) {

        Category_rvModal category_rvModal = category_rvModals.get(position);
        holder.category.setText(category_rvModal.getCategory());
        Picasso.get().load(category_rvModal.getCategoryImageUrl()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                categoryClickInterfacer.onCategoryClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return category_rvModals.size();
    }

    public interface CategoryClickInterfacer{
        void onCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView category;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img);
            category = itemView.findViewById(R.id.cat);
        }
    }
}
