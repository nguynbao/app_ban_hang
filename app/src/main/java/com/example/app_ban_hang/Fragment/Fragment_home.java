package com.example.app_ban_hang.Fragment;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.adapter.adapter_banner;
import com.example.app_ban_hang.adapter.adapter_categories;
import com.example.app_ban_hang.adapter.adapter_product;
import com.example.app_ban_hang.database.ProductDao;

import java.util.Arrays;
import java.util.List;

public class Fragment_home extends Fragment {
    private RecyclerView recyclerViewBanner, recyclerViewCategory, recyclerViewProduct;
    private AppCompatButton searchButton;
    public Fragment_home() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__home, container, false);
        recyclerViewBanner = view.findViewById(R.id.rcv1);
        recyclerViewCategory = view.findViewById(R.id.rcv2);
        recyclerViewProduct = view.findViewById(R.id.rcv3);
        searchButton = view.findViewById(R.id.search);
        setupRecyclerViews();
        setupSearchButton();
        return view;
    }
    private void setupRecyclerViews() {
        // Banner
        List<Integer> banners = Arrays.asList(
                R.drawable.banner_two, R.drawable.banner_two, R.drawable.banner_two,
                R.drawable.banner_two, R.drawable.banner_two, R.drawable.banner_two
        );
        recyclerViewBanner.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewBanner.setAdapter(new adapter_banner(banners));

        // Categories
        List<Integer> categories = Arrays.asList(
                R.drawable.cate_headphone, R.drawable.cate_headphone, R.drawable.cate_headphone,
                R.drawable.cate_headphone, R.drawable.cate_headphone, R.drawable.cate_headphone,
                R.drawable.cate_headphone
        );
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory.setAdapter(new adapter_categories(categories));

        // Products
        ProductDao ProductDao = new ProductDao(requireContext());
        List<product> products = ProductDao.getAll();
        if (products.size() > 5) {
            products = products.subList(0, 5);
        }
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewProduct.setAdapter(new adapter_product(products));
    }

    private void setupSearchButton() {
        searchButton.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), Fragment_Search.class))
        );
    }
}
