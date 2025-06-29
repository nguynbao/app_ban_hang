package com.example.app_ban_hang.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;

import java.io.File;
import java.util.List;
public class adapter_all_product extends BaseAdapter {
    private Context context;
    private List<product> productList;

    public adapter_all_product(Context context, List<product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView tvName, tvPrice;
        ImageView ivImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pro, parent, false);
            holder = new ViewHolder();
            holder.tvName = convertView.findViewById(R.id.product_namee);
            holder.tvPrice = convertView.findViewById(R.id.product_pricee);
            holder.ivImage = convertView.findViewById(R.id.product);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        product p = productList.get(position);
        holder.tvName.setText(p.getProduct_name());
        holder.tvPrice.setText(String.format("%,.2f", p.getProduct_price()));
        // Load ảnh sản phẩm
        String imgPath = p.getProduct_imgRes();
        if (imgPath != null && !imgPath.isEmpty()) {
            File imageFile = new File(imgPath);
            if (imageFile.exists()) {
                Glide.with(context)
                        .load(imageFile)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.product_tee1)
                        .error(R.drawable.product_tee1)
                        .into(holder.ivImage);
            } else {
                Log.e("TAG", "Image file not found: " + imgPath);
                holder.ivImage.setImageResource(R.drawable.product_tee1);
            }
        } else {
            holder.ivImage.setImageResource(R.drawable.product_tee1);
        }

        return convertView;
    }
}