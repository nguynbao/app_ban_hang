package com.example.app_ban_hang.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_ban_hang.pages.page_detail_activity;
import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;

import java.util.List;

public class adapter_product extends RecyclerView.Adapter<adapter_product.ViewHolder>{
    private List<product> productList;
    public adapter_product(List<product> productList) {
        this.productList = productList;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        public ViewHolder(View view){
            super(view);
            productImage = view.findViewById(R.id.product);
            productName = view.findViewById(R.id.product_namee);
            productPrice = view.findViewById(R.id.product_pricee);

        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        product currentProduct = productList.get(position);
        Uri imgPath = currentProduct.getProduct_imgRes();
        Glide.with(holder.productImage.getContext())
                .load(imgPath)  // Glide hỗ trợ String path hoặc Uri object
//                .placeholder(R.drawable.default_image) // Ảnh hiện khi đang load
//                .error(R.drawable.default_image)       // Ảnh hiện khi load lỗi
                .into(holder.productImage);
        holder.productName.setText(currentProduct.getProduct_name());
        holder.productPrice.setText(String.valueOf(currentProduct.getProduct_price()));
        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, page_detail_activity.class);
            intent.putExtra("product_name", currentProduct.getProduct_name());
            intent.putExtra("product_price", currentProduct.getProduct_price());
            intent.putExtra("product_imgRes", currentProduct.getProduct_imgRes());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

}
