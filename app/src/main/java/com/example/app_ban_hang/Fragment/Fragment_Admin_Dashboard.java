package com.example.app_ban_hang.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.Model.order;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.adapter.adapter_admin_All_order;
import com.example.app_ban_hang.database.OrderDao;

import java.util.List;

public class Fragment_Admin_Dashboard extends Fragment {
    RecyclerView recyclerView;
    adapter_admin_All_order adapter;
    List<order> orderList;
    public Fragment_Admin_Dashboard() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__admin_dashboard, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_admin_order);
        OrderDao orderDao = new OrderDao(getContext());
        orderList = orderDao.getAll();
        adapter = new adapter_admin_All_order(orderList);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
