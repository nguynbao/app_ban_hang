package com.example.app_ban_hang.pages;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.app_ban_hang.Model.CartItem;
import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.CartDao;
import com.example.app_ban_hang.database.WishlistDAO;
import com.example.app_ban_hang.database.ProductDao;

public class page_detail_activity extends AppCompatActivity {
    private ImageView productImgRes;
    private TextView productName;
    private TextView productPrice;
    private ImageButton addcart, addwish;

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

        // Ánh xạ view
        productImgRes = findViewById(R.id.detail_image);
        productName = findViewById(R.id.detail_name);
        productPrice = findViewById(R.id.detail_price);
        addcart = findViewById(R.id.addcart);
        addwish = findViewById(R.id.add_wish);

        // Nhận productId từ Intent
        int productId = getIntent().getIntExtra("intID", -1);
        Log.d("productId", String.valueOf(productId));

        // Lấy dữ liệu sản phẩm từ database
        ProductDao productDao = new ProductDao(this);
        product product = productDao.getById(String.valueOf(productId));

        if (product != null) {
            // Lấy ảnh dưới dạng String từ database
            String imageUri = product.getProduct_imgRes();
            if (imageUri != null) {
                Glide.with(this)
                        .load(imageUri)
                        .into(productImgRes);
                // Hiển thị tên và giá
                productName.setText(product.getProduct_name());
                productPrice.setText(String.valueOf(product.getProduct_price()));

                // Xử lý nút thêm giỏ hàng
                addcart.setOnClickListener(v -> {
                    SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                    int userID = sharedPreferences.getInt("user_id", -1); // -1 nếu chưa đăng nhập
                    Log.d("USERIID", String.valueOf(userID));

                    CartDao cartDao = new CartDao(this);
                    CartItem cartItem = new CartItem(userID, productId, 1);

                    // Kiểm tra sản phẩm đã có trong giỏ hàng hay chưa
                    CartItem cartItemIdProduct = cartDao.getItemIdProduct(String.valueOf(product.getProduct_id()));
                    if (cartItemIdProduct != null && product.getProduct_id() == cartItemIdProduct.getProduct_id()) {
                        Toast.makeText(this, "Sản phẩm đã có trong giỏ hàng", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        long check_insert = cartDao.insert(cartItem);
                        if (check_insert == -1) {
                            Toast.makeText(this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                        }
                    }
                    // Chuyển sang giỏ hàng
                    Intent intent = new Intent(page_detail_activity.this, page_cart_activity.class);
                    startActivity(intent);
                });
                // Xử lý nút thêm yêu thích
                addwish.setOnClickListener(v -> {
                    SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
                    int userID = sharedPreferences.getInt("user_id", -1); // -1 nếu chưa đăng nhập
                    Log.d("USERIID", String.valueOf(userID));

                    WishlistDAO wishlistDAO = new WishlistDAO(this);

                    // Kiểm tra sản phẩm đã có trong danh sách yêu thích chưa
                    if (wishlistDAO.isProductInWishlist(userID, product.getProduct_id())) {
                        Toast.makeText(this, "Sản phẩm đã có trong danh sách yêu thích", Toast.LENGTH_SHORT).show();
                    } else {
                        // Thêm sản phẩm vào danh sách yêu thích
                        long check_insert = wishlistDAO.insertWishlist(userID, product.getProduct_id());
                        if (check_insert == -1) {
                            Toast.makeText(this, "Thêm vào danh sách yêu thích thất bại", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Sản phẩm đã được thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            } else {
                Toast.makeText(this, "Không có thông tin sản phẩm", Toast.LENGTH_SHORT).show();
                finish(); // hoặc chuyển hướng người dùng
            }
        }
    }}