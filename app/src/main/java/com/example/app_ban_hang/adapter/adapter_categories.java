package com.example.app_ban_hang.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.Model.categories;
import com.example.app_ban_hang.R;

import java.util.List;

public class adapter_categories extends RecyclerView.Adapter<adapter_categories.ViewHolder> {
    private final List<categories> categoriesList;
    public adapter_categories(List<categories> categoriesList){
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.category.setText(categoriesList.get(position).getCategory_name());
        Log.d("adapter_categories", "Category Name: " + categoriesList.get(position).getCategory_name());

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView category;
        public ViewHolder(View view){
            super(view);
            category = view.findViewById(R.id.cate_name);
        }
    }
}
