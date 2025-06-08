// CartActivity.java
package com.example.app_ban_hang.pages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.adapter.adapter_cart;

import java.util.ArrayList;
import java.util.List;

public class page_cart_activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private adapter_cart adapterCart;
    private List<product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_cart_activity);

        recyclerView = findViewById(R.id.recyclerViewCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();

        Intent intent = getIntent();
        if (intent != null) {
            String imgResString = getIntent().getStringExtra("product_imgRes");
            Uri imgRes = Uri.parse(imgResString);
            String name = intent.getStringExtra("product_name");
            float price = intent.getFloatExtra("product_price", 0.0f);

            Log.d("page_cart_activity", "Received product: " + name + ", price: " + price + ", imgRes: " + imgRes);

            int categoryId = 0;
            if (name != null && imgRes != null) {
                productList.add(new product(categoryId, name, "", price, imgRes));
            } else {
                Log.d("page_cart_activity", "Product data incomplete.");
            }
        } else {
            Log.d("page_cart_activity", "Intent is null");
        }

        adapterCart = new adapter_cart(productList);
        recyclerView.setAdapter(adapterCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterCart.notifyDataSetChanged();
    }
}