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


import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.database;

public class page_dangki_activity extends AppCompatActivity {
    private AppCompatButton dangki;
    private EditText phone, email, pass, ten;
    private database db;
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
        db = new database(this);
        dangki = findViewById(R.id.dangki);
        ten = findViewById(R.id.fullname);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        dangki.setOnClickListener(v -> {
            String t = ten.getText().toString().trim();
            String p = phone.getText().toString().trim();
            String pw = pass.getText().toString().trim();
            String e = email.getText().toString().trim();

            if (t.isEmpty() || p.isEmpty() || pw.isEmpty() || e.isEmpty()){
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            } else if (p.length() != 10){
                Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra email tồn tại trước khi gọi đăng ký
            if (db.checkEmailExists(e)) {
                Toast.makeText(this, "Email đã tồn tại!", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean check = db.dangKi(t, e, pw, p);
            if (check){
                Toast.makeText(this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
            }
        });

    }
}