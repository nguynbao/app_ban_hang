package com.example.app_ban_hang.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.app_ban_hang.pages.page_detail_activity;
import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import java.io.File;
import java.util.List;

public class adapter_product extends RecyclerView.Adapter<adapter_product.ViewHolder> {
    private static final String TAG = "adapter_product";
    private List<product> productList;

    public adapter_product(List<product> productList) {
        this.productList = productList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;

        public ViewHolder(View view) {
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
        String imgPath = currentProduct.getProduct_imgRes();
        Context context = holder.productImage.getContext();

        // Log the image path for debugging
        Log.d(TAG, "Loading image for product " + currentProduct.getProduct_name() + ": " + imgPath);

        // Handle image loading
        if (imgPath != null && !imgPath.isEmpty()) {
            try {
                File imageFile = new File(imgPath);
                if (imageFile.exists()) {
                    Glide.with(context)
                            .load(imageFile)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .placeholder(R.drawable.product_tee1)
                            .error(R.drawable.product_tee1)
                            .into(holder.productImage);
                } else {
                    Log.e(TAG, "Image file does not exist for product " + currentProduct.getProduct_name() + ": " + imgPath);
                    holder.productImage.setImageResource(R.drawable.product_tee1);
                }
            } catch (Exception e) {
                Log.e(TAG, "Failed to load image for product " + currentProduct.getProduct_name() + ": " + e.getMessage());
                holder.productImage.setImageResource(R.drawable.product_tee1);
            }
        } else {
            Log.w(TAG, "Image path is null or empty for product " + currentProduct.getProduct_name());
            holder.productImage.setImageResource(R.drawable.product_tee1);
        }

        // Set product name and price
        holder.productName.setText(currentProduct.getProduct_name());
        holder.productPrice.setText(String.format("%,.2f", currentProduct.getProduct_price()));

        // Set click listener to open product details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, page_detail_activity.class);
            intent.putExtra("intID", currentProduct.getProduct_id());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}