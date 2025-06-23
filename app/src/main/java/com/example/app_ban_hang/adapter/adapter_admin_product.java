package com.example.app_ban_hang.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.ProductDao;
import com.example.app_ban_hang.pages_admin.page_admin_Edit_Product;

import java.util.List;

public class adapter_admin_product extends RecyclerView.Adapter<adapter_admin_product.ProductViewHolder> {
    List<product> productList;

    public adapter_admin_product(List<product> productList) {
        this.productList = productList;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView product_img;
        TextView product_name, product_price;
        Button btn_delte, btn_edit;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            product_img = itemView.findViewById(R.id.product_img);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            btn_delte = itemView.findViewById(R.id.btn_delte);
            btn_edit = itemView.findViewById(R.id.btn_edit);

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
        String imgPath = product.getProduct_imgRes();
        Glide.with(holder.product_img.getContext())
                .load(imgPath)
//                .placeholder(R.drawable.default_image) // Ảnh hiện khi đang load
//                .error(R.drawable.default_image)       // Ảnh hiện khi load lỗi
                .into(holder.product_img);
        Log.d("FragmentAdmin", "Selected Image URI: " + imgPath.toString());
        holder.product_name.setText(product.getProduct_name());
        float price = product.getProduct_price();
        String formattedPrice = String.format("%,.0f", price);
        holder.product_price.setText(formattedPrice + " đ");
        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), page_admin_Edit_Product.class);
                intent.putExtra("product_id", product.getProduct_id());
                String product_id = String.valueOf(product.getProduct_id());
                Log.d("FragmentAdmin",  product_id);
                view.getContext().startActivity(intent);
            }
        });

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
