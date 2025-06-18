package com.example.app_ban_hang.Fragment;

import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.app_ban_hang.API.ApiClient;
import com.example.app_ban_hang.API.ProvinceApi;
import com.example.app_ban_hang.Model.Province;
import com.example.app_ban_hang.Model.users;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.UserDAO;
import com.example.app_ban_hang.pages_admin.page_admin_Edit_user;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment_Account extends Fragment {
    private EditText fullname, email, pass;
    Spinner city;

    AppCompatButton save;
    UserDAO userDAO;

    public Fragment_Account() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__account, container, false);
        fullname = view.findViewById(R.id.fullname);
        email = view.findViewById(R.id.email);
        pass = view.findViewById(R.id.pass);
        city = view.findViewById(R.id.city);
        save = view.findViewById(R.id.save);
        userDAO = new UserDAO(getContext());

        SharedPreferences preferences = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        int userId = preferences.getInt("user_id", -1);
        save.setOnClickListener(view1 -> {
            String fullnamee = this.fullname.getText().toString();
            String email = this.email.getText().toString();
            String pass = this.pass.getText().toString();
            String city = this.city.getSelectedItem().toString();

            users user = userDAO.getUserById(userId);
            user.setUser_name(fullnamee);
            user.setUser_email(email);
            user.setUser_password(pass);
            user.setAddress(city);
            userDAO.update(user); // Gọi DAO để cập nhật dữ liệu
            Toast.makeText(getContext(), "Cập nhật thành công",Toast.LENGTH_SHORT).show();
        });
        ProvinceApi api = ApiClient.getClient().create(ProvinceApi.class);
        if (userId != -1) {
            users user = userDAO.getUserById(userId);
            if (user != null) {
                fullname.setText(user.getUser_name());
                email.setText(user.getUser_email());
                pass.setText(user.getUser_password());
                api.getProvinces().enqueue(new Callback<List<Province>>() {
                    @Override
                    public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                        Log.d("API", "onResponse called");
                        if (response.isSuccessful()){
                            Log.d("API", "SUCCESS, size = " + response.body().size());
                            List<Province> provinceList = response.body();
                            ArrayAdapter<Province> adapter = new ArrayAdapter<>(
                                    getActivity(),
                                    R.layout.spinner_selected_item,
                                    provinceList
                            );
                            adapter.setDropDownViewResource(R.layout.item_spinner_custom);
                            city.setAdapter(adapter);
                            // 👉 Sau khi set adapter xong, đặt giá trị thành phố cho user
                            String userCity = user.getAddress(); // Ví dụ: "Hồ Chí Minh"
                            int selectedIndex = -1;

                            for (int i = 0; i < provinceList.size(); i++) {
                                if (provinceList.get(i).getName().equalsIgnoreCase(userCity)) {
                                    selectedIndex = i;
                                    break;
                                }
                            }

                            if (selectedIndex != -1) {
                                city.setSelection(selectedIndex);
                            }
                        }else {
                            Log.d("API", "Response failed or empty body, code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Province>> call, Throwable t) {
                        Log.d("API", "onFailure called: " + t.getMessage());
                        Toast.makeText(getContext(), "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), "Không tìm thấy người dùng!", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getContext(), "Người dùng chưa đăng nhập!", Toast.LENGTH_SHORT).show();
        }

        return view;
    }
}