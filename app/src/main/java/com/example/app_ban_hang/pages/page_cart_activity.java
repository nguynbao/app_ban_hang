// CartActivity.java
package com.example.app_ban_hang.pages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.Model.CartItem;
import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.adapter.adapter_cart;
import com.example.app_ban_hang.database.CartDao;
import com.example.app_ban_hang.database.ProductDao;

import java.util.ArrayList;
import java.util.List;

public class page_cart_activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private adapter_cart adapterCart;
    private List<CartItem> cartItemList;
    private List<CartItem> cartItemList2;
    private CheckBox selectedAllItem;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_cart_activity);

        recyclerView = findViewById(R.id.recyclerViewCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        int userID = sharedPreferences.getInt("user_id", -1); // -1 là giá trị mặc định nếu không có
        Log.d("USERIID", String.valueOf(userID));
        CartDao cartDao = new CartDao(this);
        cartItemList = cartDao.getItemIdUser(String.valueOf(userID));
        for (CartItem cartItem : cartItemList){
            Log.d("GetAll", String.valueOf(cartItem.getCart_id()));
            String productID = String.valueOf(cartItem.getProduct_id());
            ProductDao productDao = new ProductDao(this);
            product product = productDao.getById(productID);
            if (product == null){
                Log.d("NotFindProduct", "Khong tim thay id san pham");
                cartDao.deletedCartByIdProduct(cartItem.getProduct_id());
            }
        }
        selectedAllItem = findViewById(R.id.selectedAllItem);
        cartItemList2 = cartDao.getItemIdUser(String.valueOf(userID));
        adapterCart = new adapter_cart(cartItemList2);
        recyclerView.setAdapter(adapterCart);
        selectedAllItem.setOnClickListener(v -> {
            if (selectedAllItem.isChecked()){
                adapterCart.checkedAll(true);
            }
            if (!selectedAllItem.isChecked()){
                adapterCart.checkedAll(false);
            }
        });
        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(view -> {
            adapterCart.paymentSelected(this);
        });
    }
}