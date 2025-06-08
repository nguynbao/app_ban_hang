package com.example.app_ban_hang.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.Fragment;

import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.database.ProductDao;
import com.example.app_ban_hang.R;

import java.util.List;
import java.util.stream.Collectors;

public class Fragment_Search extends Fragment {
    private AutoCompleteTextView search;
    private ProductDao productDao;

    public Fragment_Search() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__search, container, false);
        productDao = new ProductDao(getContext());
        search = view.findViewById(R.id.search);
        List<product> productList = productDao.getAll();
        List<String> productNames = productList.stream()
                .map(product::getProduct_name)
                .collect(Collectors.toList());
        // Adapter cho AutoCompleteTextView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line,
                productNames
        );
        search.setAdapter(adapter);
        search.setThreshold(1); // Bắt đầu gợi ý từ ký tự thứ 1

        return view;
    }
}
