package com.example.app_ban_hang;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_ban_hang.database.DB_user;
import com.example.app_ban_hang.pages.page_home_activity;

public class activity_login extends AppCompatActivity {
    private AppCompatButton dangnhap;
    private AppCompatButton dangki;
    private EditText phone, email, pass;
    private DB_user db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = new DB_user(this);
        dangnhap = findViewById(R.id.dangnhap);
        dangki = findViewById(R.id.dangki);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        dangki.setOnClickListener(v -> {
            String p = phone.getText().toString();
            String pw= pass.getText().toString();
            String e = email.getText().toString();
            if (p.isEmpty() || pw.isEmpty() || e.isEmpty()){
                Toast.makeText(activity_login.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }else if (p.length() != 10){
                Toast.makeText(activity_login.this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            }
          boolean check = db.dangKi(pw,e, p);
            if (check){
                Toast.makeText(activity_login.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(activity_login.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
            }

        });
        dangnhap.setOnClickListener(v -> {
            String e = email.getText().toString();
            String pw= pass.getText().toString();
            startActivity(new Intent(activity_login.this, page_home_activity.class));
//            boolean check = db.dangNhap(e, pw);
//            if (check){
//                Toast.makeText(activity_login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//
//            }else {
//                Toast.makeText(activity_login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
//            }
//            if (e.isEmpty() || pw.isEmpty()){
//                Toast.makeText(activity_login.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
//            }

        });
    }
}


