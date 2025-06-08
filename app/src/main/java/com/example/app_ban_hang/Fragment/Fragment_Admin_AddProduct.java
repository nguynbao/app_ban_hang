package com.example.app_ban_hang.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.app_ban_hang.Model.categories;
import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.CategoryDao;
import com.example.app_ban_hang.database.ProductDao;

import java.util.Collections;
import java.util.List;

public class Fragment_Admin_AddProduct extends Fragment {
    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int REQUEST_PERMISSION = 100;
    private Uri selectedImageUri;
    ImageView btn_imgRes;

    public Fragment_Admin_AddProduct() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__admin__add_product, container, false);
        EditText  edt_productName, edt_price, edt_Desc;
        Spinner spinner_cateID;
        List<categories> categoriesList ;
        Button btn_addProduct;
        spinner_cateID = view.findViewById(R.id.edt_cateID);

        // Lấy danh sách tên danh mục
        CategoryDao categoryDao = new CategoryDao(getContext());
        List<String> categoryNames = categoryDao.getAllCategoryNames();
        // Tạo Adapter cho Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                categoryNames
        );
        adapter.setDropDownViewResource(R.layout.item_spinner);
        spinner_cateID.setAdapter(adapter);
        edt_productName = view.findViewById(R.id.edt_productName);
        edt_price = view.findViewById(R.id.edt_price);
        edt_Desc = view.findViewById(R.id.edt_Desc);
        btn_imgRes = view.findViewById(R.id.btn_imgRes);
        btn_imgRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Thực hiện hành động chọn ảnh ở đây
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    checkPermissionAndOpenImagePicker();
                }

            }
        });
        btn_addProduct = view.findViewById(R.id.btn_addProduct);

        btn_addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedPosition = spinner_cateID.getSelectedItemPosition();
                List<categories> allCategories = categoryDao.getAllCategories();
                int cateID = allCategories.get(selectedPosition).getCategory_id();
                String productName = edt_productName.getText().toString().trim();
                String productDesc = edt_Desc.getText().toString().trim();
                String priceText = edt_price.getText().toString().trim();

                if (productName.isEmpty() || productDesc.isEmpty() || priceText.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin sản phẩm!", Toast.LENGTH_SHORT).show();
                    return;
                }

                float Price = Float.parseFloat(priceText);
                Uri product_imgRes = selectedImageUri;
                product product = new product(cateID, productName, productDesc, Price, product_imgRes);
                ProductDao productDao = new ProductDao(getContext());
                long status = productDao.insert(product);
                if (status != 1){
                    Toast.makeText(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    private void checkPermissionAndOpenImagePicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_PERMISSION);
            } else {
                openImagePicker();
            }
        } else {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            } else {
                openImagePicker();
            }
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == getActivity().RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                btn_imgRes.setImageURI(selectedImageUri);  // Hiển thị ảnh lên ImageButton
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openImagePicker();
        } else {
            Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

}