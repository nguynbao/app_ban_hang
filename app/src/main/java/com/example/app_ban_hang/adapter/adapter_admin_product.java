package com.example.app_ban_hang.adapter;

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
import com.example.app_ban_hang.database.ProductDao;

import java.util.List;

public class adapter_admin_product extends RecyclerView.Adapter<adapter_admin_product.ProductViewHolder> {
    List<product> productList;

    public adapter_admin_product(List<product> productList) {
        this.productList = productList;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView product_img;
        TextView product_name, product_price;
        Button btn_delte;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            product_img = itemView.findViewById(R.id.product_img);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            btn_delte = itemView.findViewById(R.id.btn_delte);

        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_allproduct, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        product product = productList.get(position);
        holder.product_img.setImageResource(product.getProduct_imgRes());
        holder.product_name.setText(product.getProduct_name());
        holder.product_price.setText(String.valueOf(product.getProduct_price()));
        holder.btn_delte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductDao productDao = new ProductDao(view.getContext());
                productDao.delete(String.valueOf(product.getProduct_id()));
                productList.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}
