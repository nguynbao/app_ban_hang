package com.example.app_ban_hang.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.Model.categories;
import com.example.app_ban_hang.adapter.adapter_admin_AllCate;

import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.CategoryDao;

import java.util.List;

public class Fragment_Admin_AllCate extends Fragment {
    RecyclerView recyclerViewAllCate;
    adapter_admin_AllCate adapter_admin_AllCate;
    List<categories> categoriesList;
    public Fragment_Admin_AllCate() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__admin__all_cate, container, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2) ;
        recyclerViewAllCate = view.findViewById(R.id.recyclerViewAllCate);
        recyclerViewAllCate.setLayoutManager(gridLayoutManager);
        loadAdapter();
        return view;
    }
    @Override
    public void onResume(){
        super.onResume();
        loadAdapter();

    }
    public void loadAdapter() {
        CategoryDao categoryDao = new CategoryDao(getContext());
        categoriesList = categoryDao.getAllCategories();
        adapter_admin_AllCate = new adapter_admin_AllCate(categoriesList);
        recyclerViewAllCate.setAdapter(adapter_admin_AllCate);
    }

}
