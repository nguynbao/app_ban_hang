package com.example.app_ban_hang.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.adapter.adapter_product_overview;
import com.example.app_ban_hang.database.productDao;

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
        productList.add(new product(1, 4, "Loa JBL", "Sản phẩm đỉnh", 5500, R.drawable.product_loudspeakeer));
        productList.add(new product(2, 4, "Loa Gojodo", "Sản phẩm đỉnh", 5500, R.drawable.product_loudspeaker1));
        productList.add(new product(3, 4, "Loa Kẹo Kéo", "Sản phẩm đỉnh", 5500, R.drawable.product_loudspeaker2));
        productList.add(new product(4, 1, "Giày tây", "Sản phẩm đỉnh", 150, R.drawable.product_shoe1));
        productList.add(new product(5, 1, "Giày bata", "Sản phẩm đỉnh", 250, R.drawable.product_shoe2));
        productList.add(new product(6, 2, "Áo VECTOR", "Sản phẩm đỉnh", 150, R.drawable.product_tee1));
        productList.add(new product(7, 2, "DanhVector", "Sản phẩm đỉnh", 250, R.drawable.product_tee2));
        productList.add(new product(8, 3, "Dụng cụ ăn uống1", "Sản phẩm đỉnh", 150, R.drawable.product_eat1));
        productList.add(new product(9, 3, "Dụng cụ ăn uống2", "Sản phẩm đỉnh", 250, R.drawable.product_eat2));
        productList.add(new product(10, 5, "Dây chuyền 1", "Sản phẩm đỉnh", 150, R.drawable.product_accessories1));
        productList.add(new product(11, 5, "Dây chuyền 2", "Sản phẩm đỉnh", 250, R.drawable.product_accessories2));
        productList.add(new product(12, 6, "Son môi", "Sản phẩm đỉnh", 150, R.drawable.product_comestics1));
        productList.add(new product(13, 6, "Trang điểm", "Sản phẩm đỉnh", 250, R.drawable.product_comestics2));
        productDao productDao = new productDao(getContext());
        for (product product : productList) {
            productDao.insert(product);
        }

        List<product> test = productDao.getAll();
        for (product product : test) {
            Log.d("Testproduct",product.getProduct_name());
        }
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