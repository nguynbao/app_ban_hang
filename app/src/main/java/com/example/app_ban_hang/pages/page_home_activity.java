package com.example.app_ban_hang.pages;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.R;
import com.example.app_ban_hang.adapter.adapter_banner;
import com.example.app_ban_hang.adapter.adapter_categories;
import com.example.app_ban_hang.adapter.adapter_product;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class page_home_activity extends AppCompatActivity {
    RecyclerView recyclerView, recyclerView2, recyclerView3;
    adapter_banner bannerAdapter;
    adapter_categories categoryAdapter;
    adapter_product productAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //banner
        List<Integer> bannerList = new ArrayList<>();
        bannerList.add(R.drawable.banner_two);
        bannerList.add(R.drawable.banner_two);
        bannerList.add(R.drawable.banner_two);
        bannerList.add(R.drawable.banner_two);
        bannerList.add(R.drawable.banner_two);
        bannerList.add(R.drawable.banner_two);
        recyclerView = findViewById(R.id.rcv1);
        bannerAdapter = new adapter_banner(bannerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(bannerAdapter);
        //category
        List<Integer> categoryList = new ArrayList<>();
        categoryList.add(R.drawable.cate_headphone);
        categoryList.add(R.drawable.cate_headphone);
        categoryList.add(R.drawable.cate_headphone);
        categoryList.add(R.drawable.cate_headphone);
        categoryList.add(R.drawable.cate_headphone);
        categoryList.add(R.drawable.cate_headphone);
        categoryList.add(R.drawable.cate_headphone);
        recyclerView2 = findViewById(R.id.rcv2);
        categoryAdapter = new adapter_categories(categoryList);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setAdapter(categoryAdapter);
        //product
        List<Integer> productList = new ArrayList<>();
        productList.add(R.drawable.product_one);
        productList.add(R.drawable.product_one);
        productList.add(R.drawable.product_one);
        productList.add(R.drawable.product_one);
        recyclerView3 = findViewById(R.id.rcv3);
        productAdapter = new adapter_product(productList);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setAdapter(productAdapter);
        //tablayout
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        TabLayout.Tab tab1 = tabLayout.newTab();
        tab1.setText("home");
        tab1.setIcon(R.drawable.img_menu);
        tabLayout.addTab(tab1);
        TabLayout.Tab tab2 = tabLayout.newTab();
        tab2.setText("home");
        tab2.setIcon(R.drawable.img_menu);
        tabLayout.addTab(tab2);
        TabLayout.Tab tab3 = tabLayout.newTab();
        tab3.setText("home");
        tab3.setIcon(R.drawable.img_menu);
        tabLayout.addTab(tab3);
        TabLayout.Tab tab4 = tabLayout.newTab();
        tab4.setText("home");
        tab4.setIcon(R.drawable.img_menu);
        tabLayout.addTab(tab4);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }
}