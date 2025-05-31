package com.example.app_ban_hang.pages;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.app_ban_hang.Model.users;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.UserDAO;
import com.example.app_ban_hang.database.database;

public class page_dangki_activity extends AppCompatActivity {
    private AppCompatButton dangki;
    private EditText phone, email, pass, ten, city;
    UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dangki);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        userDAO = new UserDAO(this);
        dangki = findViewById(R.id.dangki);
        ten = findViewById(R.id.fullname);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        city = findViewById(R.id.diachi);
        dangki.setOnClickListener(v -> {
            registerUser();
        });
    }
    private void registerUser() {
        String name = ten.getText().toString().trim();
        String inputemail = email.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String phoneStr = phone.getText().toString().trim();
        String address = city.getText().toString().trim();

        // Validate
        if (name.isEmpty() || inputemail.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin bắt buộc!", Toast.LENGTH_SHORT).show();
            return;
        }

        int phone = 0;
        try {
            phone = Integer.parseInt(phoneStr);
        } catch (NumberFormatException e) {
            phone = 0; // hoặc bạn có thể hiển thị lỗi
        }

        users newUser = new users(0, name, inputemail, password, phone, address);
        long result = userDAO.insert(newUser);

        if (result > 0) {
            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
            finish(); // Quay về màn hình trước đó
        } else {
            Toast.makeText(this, "Đăng ký thất bại! Email đã tồn tại?", Toast.LENGTH_SHORT).show();
        }
}}