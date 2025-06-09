package com.example.app_ban_hang.pages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.service.autofill.UserData;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.app_ban_hang.Model.CartItem;
import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.Model.users;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.CartDao;
import com.example.app_ban_hang.database.ProductDao;
import com.example.app_ban_hang.database.UserDAO;

import java.util.List;

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
        int productId = getIntent().getIntExtra("intID", -1);
        Log.d("productId", String.valueOf(productId));
        ProductDao productDao = new ProductDao(this);
        product product = productDao.getById(String.valueOf(productId));
        productImgRes.setImageResource(product.getProduct_imgRes());
        productName.setText(product.getProduct_name());
        productPrice.setText(String.valueOf(product.getProduct_price()));

        addcart.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
            int userID = sharedPreferences.getInt("user_id", -1); // -1 là giá trị mặc định nếu không có
            Log.d("USERIID", String.valueOf(userID));
            CartDao cartDao = new CartDao(this);
            CartItem cartItem = new CartItem(userID, productId, 1);
            Log.d("ProductID", String.valueOf(productId));
            CartItem cartItemIdProduct = cartDao.getItemIdProduct(String.valueOf(product.getProduct_id()));
            if (product.getProduct_id()  == cartItemIdProduct.getProduct_id()){
                Toast.makeText(this, "Sản phẩm đã có trong giỏ hàng", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(page_detail_activity.this, page_cart_activity.class);
                startActivity(intent);
            }
            else {
                long check_insert = cartDao.insert(cartItem);
                if (check_insert != -1){
                    Log.d("Insert", "Thêm thất bại");
                }
                Toast.makeText(this, "Sản phẩm thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(page_detail_activity.this, page_cart_activity.class);
                startActivity(intent);
            }



        });

    }
}