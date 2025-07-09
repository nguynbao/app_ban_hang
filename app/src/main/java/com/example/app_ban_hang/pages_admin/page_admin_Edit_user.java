package com.example.app_ban_hang.pages_admin;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.app_ban_hang.pages.page_dangki_activity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class page_admin_Edit_user extends AppCompatActivity {
    EditText admin_fullname, admin_email, admin_pass, admin_phone;
    Spinner admin_city;
    AppCompatButton admin_save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_admin_edit_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        admin_fullname = findViewById(R.id.admin_fullname);
        admin_email = findViewById(R.id.admin_email);
        admin_pass = findViewById(R.id.admin_pass);
        admin_phone = findViewById(R.id.admin_phone);
        admin_city = findViewById(R.id.admin_city);
        admin_save = findViewById(R.id.admin_save);
        UserDAO userDAO = new UserDAO(this);
        int userId = getIntent().getIntExtra("user_id", -1);
        Log.d("user_id2", String.valueOf(userId));
        users user = userDAO.getUserById(userId);
            if (user != null) {
            admin_fullname.setText(user.getUser_name());
            admin_email.setText(user.getUser_email());
            admin_pass.setText(user.getUser_password());
            admin_phone.setText("0" + String.valueOf(user.getPhone()));
        } else {
            Toast.makeText(this, "Không tìm thấy người dùng!", Toast.LENGTH_SHORT).show();
        }

        ProvinceApi api = ApiClient.getClient().create(ProvinceApi.class);
        api.getProvinces().enqueue(new Callback<List<Province>>() {
            @Override
            public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                Log.d("API", "onResponse called");
                if (response.isSuccessful()){
                    Log.d("API", "SUCCESS, size = " + response.body().size());
                    List<Province> provinceList = response.body();
                    ArrayAdapter<Province> adapter = new ArrayAdapter<>(
                            page_admin_Edit_user.this,
                            R.layout.spinner_selected_item,
                            provinceList
                    );
                    adapter.setDropDownViewResource(R.layout.item_spinner_custom);
                    admin_city.setAdapter(adapter);
                    // 👉 Sau khi set adapter xong, đặt giá trị thành phố cho user
                    if (user != null) {
                        String userCity = user.getAddress(); // Ví dụ: "Hồ Chí Minh"
                        int selectedIndex = -1;

                        for (int i = 0; i < provinceList.size(); i++) {
                            if (provinceList.get(i).getName().equalsIgnoreCase(userCity)) {
                                selectedIndex = i;
                                break;
                            }
                        }

                        if (selectedIndex != -1) {
                            admin_city.setSelection(selectedIndex);
                        }
                    }
                }else {
                    Log.d("API", "Response failed or empty body, code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Province>> call, Throwable t) {
                Log.d("API", "onFailure called: " + t.getMessage());
                Toast.makeText(page_admin_Edit_user.this, "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
            }
        });
        admin_save.setOnClickListener(view -> {
            String fullname = admin_fullname.getText().toString();
            String email = admin_email.getText().toString();
            String pass = admin_pass.getText().toString();
            String phone = admin_phone.getText().toString();
            String city = admin_city.getSelectedItem().toString();
            if (user != null) {
                user.setUser_name(fullname);
                user.setUser_email(email);
                user.setUser_password(pass);
                user.setPhone(Integer.parseInt(phone));
                user.setAddress(city);
                userDAO.update(user);
                Toast.makeText(page_admin_Edit_user.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
}