package com.example.app_ban_hang.pages;


import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;


import com.example.app_ban_hang.R;
import com.example.app_ban_hang.adapter.adapter_viewpages;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class page_home_activity extends AppCompatActivity {
    ViewPager2 viewPager;
    TabLayout tabLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            var systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager.setAdapter(new adapter_viewpages(this));
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Home");
                            tab.setIcon(R.drawable.icon_home);
                            break;
                        case 1:
                            tab.setText("Categories");
                            tab.setIcon(R.drawable.icon_cate);
                            break;
                        case 2:
                            tab.setText("Search");
                            tab.setIcon(R.drawable.icon_search);
                            break;
                        case 3:
                            tab.setText("Account");
                            tab.setIcon(R.drawable.icon_account);
                            break;
                    }
                }).attach();
    }

    }



