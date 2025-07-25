package com.example.app_ban_hang.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.pages.page_detail_activity;

import java.util.List;

public class adapter_wish extends RecyclerView.Adapter<adapter_wish.ViewHolder> {
    private List<product> productList;
    private Context context;

    public adapter_wish(List<product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgProduct;
        TextView txtName, txtPrice;

        public ViewHolder(View view) {
            super(view);
            imgProduct = view.findViewById(R.id.img_product);
            txtName = view.findViewById(R.id.product_name);
            txtPrice = view.findViewById(R.id.product_price);
        }
    }

    @NonNull
    @Override
    public adapter_wish.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wish, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_wish.ViewHolder holder, int position) {
        product p = productList.get(position);

        holder.txtName.setText(p.getProduct_name());
        float price = p.getProduct_price();
        String formattedPrice = String.format("%,.0f", price);
        holder.txtPrice.setText(formattedPrice + " đ");
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(context, page_detail_activity.class);
               intent.putExtra("intID", p.getProduct_id());
               context.startActivity(intent);
           }
       });


        if (p.getProduct_imgRes() != null) {
            Glide.with(context)
                    .load(p.getProduct_imgRes())
                    .into(holder.imgProduct);
        } else {
            holder.imgProduct.setImageResource(R.drawable.ic_launcher_foreground); // fallback ảnh mặc định
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
