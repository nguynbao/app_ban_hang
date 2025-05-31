package com.example.app_ban_hang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.R;

import java.util.List;

public class adapter_product extends RecyclerView.Adapter<adapter_product.ViewHolder>{
    private List<Integer> productList;

    public adapter_product(List<Integer> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public adapter_product.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_product.ViewHolder holder, int position) {
        holder.product.setImageResource(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView product;
        public ViewHolder(View view){
            super(view);
            product = view.findViewById(R.id.product);
        }
    }
}
