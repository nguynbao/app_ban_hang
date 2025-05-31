package com.example.app_ban_hang.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.app_ban_hang.R;

public class Fragment_Categories extends Fragment {
    private ImageButton back;

    public Fragment_Categories() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__categories, container, false);
        ImageButton btn_cate_shoes = view.findViewById(R.id.btn_cate_shoes);
        ImageButton btn_cate_clothing = view.findViewById(R.id.btn_cate_clothing);
        ImageButton btn_cate_food = view.findViewById(R.id.btn_cate_food);
        ImageButton btn_cate_accessories = view.findViewById(R.id.btn_cate_accessories);
        ImageButton btn_cate_comestic = view.findViewById(R.id.btn_cate_comestic);
        ImageButton btn_cate_tech = view.findViewById(R.id.btn_cate_tech);
        back = view.findViewById(R.id.back);
        Bundle bundle = new Bundle();
        back.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        btn_cate_tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bundle.putString("category_id", "4");
                Fragment_Product fragmentProduct = new Fragment_Product();
                fragmentProduct.setArguments(bundle);
                //chuyển fragment
                getParentFragmentManager().beginTransaction().replace(R.id.Fragment_categories, fragmentProduct).addToBackStack(null).commit();
            }
        });

        btn_cate_shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bundle.putString("category_id", "1");
                Fragment_Product fragmentProduct = new Fragment_Product();
                fragmentProduct.setArguments(bundle);
                //chuyển fragment
                getParentFragmentManager().beginTransaction().replace(R.id.Fragment_categories, fragmentProduct).addToBackStack(null).commit();
            }
        });

        btn_cate_clothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bundle.putString("category_id", "2");
                Fragment_Product fragmentProduct = new Fragment_Product();
                fragmentProduct.setArguments(bundle);
                //chuyển fragment
                getParentFragmentManager().beginTransaction().replace(R.id.Fragment_categories, fragmentProduct).addToBackStack(null).commit();
            }
        });

        btn_cate_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bundle.putString("category_id", "3");
                Fragment_Product fragmentProduct = new Fragment_Product();
                fragmentProduct.setArguments(bundle);
                //chuyển fragment
                getParentFragmentManager().beginTransaction().replace(R.id.Fragment_categories, fragmentProduct).addToBackStack(null).commit();
            }
        });

        btn_cate_accessories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bundle.putString("category_id", "5");
                Fragment_Product fragmentProduct = new Fragment_Product();
                fragmentProduct.setArguments(bundle);
                //chuyển fragment
                getParentFragmentManager().beginTransaction().replace(R.id.Fragment_categories, fragmentProduct).addToBackStack(null).commit();
            }
        });

        btn_cate_comestic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bundle.putString("category_id", "6");
                Fragment_Product fragmentProduct = new Fragment_Product();
                fragmentProduct.setArguments(bundle);
                //chuyển fragment
                getParentFragmentManager().beginTransaction().replace(R.id.Fragment_categories, fragmentProduct).addToBackStack(null).commit();
            }
        });
        return view;
    }
}