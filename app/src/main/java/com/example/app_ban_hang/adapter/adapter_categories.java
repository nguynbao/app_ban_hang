package com.example.app_ban_hang.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.R;

import java.util.List;

public class adapter_categories extends RecyclerView.Adapter<adapter_categories.ViewHolder> {
    private List<Integer> categoriesList;
    public adapter_categories(List<Integer> categoriesList){
        this.categoriesList = categoriesList;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView category;
        public ViewHolder(View view){
            super(view);
            category = view.findViewById(R.id.category);
        }
    }
}
