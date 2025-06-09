package com.example.app_ban_hang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.Model.CartItem;
import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.CartDao;
import com.example.app_ban_hang.database.ProductDao;

import java.util.List;

public class adapter_cart extends RecyclerView.Adapter<adapter_cart.ViewHolder>{
    private List<CartItem> cartItemList;
    public adapter_cart(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }
    @Override
    public int getItemCount() {
        return cartItemList.size();
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_cart.ViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        ProductDao productDao = new ProductDao(holder.itemView.getContext());
        product product = productDao.getById(String.valueOf(cartItem.getProduct_id()));
        holder.productImage.setImageResource(product.getProduct_imgRes());
        holder.productName.setText(product.getProduct_name());
        holder.productPrice.setText(String.valueOf(product.getProduct_price()));
        holder.txtQuantity.setText(String.valueOf(cartItem.getQuantity()));
        holder.btnIncrease.setOnClickListener(v -> {
            CartDao  cartDao = new CartDao(v.getContext());
            int current_quantity = cartItem.getQuantity();
            int add_quantity = current_quantity + 1;
            cartDao.updateQuantity(add_quantity, cartItem.getCart_id());
            // Cập nhật trong list đang hiển thị
            cartItem.setQuantity(add_quantity);
            // Cập nhật lại view
            notifyItemChanged(holder.getAdapterPosition());
        });

        holder.btnDecrease.setOnClickListener(v -> {
            CartDao  cartDao = new CartDao(v.getContext());
            int current_quantity = cartItem.getQuantity();
            int descrease_quantity = current_quantity - 1;
            if(cartItem.getQuantity() > 1){
                cartDao.updateQuantity(descrease_quantity, cartItem.getCart_id());
                // Cập nhật trong list đang hiển thị
                cartItem.setQuantity(descrease_quantity);
                // Cập nhật lại view
                notifyItemChanged(holder.getAdapterPosition());
            }
            else {
                Toast.makeText(holder.itemView.getContext(), "Đã đạt số lượng tối thiểu", Toast.LENGTH_SHORT).show();
            }

        });

    }
}
