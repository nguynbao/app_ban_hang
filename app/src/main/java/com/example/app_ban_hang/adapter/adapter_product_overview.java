package com.example.app_ban_hang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;

import java.sql.Array;
import java.util.List;
import java.util.zip.Inflater;

public class adapter_product_overview extends RecyclerView.Adapter<adapter_product_overview.productOverViewViewHolder> {
    private List<product> productList;

    public adapter_product_overview(List<product> productList) {
        this.productList = productList;
    }

    public class productOverViewViewHolder extends RecyclerView.ViewHolder {
        ImageView product_img;
        TextView product_name, product_price;
        public productOverViewViewHolder(@NonNull View itemView) {
            super(itemView);
            product_img = itemView.findViewById(R.id.product_img);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);

        }
    }


    @NonNull
    @Override
    public productOverViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_overview, parent, false);
        return new productOverViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull productOverViewViewHolder holder, int position) {
        product product = productList.get(position);
        holder.product_img.setImageResource(product.getProduct_imgRes());
        holder.product_name.setText(product.getProduct_name());
        holder.product_price.setText(String.valueOf(product.getProduct_price()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}
