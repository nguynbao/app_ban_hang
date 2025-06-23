package com.example.app_ban_hang.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.util.Log;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class Fragment_Admin_AddProduct extends Fragment {
    private static final int REQUEST_IMAGE_PICK = 1;
    private static final int REQUEST_PERMISSION = 100;
    private static final String TAG = "Fragment_Admin_AddProduct";

    private String selectedImagePath; // Changed to store file path instead of Uri
    private ImageView btn_imgRes;
    private EditText edt_productName, edt_price, edt_Desc;
    private Spinner spinner_cateID;

    public Fragment_Admin_AddProduct() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__admin__add_product, container, false);
        edt_productName = view.findViewById(R.id.edt_productName);
        edt_price = view.findViewById(R.id.edt_price);
        edt_Desc = view.findViewById(R.id.edt_Desc);
        spinner_cateID = view.findViewById(R.id.edt_cateID);
        btn_imgRes = view.findViewById(R.id.btn_imgRes);
        Button btn_addProduct = view.findViewById(R.id.btn_addProduct);

        // Initial load categories
        refreshCategorySpinner();

        // Image picker click listener
        btn_imgRes.setOnClickListener(v -> checkPermissionAndOpenImagePicker());

        // Add product click listener
        btn_addProduct.setOnClickListener(v -> {
            int selectedPosition = spinner_cateID.getSelectedItemPosition();
            CategoryDao categoryDao = new CategoryDao(getContext());
            List<categories> allCategories = categoryDao.getAllCategories();
            if (selectedPosition < 0 || selectedPosition >= allCategories.size()) {
                Toast.makeText(getContext(), "Vui lòng chọn danh mục hợp lệ!", Toast.LENGTH_SHORT).show();
                return;
            }
            int cateID = allCategories.get(selectedPosition).getCategory_id();
            String productName = edt_productName.getText().toString().trim();
            String productDesc = edt_Desc.getText().toString().trim();
            String priceText = edt_price.getText().toString().trim();

            if (productName.isEmpty() || productDesc.isEmpty() || priceText.isEmpty() || selectedImagePath == null) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin sản phẩm!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                float price = Float.parseFloat(priceText);
                product newProduct = new product(cateID, productName, productDesc, price, selectedImagePath);
                ProductDao productDao = new ProductDao(getContext());
                long status = productDao.insert(newProduct);
                if (status != -1) {
                    Toast.makeText(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                    resetForm();
                } else {
                    Toast.makeText(getContext(), "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Giá sản phẩm không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh categories when returning to this fragment
        refreshCategorySpinner();
    }

    private void refreshCategorySpinner() {
        if (getContext() != null) {
            int previousSelection = spinner_cateID.getSelectedItemPosition(); // Lưu vị trí trước

            CategoryDao categoryDao = new CategoryDao(getContext());
            List<String> categoryNames = categoryDao.getAllCategoryNames();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    categoryNames
            );
            adapter.setDropDownViewResource(R.layout.item_spinner_custom);
            spinner_cateID.setAdapter(adapter);

            // Set lại vị trí đã chọn trước đó (nếu hợp lệ)
            if (previousSelection >= 0 && previousSelection < categoryNames.size()) {
                spinner_cateID.setSelection(previousSelection);
            }
        }
    }


    private void resetForm() {
        edt_productName.setText("");
        edt_Desc.setText("");
        edt_price.setText("");
        btn_imgRes.setImageDrawable(null);
        selectedImagePath = null;
    }

    private void checkPermissionAndOpenImagePicker() {
        String permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            permission = Manifest.permission.READ_MEDIA_IMAGES;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // Android 10-12
            permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        } else { // Android 9 and below
            permission = Manifest.permission.READ_EXTERNAL_STORAGE;
        }

        if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{permission}, REQUEST_PERMISSION);
        } else {
            openImagePicker();
        }
    }

    private void openImagePicker() {
        Intent intent;
        // Prefer ACTION_GET_CONTENT for modern Android, fallback to ACTION_PICK for older versions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // Android 4.4+
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        } else {
            intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
        }
        // Add read permission flag (no persistable permission needed since we copy to internal storage)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivityForResult(Intent.createChooser(intent, "Chọn hình ảnh"), REQUEST_IMAGE_PICK);
        } catch (Exception e) {
            Log.e(TAG, "Failed to open image picker: " + e.getMessage());
            Toast.makeText(getContext(), "Không thể mở thư viện hình ảnh!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == getActivity().RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                try {
                    // Copy image to internal storage
                    String fileName = "product_image_" + System.currentTimeMillis() + ".jpg";
                    File outputFile = new File(requireContext().getFilesDir(), fileName);
                    try (InputStream in = requireContext().getContentResolver().openInputStream(imageUri);
                         FileOutputStream out = new FileOutputStream(outputFile)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = in.read(buffer)) != -1) {
                            out.write(buffer, 0, len);
                        }
                    }
                    selectedImagePath = outputFile.getAbsolutePath();
                    // Display the image using the Uri for preview
                    btn_imgRes.setImageURI(imageUri);
                    Log.d(TAG, "Image copied to internal storage: " + selectedImagePath);
                } catch (Exception e) {
                    Log.e(TAG, "Failed to copy image: " + e.getMessage());
                    Toast.makeText(getContext(), "Không thể xử lý hình ảnh!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.w(TAG, "No image Uri returned from picker");
                Toast.makeText(getContext(), "Không thể chọn hình ảnh!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.w(TAG, "Image picker result: requestCode=" + requestCode + ", resultCode=" + resultCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openImagePicker();
        } else {
            Toast.makeText(getContext(), "Quyền truy cập bị từ chối, không thể chọn hình ảnh!", Toast.LENGTH_SHORT).show();
        }
    }
}