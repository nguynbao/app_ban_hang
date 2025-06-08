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

import java.util.List;

public class adapter_cart extends RecyclerView.Adapter<adapter_cart.ViewHolder>{
    private List<product> cartList;

    public adapter_cart(List<product> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public adapter_cart.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_cart.ViewHolder holder, int position) {
        product currentCart = cartList.get(position);
        holder.productImage.setImageURI(currentCart.getProduct_imgRes());
        holder.productName.setText(currentCart.getProduct_name());
        holder.productPrice.setText(String.valueOf(currentCart.getProduct_price()));
        holder.txtQuantity.setText(String.valueOf(currentCart.getQuantity()));
        holder.btnDecrease.setOnClickListener(v -> {
            int currentQuantity = currentCart.getQuantity();
            if (currentQuantity > 1) {
                currentCart.setQuantity(currentQuantity - 1);
                holder.txtQuantity.setText(String.valueOf(currentCart.getQuantity()));
                notifyItemChanged(position);
            }
        });

        holder.btnIncrease.setOnClickListener(v -> {
            int currentQuantity = currentCart.getQuantity();
            currentCart.setQuantity(currentQuantity + 1);
            holder.txtQuantity.setText(String.valueOf(currentCart.getQuantity()));
            notifyItemChanged(position);
        });

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView productImage;
        public TextView productName;
        public TextView productPrice;
        public Button btnDecrease;
        public Button btnIncrease;
        public TextView txtQuantity;

        public ViewHolder(View view){
            super(view);
            productImage = view.findViewById(R.id.img_product);
            productName = view.findViewById(R.id.product_name);
             productPrice = view.findViewById(R.id.product_price);
             btnDecrease = view.findViewById(R.id.btn_decrease);
             btnIncrease = view.findViewById(R.id.btn_increase);
             txtQuantity = view.findViewById(R.id.txt_quantity);

    }
}
}
