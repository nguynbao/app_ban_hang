package com.example.app_ban_hang.pages_admin;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.app_ban_hang.Fragment.Fragment_Admin_AddProduct;
import com.example.app_ban_hang.Fragment.Fragment_Admin_AllProduct;
import com.example.app_ban_hang.Fragment.Fragment_Product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.adapter.adapter_viewpagerAdmin;
import com.google.android.material.navigation.NavigationView;


public class page_admin_activity extends AppCompatActivity {
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        name = findViewById(R.id.name);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ImageView openMenu = findViewById(R.id.open_menu);
        NavigationView navigation_view = findViewById(R.id.navigation_view);
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        viewPager.setUserInputEnabled(false);
        viewPager.setAdapter(new adapter_viewpagerAdmin(page_admin_activity.this));

        openMenu.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigation_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_Dashboard) {
                    viewPager.setCurrentItem(0);// danh thu
                } else if (id == R.id.nav_cate_add) {
                    viewPager.setCurrentItem(1);
                    name.setText("Danh mục");// thêm danh mục
                } else if (id == R.id.nav_cate_all) {
                    viewPager.setCurrentItem(2);
                    name.setText("Danh mục");// danh mục
                    // tất cả danh mục
                } else if (id == R.id.nav_product_add) {
                    viewPager.setCurrentItem(3);
                    name.setText("Sản phẩm");// thêm sản phẩm
                } else if (id == R.id.nav_product_all) {
                    viewPager.setCurrentItem(4);
                    name.setText("Sản phẩm");
                } else if (id == R.id.nav_users_add) {
                    viewPager.setCurrentItem(5);// thêm người dùng
                    name.setText("Người dùng");
                }else if (id == R.id.nav_users_all) {
                    viewPager.setCurrentItem(6);// tất cả người dùng
                    name.setText("Người dùng");
                }
                return true;
            }
        });
    }
}