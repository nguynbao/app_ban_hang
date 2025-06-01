package com.example.app_ban_hang.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.ProductDao;

public class Fragment_Admin_AddProduct extends Fragment {

    public Fragment_Admin_AddProduct() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__admin__add_product, container, false);
        EditText edt_cateID, edt_productName, edt_price, edt_Desc;
        ImageButton btn_imgRes;
        Button btn_addProduct;
        edt_cateID = view.findViewById(R.id.edt_cateID);
        edt_productName = view.findViewById(R.id.edt_productName);
        edt_price = view.findViewById(R.id.edt_price);
        edt_Desc = view.findViewById(R.id.edt_Desc);
        btn_imgRes = view.findViewById(R.id.btn_imgRes);
        btn_addProduct = view.findViewById(R.id.btn_addProduct);

        btn_addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cate_ID = Integer.parseInt(edt_cateID.getText().toString());
                String productName = edt_productName.getText().toString();
                String productDesc = edt_Desc.getText().toString();
                float Price = Float.parseFloat(edt_price.getText().toString());
                int product_imgRes = R.drawable.product_tee1;
                product product = new product(cate_ID, productName, productDesc, Price, product_imgRes);
                ProductDao productDao = new ProductDao(getContext());
                long status = productDao.insert(product);
                if (status != 1){
                    Toast.makeText(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}