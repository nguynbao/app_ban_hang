package com.example.app_ban_hang.pages;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.app_ban_hang.R;

public class page_detail_activity extends AppCompatActivity {
    private ImageView productImgRes;
    private TextView productName;
    private TextView productPrice;
    private ImageButton addcart;

    private int imgRes;


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
        addcart = findViewById(R.id.addcart);
        Intent intent = getIntent();
        if (intent != null) {
            imgRes  = intent.getIntExtra("product_imgRes", 0);
            String productName = intent.getStringExtra("product_name");
            float productPrice = intent.getFloatExtra("product_price", 0.0f);

            productImgRes.setImageResource(imgRes);
            this.productName.setText(productName);
            this.productPrice.setText(String.format("%.2f", productPrice));  // Hiển thị 2 chữ số thập phân
        }
        addcart.setOnClickListener(v -> {
            Intent intent1 = new Intent(page_detail_activity.this, page_cart_activity.class);

            intent1.putExtra("product_name", productName.getText().toString());

            // Xử lý giá từ textview để đảm bảo parse đúng float
            String priceText = productPrice.getText().toString();
            float price = 0f;
            try {
                price = Float.parseFloat(priceText.replaceAll("[^\\d.]", ""));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            intent1.putExtra("product_price", price);

            // Truyền id resource ảnh đã lưu
            intent1.putExtra("product_imgRes", imgRes);

            startActivity(intent1);
        });

    }
}