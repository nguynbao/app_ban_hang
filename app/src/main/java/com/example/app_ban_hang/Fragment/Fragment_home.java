package com.example.app_ban_hang.Fragment;



import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.Model.categories;
import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.adapter.adapter_banner;
import com.example.app_ban_hang.adapter.adapter_categories;
import com.example.app_ban_hang.adapter.adapter_product;
import com.example.app_ban_hang.database.ProductDao;
import com.example.app_ban_hang.database.CategoryDao;


import java.util.Arrays;
import java.util.List;

public class Fragment_home extends Fragment {
    private RecyclerView recyclerViewBanner, recyclerViewCategory, recyclerViewProduct;
    private AppCompatButton searchButton;
    public Fragment_home() {
    }
    private ActivityResultLauncher<String> requestPermissionLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Khởi tạo launcher xin quyền
        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        // Được quyền, load dữ liệu hình ảnh ở đây hoặc gọi lại setupRecyclerViews()
                        setupRecyclerViews();
                    } else {
                        // Không được cấp quyền, thông báo hoặc xử lý khác
                        Toast.makeText(requireContext(), "Permission denied to read storage", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__home, container, false);
        recyclerViewBanner = view.findViewById(R.id.rcv1);
        recyclerViewCategory = view.findViewById(R.id.rcv2);
        recyclerViewProduct = view.findViewById(R.id.rcv3);
        searchButton = view.findViewById(R.id.search);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13+
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES)
                    == PackageManager.PERMISSION_GRANTED) {
                setupRecyclerViews();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
            }
        } else { // Android dưới 13
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                setupRecyclerViews();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }

        setupSearchButton();
        return view;
    }


    private void setupRecyclerViews() {
        // Banner
        List<Integer> banners = Arrays.asList(
                R.drawable.banner_two, R.drawable.banner_two, R.drawable.banner_two,
                R.drawable.banner_two, R.drawable.banner_two, R.drawable.banner_two
        );
        recyclerViewBanner.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewBanner.setAdapter(new adapter_banner(banners));

        // Categories
        CategoryDao categoriesDao = new CategoryDao(requireContext());
        List<categories> categories = categoriesDao.getAllCategories();
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory.setAdapter(new adapter_categories(categories));
        Log.d("CategoriesFragment", "Số danh mục: " + categories.size());


        // Products
        ProductDao ProductDao = new ProductDao(requireContext());
        List<product> products = ProductDao.getAll();
        if (products.size() > 5) {
            products = products.subList(0, 5);
        }
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewProduct.setAdapter(new adapter_product(products));
    }

    private void setupSearchButton() {
        searchButton.setOnClickListener(v -> {
            Fragment_Search fragmentSearch = new Fragment_Search();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, fragmentSearch)  // R.id.fragment_container là ID của FrameLayout trong MainActivity
                    .addToBackStack(null)
                    .commit();
        });
    }
}
