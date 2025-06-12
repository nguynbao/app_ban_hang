package com.example.app_ban_hang.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.Model.users;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.adapter.adapter_admin_all_user;
import com.example.app_ban_hang.database.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Admin_AllUsers extends Fragment {
    RecyclerView recyclerView;
    adapter_admin_all_user adapter_admin_all_user;
    List<users> usersList;
    UserDAO userDAO;
    public Fragment_Admin_AllUsers() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__admin_allusers, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_admin);
       loadAdapter();
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        loadAdapter();
    }
    public void loadAdapter(){
        userDAO = new UserDAO(getContext());
        usersList = userDAO.getAll();
        adapter_admin_all_user = new adapter_admin_all_user(usersList);
        recyclerView.setAdapter(adapter_admin_all_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
