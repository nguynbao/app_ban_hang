// CartActivity.java
package com.example.app_ban_hang.pages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.Model.CartItem;
import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.adapter.adapter_cart;
import com.example.app_ban_hang.database.CartDao;

import java.util.ArrayList;
import java.util.List;

public class page_cart_activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private adapter_cart adapterCart;
    private List<CartItem> cartItemList;

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
        }
        adapterCart = new adapter_cart(cartItemList);
        recyclerView.setAdapter(adapterCart);
    }
}