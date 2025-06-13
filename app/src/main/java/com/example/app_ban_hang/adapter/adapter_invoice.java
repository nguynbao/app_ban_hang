package com.example.app_ban_hang.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.Model.CartItem;
import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.ProductDao;

import java.util.List;

public class adapter_invoice extends RecyclerView.Adapter<adapter_invoice.ViewHolder> {
    List<CartItem> cartItemList;
    float totalAll = 0;
    Context context;

    public adapter_invoice(List<CartItem> cartItemList, Context context) {
        this.cartItemList = cartItemList;
        this.context = context;
        calculateTotal();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameProduct, priceProduct, quantity_Product, totalPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameProduct = itemView.findViewById(R.id.nameProduct);
            priceProduct = itemView.findViewById(R.id.priceProduct);
            quantity_Product = itemView.findViewById(R.id.quantity_Product);
            totalPrice = itemView.findViewById(R.id.totalPrice);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_invoice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_invoice.ViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        ProductDao productDao = new ProductDao(holder.itemView.getContext());
        product product = productDao.getById(String.valueOf(cartItem.getProduct_id()));
        holder.nameProduct.setText(product.getProduct_name());
        holder.priceProduct.setText(String.valueOf(product.getProduct_price()));
        holder.quantity_Product.setText(String.valueOf(cartItem.getQuantity()));
        holder.totalPrice.setText(String.valueOf(cartItem.getQuantity() * product.getProduct_price()));


    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }
    public float totalAll(){
        return totalAll;
    }

    private void calculateTotal() {
        totalAll = 0;
        ProductDao productDao = new ProductDao(context); // hoặc context từ constructor
        for (CartItem cartItem : cartItemList) {
            product product = productDao.getById(String.valueOf(cartItem.getProduct_id()));
            totalAll += cartItem.getQuantity() * product.getProduct_price();
            Log.d("totalAll", String.valueOf(totalAll));
        }
    }


}
