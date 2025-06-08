package com.example.app_ban_hang.pages_admin;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_ban_hang.Model.categories;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.CategoryDao;

public class page_admin_Edit_cate extends AppCompatActivity {
    EditText Name_edi_cate;
    AppCompatButton edit_cate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_admin_edit_cate);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Name_edi_cate = findViewById(R.id.Name_edit_cate);
        edit_cate = findViewById(R.id.edit_cate);
        int category_id = getIntent().getIntExtra("category_id", -1);
        Log.d("TAG", "onCreate: " + category_id);
        if (category_id != -1) {
            CategoryDao categoryDao = new CategoryDao(this);
            String categoryName = categoryDao.getCategoryNameById(category_id);
            Log.d("TAG", "onCreate: " + categoryName);
            Name_edi_cate.setText(categoryName);
        }
        edit_cate.setOnClickListener(v -> {
            String name = Name_edi_cate.getText().toString();
            if (name.isEmpty()) {
                Name_edi_cate.setError("Vui lòng nhập tên danh mục");
                return;
            }
            categories cate = new categories(category_id, name, R.drawable.product_tee1);
            if (category_id != -1) {
                CategoryDao categoryDao = new CategoryDao(this);
                categoryDao.updateCategory(cate);
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }
}