package com.example.app_ban_hang.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.adapter.adapter_all_product;
import com.example.app_ban_hang.database.ProductDao;
import com.example.app_ban_hang.pages.page_detail_activity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Fragment_Search extends Fragment {
    private AutoCompleteTextView search;
    private ProductDao productDao;
    private GridView all_product;
    private adapter_all_product adapter;
    private List<product> productList;

    public Fragment_Search() {
        // Constructor rỗng
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__search, container, false);

        // Ánh xạ view
        search = view.findViewById(R.id.search);
        all_product = view.findViewById(R.id.all_product);

        // Khởi tạo DAO và lấy dữ liệu
        productDao = new ProductDao(getContext());
        productList = productDao.getAll();

        // Danh sách tên sản phẩm để gợi ý tìm kiếm
        List<String> productNames = productList.stream()
                .map(product::getProduct_name)
                .collect(Collectors.toList());

        // Gợi ý tìm kiếm
        ArrayAdapter<String> searchAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_dropdown_item_1line,
                productNames
        );
        search.setAdapter(searchAdapter);
        search.setThreshold(1); // Gợi ý sau 1 ký tự

        // Hiển thị toàn bộ sản phẩm ban đầu
        adapter = new adapter_all_product(getContext(), productList);
        all_product.setAdapter(adapter);

        // Xử lý khi người dùng chọn từ gợi ý
        search.setOnItemClickListener((parent, view12, position, id) -> {
            String selectedName = (String) parent.getItemAtPosition(position);
            filterProductByName(selectedName);
        });

        // Xử lý khi người dùng gõ rồi nhấn Enter
        search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                String inputText = search.getText().toString().trim();
                filterProductByName(inputText);
                return true;
            }
            return false;
        });

        // Khi xóa nội dung tìm kiếm, hiển thị lại toàn bộ sản phẩm
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty()) {
                    adapter = new adapter_all_product(getContext(), productList);
                    all_product.setAdapter(adapter);
                }
            }
        });

        // Nhấn vào sản phẩm để xem chi tiết
        all_product.setOnItemClickListener((parent, view1, position, id) -> {
            product selectedProduct = (product) adapter.getItem(position);
            if (selectedProduct != null) {
                Intent intent = new Intent(getContext(), page_detail_activity.class);
                intent.putExtra("intID", selectedProduct.getProduct_id());
                startActivity(intent);
            }
        });

        return view;
    }

    // Hàm lọc sản phẩm theo tên và cập nhật giao diện
    private void filterProductByName(String name) {
        List<product> filteredList = productList.stream()
                .filter(p -> p.getProduct_name().equalsIgnoreCase(name))
                .collect(Collectors.toList());

        if (filteredList.isEmpty()) {
            filteredList = new ArrayList<>(); // Tránh lỗi nếu không tìm thấy
        }

        adapter = new adapter_all_product(getContext(), filteredList);
        all_product.setAdapter(adapter);
    }
}
