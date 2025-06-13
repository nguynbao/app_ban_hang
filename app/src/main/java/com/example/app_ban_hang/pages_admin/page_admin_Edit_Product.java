package com.example.app_ban_hang.pages_admin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.CategoryDao;
import com.example.app_ban_hang.database.ProductDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class page_admin_Edit_Product extends AppCompatActivity {
    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int REQUEST_PERMISSION = 100;

    private EditText editProductName, editPrice, editDesc;
    private Spinner spinnerCategory;
    private ImageView imageProduct;
    private Button btnUpdateProduct;
    private String selectedImagePath = null;

    private product currentProduct;
    private ProductDao productDao;
    private CategoryDao categoryDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_admin_edit_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        editProductName = findViewById(R.id.ed_productName);
        editPrice = findViewById(R.id.ed_price);
        editDesc = findViewById(R.id.ed_Desc);
        spinnerCategory = findViewById(R.id.ed_cateID);
        imageProduct = findViewById(R.id.bn_imgRes);
        btnUpdateProduct = findViewById(R.id.bn_addProduct);

        // Initialize DAOs
        productDao = new ProductDao(this);
        categoryDao = new CategoryDao(this);

        // Get product ID from intent
        int productId = getIntent().getIntExtra("product_id", -1);
        if (productId != -1) {
            currentProduct = productDao.getById(String.valueOf(productId));
            if (currentProduct != null) {
                // Populate fields with existing product data
                editProductName.setText(currentProduct.getProduct_name());
                editPrice.setText(String.valueOf(currentProduct.getProduct_price()));
                editDesc.setText(currentProduct.getProduct_description());
                selectedImagePath = currentProduct.getProduct_imgRes();

                // Refresh category spinner with selected category
                refreshCategorySpinner(currentProduct.getCategory_id());

                // Load product image
                Glide.with(this)
                        .load(selectedImagePath)
                        .into(imageProduct);
            } else {
                Toast.makeText(this, "Không tìm thấy sản phẩm!", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Lỗi tải sản phẩm!", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Set listeners
        imageProduct.setOnClickListener(v -> checkPermissionAndOpenImagePicker());

        btnUpdateProduct.setOnClickListener(v -> updateProduct());
    }

    private void refreshCategorySpinner(int selectedId) {
        List<String> categoryNames = categoryDao.getAllCategoryNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categoryNames
        );
        spinnerCategory.setAdapter(adapter);

        String selectedName = categoryDao.getCategoryNameById(selectedId);
        if (selectedName != null) {
            int position = adapter.getPosition(selectedName);
            spinnerCategory.setSelection(position);
        }
    }

    private void updateProduct() {
        String name = editProductName.getText().toString().trim();
        String priceStr = editPrice.getText().toString().trim();
        String desc = editDesc.getText().toString().trim();
        String selectedCategoryName = spinnerCategory.getSelectedItem() != null ?
                spinnerCategory.getSelectedItem().toString() : null;

        // Validate input
        if (name.isEmpty() || priceStr.isEmpty() || desc.isEmpty() ||
                selectedCategoryName == null || selectedImagePath == null) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            float price = Float.parseFloat(priceStr);
            int categoryId = categoryDao.getCategoryIdByName(selectedCategoryName);

            if (categoryId == -1) {
                Toast.makeText(this, "Danh mục không hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Update product details
            currentProduct.setProduct_name(name);
            currentProduct.setProduct_price(price);
            currentProduct.setProduct_description(desc);
            currentProduct.setCategory_id(categoryId);
            currentProduct.setProduct_imgRes(selectedImagePath);

            // Perform update
            int result = productDao.update(currentProduct);
            if (result > 0) {
                Toast.makeText(this, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá sản phẩm không hợp lệ!", Toast.LENGTH_SHORT).show();
        }
    }



    private void checkPermissionAndOpenImagePicker() {
        String permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permission = Manifest.permission.READ_MEDIA_IMAGES;
        } else {
            permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        }

        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{permission}, REQUEST_PERMISSION);
        } else {
            openImagePicker();
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivityForResult(Intent.createChooser(intent, "Chọn hình ảnh"), REQUEST_IMAGE_PICK);
        } catch (Exception e) {
            Log.e("ImagePicker", "Failed to open image picker: " + e.getMessage());
            Toast.makeText(this, "Không thể mở thư viện hình ảnh!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                try {
                    String fileName = "product_image_" + System.currentTimeMillis() + ".jpg";
                    File outputFile = new File(this.getFilesDir(), fileName);
                    try (InputStream in = this.getContentResolver().openInputStream(imageUri);
                         FileOutputStream out = new FileOutputStream(outputFile)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = in.read(buffer)) != -1) {
                            out.write(buffer, 0, len);
                        }
                    }
                    selectedImagePath = outputFile.getAbsolutePath();
                    imageProduct.setImageURI(imageUri);
                } catch (Exception e) {
                    Log.e("ImageProcessing", "Failed to copy image: " + e.getMessage());
                    Toast.makeText(this, "Không thể xử lý hình ảnh!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openImagePicker();
        } else {
            Toast.makeText(this, "Quyền truy cập bị từ chối, không thể chọn hình ảnh!", Toast.LENGTH_SHORT).show();
        }
    }
}