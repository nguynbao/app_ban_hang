package com.example.app_ban_hang.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.app_ban_hang.Model.categories;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.CategoryDao;

import java.util.List;

public class Fragment_Categories extends Fragment {
    private ImageButton back;
    AppCompatButton btn_cate_shoes, btn_cate_clothing, btn_cate_food, btn_cate_accessories, btn_cate_comestic, btn_cate_tech;

    public Fragment_Categories() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__categories, container, false);
        btn_cate_shoes = view.findViewById(R.id.btn_cate_shoes);
        btn_cate_clothing = view.findViewById(R.id.btn_cate_clothing);
        btn_cate_food = view.findViewById(R.id.btn_cate_food);
        btn_cate_accessories = view.findViewById(R.id.btn_cate_accessories);
        btn_cate_comestic = view.findViewById(R.id.btn_cate_comestic);
        btn_cate_tech = view.findViewById(R.id.btn_cate_tech);
        back = view.findViewById(R.id.back);
        btn_cate_shoes.setVisibility(View.GONE);
        btn_cate_clothing.setVisibility(View.GONE);
        btn_cate_food.setVisibility(View.GONE);
        btn_cate_accessories.setVisibility(View.GONE);
        btn_cate_comestic.setVisibility(View.GONE);
        btn_cate_tech.setVisibility(View.GONE);

        loadCate();

        Bundle bundle = new Bundle();
        back.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().popBackStack();
        });
        btn_cate_tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bundle.putString("category_id", "6");
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

                bundle.putString("category_id", "4");
                Fragment_Product fragmentProduct = new Fragment_Product();
                fragmentProduct.setArguments(bundle);
                //chuyển fragment
                getParentFragmentManager().beginTransaction().replace(R.id.Fragment_categories, fragmentProduct).addToBackStack(null).commit();
            }
        });
        btn_cate_comestic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bundle.putString("category_id", "5");
                Fragment_Product fragmentProduct = new Fragment_Product();
                fragmentProduct.setArguments(bundle);
                //chuyển fragment
                getParentFragmentManager().beginTransaction().replace(R.id.Fragment_categories, fragmentProduct).addToBackStack(null).commit();
            }
        });
        return view;
    }
    public void loadCate(){
        CategoryDao categoryDao = new CategoryDao(getContext());
        List<categories> categoriesList = categoryDao.getAllCategories();
        int size = categoriesList.size();
        for (int i = 1; i <= size; i++){
            if (i == 1){
                btn_cate_shoes.setVisibility(View.VISIBLE);
            }
            if (i == 2){
                btn_cate_clothing.setVisibility(View.VISIBLE);
            }
            if (i == 3){
                btn_cate_food.setVisibility(View.VISIBLE);
            }
            if (i == 4){
                btn_cate_accessories.setVisibility(View.VISIBLE);
            }
            if (i == 5){
                btn_cate_comestic.setVisibility(View.VISIBLE);
            }
            if (i == 6){
                btn_cate_tech.setVisibility(View.VISIBLE);
            }
        }

        for (categories categories : categoriesList){
            if (categories.getCategory_id() == 1){
                btn_cate_shoes.setText(categories.getCategory_name());
            }
            if (categories.getCategory_id() == 2){
                btn_cate_clothing.setText(categories.getCategory_name());
            }
            if (categories.getCategory_id() == 3){
                btn_cate_food.setText(categories.getCategory_name());
            }
            if (categories.getCategory_id() == 4){
                btn_cate_accessories.setText(categories.getCategory_name());
            }
            if (categories.getCategory_id() == 5) {
                btn_cate_comestic.setText(categories.getCategory_name());
            }
            if (categories.getCategory_id() == 6) {
                btn_cate_tech.setText(categories.getCategory_name());
            }
        }
    }
}