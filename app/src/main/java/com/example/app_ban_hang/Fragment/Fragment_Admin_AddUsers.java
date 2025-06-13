package com.example.app_ban_hang.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

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

public class Fragment_Admin_AddUsers extends Fragment {
    EditText ad_add_name_user, ad_add_mail_user, ad_add_pass_user, ad_add_phone_user;
    Spinner ad_add_city_user;
    AppCompatButton ad_save_user;
    UserDAO userDAO;
    public Fragment_Admin_AddUsers() {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__admin_addusers, container, false);
        userDAO = new UserDAO(getContext());
        ad_add_name_user = view.findViewById(R.id.ad_add_name_user);
        ad_add_mail_user = view.findViewById(R.id.ad_add_mail_user);
        ad_add_pass_user = view.findViewById(R.id.ad_add_pass_user);
        ad_add_phone_user = view.findViewById(R.id.ad_add_phone_user);
        ad_add_city_user = view.findViewById(R.id.ad_add_city_user);
        ad_save_user = view.findViewById(R.id.ad_save_user);
       load_api_city(view);
        ad_save_user.setOnClickListener(v -> {
            save_user();
        });
        return view;
    }
    private void load_api_city(View view){
        ProvinceApi api = ApiClient.getClient().create(ProvinceApi.class);
        api.getProvinces().enqueue(new Callback<List<Province>>() {
            @Override
            public void onResponse(@NonNull Call<List<Province>> call, @NonNull Response<List<Province>> response) {
                Log.d("API", "onResponse called");
                if (response.isSuccessful()){
                    assert response.body() != null;
                    Log.d("API", "SUCCESS, size = " + response.body().size());
                    List<Province> provinceList = response.body();
                    ArrayAdapter<Province> adapter = new ArrayAdapter<>(
                            view.getContext(),
                            R.layout.spinner_selected_item,
                            provinceList
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    ad_add_city_user.setAdapter(adapter);
                }else {
                    Log.d("API", "Response failed or empty body, code: " + response.code());
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<Province>> call, @NonNull Throwable t) {
                Log.d("API", "onFailure called: " + t.getMessage());
                Toast.makeText(view.getContext(), "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void save_user(){
        String name = ad_add_name_user.getText().toString();
        String mail = ad_add_mail_user.getText().toString();
        String pass = ad_add_pass_user.getText().toString();
        String phoneStr = ad_add_phone_user.getText().toString();
        String city = ad_add_city_user.getSelectedItem().toString();
        if (name.isEmpty() || mail.isEmpty() || pass.isEmpty() || city.isEmpty()){
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }else {
            int phone = 0;
            try {
                phone = Integer.parseInt(phoneStr);
            } catch (NumberFormatException e) {
                // hoặc bạn có thể hiển thị lỗi
            }
            users newUser = new users(0, name, mail, pass, phone, city);
            userDAO.insert(newUser);
            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            // Xóa dữ liệu đã nhập
            ad_add_name_user.setText("");
            ad_add_mail_user.setText("");
            ad_add_pass_user.setText("");
            ad_add_phone_user.setText("");
            ad_add_city_user.setSelection(0);
        }
    }
}
