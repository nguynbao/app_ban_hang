package com.example.app_ban_hang.adapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.pages.page_detail_activity;

import java.util.List;

public class adapter_product_overview extends RecyclerView.Adapter<adapter_product_overview.productOverViewViewHolder> {
    private List<product> productList;

    public adapter_product_overview(List<product> productList) {
        this.productList = productList;
    }

    public class productOverViewViewHolder extends RecyclerView.ViewHolder {
        ImageView product_img;
        TextView product_name, product_price;
        Button btn_Detail;
        public productOverViewViewHolder(@NonNull View itemView) {
            super(itemView);
            product_img = itemView.findViewById(R.id.product_img);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            btn_Detail = itemView.findViewById(R.id.btn_Detail);

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
        product currentProduct  = productList.get(position);
        String imgRes = currentProduct.getProduct_imgRes();
        if (imgRes != null && !imgRes.isEmpty()) {
            holder.product_img.setImageURI(Uri.parse(imgRes));}
        holder.product_name.setText(currentProduct.getProduct_name());
        holder.product_price.setText(String.format("%,d", (int)currentProduct.getProduct_price()));
        holder.btn_Detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), page_detail_activity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("intID", currentProduct.getProduct_id());
                intent.putExtras(bundle);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}
