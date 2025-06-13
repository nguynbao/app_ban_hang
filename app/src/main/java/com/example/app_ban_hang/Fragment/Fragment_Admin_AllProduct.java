package com.example.app_ban_hang.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.adapter.adapter_admin_product;
import com.example.app_ban_hang.database.ProductDao;

import java.util.List;

public class Fragment_Admin_AllProduct extends Fragment {
    RecyclerView recycler_ProductAll;
    public Fragment_Admin_AllProduct(){};
    ProductDao productDao;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment__admin_allproduct, container ,false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2) ;
        recycler_ProductAll = view.findViewById(R.id.recycler_ProductAll);
        recycler_ProductAll.setLayoutManager(gridLayoutManager);
        loadAdapter();
        return view;
    }
    @Override
    public void onResume(){
        super.onResume();
        loadAdapter();
    }

    private void loadAdapter() {
        productDao = new ProductDao(getContext());
        List<product> productList = productDao.getAll();
        adapter_admin_product adapter = new adapter_admin_product(productList);
        for (product product : productList) {
            Log.d("Product name", product.getProduct_name());
        }
        recycler_ProductAll.setAdapter(adapter);

    }
}
