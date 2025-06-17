package com.example.app_ban_hang.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_ban_hang.Fragment.Fragment_Invoice;
import com.example.app_ban_hang.Model.CartItem;
import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.CartDao;
import com.example.app_ban_hang.database.ProductDao;

import java.util.ArrayList;
import java.util.List;

public class adapter_cart extends RecyclerView.Adapter<adapter_cart.ViewHolder>{
    private List<CartItem> cartItemList;
    private boolean selectedAllItem = false;
    private List<Integer> checkedList = new ArrayList<>();
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
        public CheckBox selected_Item;

        public ViewHolder(View view){
            super(view);
            productImage = view.findViewById(R.id.img_product);
            productName = view.findViewById(R.id.product_name);
            productPrice = view.findViewById(R.id.product_price);
            btnDecrease = view.findViewById(R.id.btn_decrease);
            btnIncrease = view.findViewById(R.id.btn_increase);
            txtQuantity = view.findViewById(R.id.txt_quantity);
            selected_Item = view.findViewById(R.id.selected_Item);

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
        if (selectedAllItem == true){
            holder.selected_Item.setChecked(true);
            if (!checkedList.contains(position)) {
                checkedList.add(position);
                Log.d("Checkedlist", "đã thêm");
            }
        }else{
            holder.selected_Item.setChecked(false);
            if (checkedList.contains(position)) {
                checkedList.remove(Integer.valueOf(position));
                Log.d("Removelist", "đã xóa");
            }
        }
        CartItem cartItem = cartItemList.get(position);
        ProductDao productDao = new ProductDao(holder.itemView.getContext());
        product product = productDao.getById(String.valueOf(cartItem.getProduct_id()));
        Log.d("IDproduct", String.valueOf(cartItem.getProduct_id()));
        Log.d("IDproduct", productDao.getById("1").getProduct_name());
        String imageUri = product.getProduct_imgRes(); // hoặc String imagePath
        if (imageUri != null){
            Glide.with(holder.itemView.getContext())
                    .load(imageUri)
                    .into(holder.productImage);
        }
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

        holder.selected_Item.setOnClickListener(view -> {
            if (!holder.selected_Item.isChecked()){
                if (checkedList.contains(position)) {
                    checkedList.remove(Integer.valueOf(position));
                    Toast.makeText(holder.itemView.getContext(), "Đã gỡ chọn", Toast.LENGTH_SHORT).show();
                }
            }

            if (holder.selected_Item.isChecked()){
                if (!checkedList.contains(position)) {
                    checkedList.add(position);
                    Toast.makeText(holder.itemView.getContext(), "Đã thêm vào danh sách chọn", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void checkedAll (boolean checked){
        selectedAllItem = checked;
        notifyDataSetChanged();
    }

    public void paymentSelected(Context context){
        List<Integer> cartIDList = new ArrayList<>();
        if (checkedList.isEmpty()){
            Toast.makeText(context, "Vui lòng chọn sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }
        for (int position : checkedList){
            CartItem cartItem = cartItemList.get(position);
            int cartID = cartItem.getCart_id();
            cartIDList.add(cartID);
        }
        Fragment_Invoice fragment = new Fragment_Invoice();
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("cartIDList", (ArrayList<Integer>) cartIDList);
        fragment.setArguments(bundle);

        // Replace fragment
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main, fragment)
                .addToBackStack(null)
                .commit();
    }
}
