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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Product#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Product extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Product() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Product.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Product newInstance(String param1, String param2) {
        Fragment_Product fragment = new Fragment_Product();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        productList.add(new product(1,"Loa JBL", "Sản phẩm đỉnh", 5500, R.drawable.product_loudspeakeer));
        productList.add(new product(1,"Loa Gojodo", "Sản phẩm đỉnh", 5500, R.drawable.product_loudspeaker1));
        productList.add(new product(1,"Loa Kẹo Kéo", "Sản phẩm đỉnh", 5500, R.drawable.product_loudspeaker2));

        adapter_product_overview adapterProductOverview = new adapter_product_overview(productList);
        recycler_Product.setAdapter(adapterProductOverview);
        return view;
    }
}