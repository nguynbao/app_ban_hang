package com.example.app_ban_hang.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.adapter.adapter_product_overview;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Product extends Fragment {


    public Fragment_Product() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflater.inflate(R.layout.fragment__product, container, false);
        View view = inflater.inflate(R.layout.fragment__product, container, false);
        List<product> productList = new ArrayList<>();
        RecyclerView recycler_Product = view.findViewById(R.id.recycler_Product);
        recycler_Product.setHasFixedSize(true);
        // Dùng GridLayoutManager với 2 cột
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2); // 2 cột
        recycler_Product.setLayoutManager(layoutManager);
        productList.add(new product(1,1,"Loa JBL", "Sản phẩm đỉnh", 5500, R.drawable.product_loudspeakeer));
        productList.add(new product(1,1,"Loa Gojodo", "Sản phẩm đỉnh", 5500, R.drawable.product_loudspeaker1));
        productList.add(new product(1,1,"Loa Kẹo Kéo", "Sản phẩm đỉnh", 5500, R.drawable.product_loudspeaker2));

        adapter_product_overview adapterProductOverview = new adapter_product_overview(productList);
        recycler_Product.setAdapter(adapterProductOverview);
        return view;
    }
}