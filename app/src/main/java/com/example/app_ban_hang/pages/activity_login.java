package com.example.app_ban_hang.pages;

import static java.lang.Boolean.TRUE;

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


    }
}


