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

import java.util.ArrayList;
import java.util.List;

public class page_home_activity extends AppCompatActivity {
    RecyclerView recyclerView;
    adapter_banner bannerAdapter;
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
        List<Integer> bannerList = new ArrayList<>();
        bannerList.add(R.drawable.banner_one);
        bannerList.add(R.drawable.banner_two);
        recyclerView = findViewById(R.id.rcv1);
        bannerAdapter = new adapter_banner(bannerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(bannerAdapter);

    }
}