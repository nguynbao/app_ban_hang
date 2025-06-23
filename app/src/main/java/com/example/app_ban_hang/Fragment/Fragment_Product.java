package com.example.app_ban_hang.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.adapter.adapter_product_overview;
import com.example.app_ban_hang.database.ProductDao;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Product extends Fragment {

    private ImageButton back;
    public Fragment_Product() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__product, container, false);
        back = view.findViewById(R.id.back);
        back.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        List<product> productList = new ArrayList<>();
        RecyclerView recycler_Product = view.findViewById(R.id.recycler_Product);
        recycler_Product.setHasFixedSize(true);
        // Dùng GridLayoutManager với 2 cột
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2); // 2 cột
        recycler_Product.setLayoutManager(layoutManager);
        ProductDao productDao = new ProductDao(getContext());

        List<product> test = productDao.getAll();
        //Lấy bundle từ categories
        Bundle bundle = getArguments();
        List<product> filter_cateID = null;
        if (bundle != null) {
            String cate_id = bundle.getString("category_id");
            Log.d("cate_id", cate_id);
            filter_cateID = productDao.getByCategoryId(cate_id);
            for (product product : filter_cateID){
                Log.d("TestproductInsert",product.getProduct_name());
            }
        }

        adapter_product_overview adapterProductOverview = new adapter_product_overview(filter_cateID);
        recycler_Product.setAdapter(adapterProductOverview);
        return view;
    }
}