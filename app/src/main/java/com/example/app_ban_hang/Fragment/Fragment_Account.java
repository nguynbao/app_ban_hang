package com.example.app_ban_hang.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_ban_hang.Model.users;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.UserDAO;


public class Fragment_Account extends Fragment {
    private EditText fullname, email, pass, city;
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
        userDAO = new UserDAO(getContext());
        SharedPreferences preferences = getActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        int userId = preferences.getInt("user_id", -1);
        if (userId != -1) {
            users user = userDAO.getUserById(userId);
            if (user != null) {
                fullname.setText(user.getUser_name());
                email.setText(user.getUser_email());
                pass.setText(user.getUser_password());
                city.setText(user.getAddress());
            } else {
                Toast.makeText(getContext(), "Không tìm thấy người dùng!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Người dùng chưa đăng nhập!", Toast.LENGTH_SHORT).show();
        }

        return view;
    }



}