package com.example.app_ban_hang.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.database;

public class activity_login extends AppCompatActivity {
    private AppCompatButton dangnhap;

    private EditText  email, pass;
    private database db;
    private TextView txt_dangki;

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
        db = new database(this);
        dangnhap = findViewById(R.id.dangnhap);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        dangnhap.setOnClickListener(v -> {
            String e = email.getText().toString().trim();
            String pw = pass.getText().toString().trim();

            if (e.isEmpty() || pw.isEmpty()) {
                Toast.makeText(activity_login.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            // (tuỳ chọn) kiểm tra định dạng email
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(e).matches()) {
                Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean check = db.dangNhap(e, pw);
            if (check) {
                Toast.makeText(activity_login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(activity_login.this, page_home_activity.class));
            } else {
                Toast.makeText(activity_login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        txt_dangki = findViewById(R.id.txt_dangki);

        txt_dangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_login.this, dangki.class);
                startActivity(intent);
            }
        });

    }
}


