package com.example.app_ban_hang;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_ban_hang.Fragment.Fragment_Product;

public class test_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test);
        // Gắn fragment bạn muốn kiểm thử vào
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new Fragment_Product()) // YourFragment là fragment bạn cần test
                .commit();
    }
}