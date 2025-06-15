package com.example.app_ban_hang.pages;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.app_ban_hang.API.ApiClient;
import com.example.app_ban_hang.API.ProvinceApi;
import com.example.app_ban_hang.Model.Province;
import com.example.app_ban_hang.Model.users;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.UserDAO;
import com.example.app_ban_hang.database.database;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class page_dangki_activity extends AppCompatActivity {
    private AppCompatButton dangki;
    private EditText phone, email, pass, ten, city;
    private Spinner spinner_City;
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
        spinner_City = findViewById(R.id.spinner_City);
        ProvinceApi api = ApiClient.getClient().create(ProvinceApi.class);
        api.getProvinces().enqueue(new Callback<List<Province>>() {
            @Override
            public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                Log.d("API", "onResponse called");
                if (response.isSuccessful()){
                    Log.d("API", "SUCCESS, size = " + response.body().size());
                    List<Province> provinceList = response.body();
                    ArrayAdapter<Province> adapter = new ArrayAdapter<>(
                            page_dangki_activity.this,
                            android.R.layout.simple_spinner_item,
                            provinceList
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_City.setAdapter(adapter);
                }else {
                    Log.d("API", "Response failed or empty body, code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Province>> call, Throwable t) {
                Log.d("API", "onFailure called: " + t.getMessage());
                Toast.makeText(page_dangki_activity.this, "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
            }
        });


        dangki.setOnClickListener(v -> {
            registerUser();
        });
    }
    private void registerUser() {
        String name = ten.getText().toString().trim();
        String inputemail = email.getText().toString().trim();
        String password = pass.getText().toString().trim();
        String phoneStr = phone.getText().toString().trim();
        String address = spinner_City.getSelectedItem().toString();

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