package com.example.app_ban_hang.pages;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_ban_hang.R;

public class page_detail_activity extends AppCompatActivity {
    private ImageView productImgRes;
    private TextView productName;
    private TextView productPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment__detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        productImgRes = findViewById(R.id.detail_image);
        productName = findViewById(R.id.detail_name);
        productPrice = findViewById(R.id.detail_price);
        Intent intent = getIntent();
        if (intent != null) {
            int imgRes  = intent.getIntExtra("product_imgRes", 0);
            String productName = intent.getStringExtra("product_name");
            float productPrice = intent.getFloatExtra("product_price", 0.0f);

            productImgRes.setImageResource(imgRes);
            this.productName.setText(productName);
            this.productPrice.setText(String.format("%.2f", productPrice));  // Hiển thị 2 chữ số thập phân
        }

    }
}