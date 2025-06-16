package com.example.app_ban_hang.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.adapter.adapter_wish;
import com.example.app_ban_hang.database.ProductDao;
import com.example.app_ban_hang.database.WishlistDAO;

import java.util.ArrayList;
import java.util.List;

public class Fragment_WishList extends Fragment {

    private RecyclerView recyclerView;
    private adapter_wish adapter;
    private List<product> productList;

    public Fragment_WishList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__wishlist, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadWishlistProducts();

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        loadWishlistProducts();
    }

    private void loadWishlistProducts() {
        Context context = getContext();
        if (context == null) return;

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);

        WishlistDAO wishlistDAO = new WishlistDAO(context);
        List<Integer> wishProductIds = wishlistDAO.getWishlistProductIds(userId);

        ProductDao productDao = new ProductDao(context);
        productList = new ArrayList<>();

        for (int id : wishProductIds) {
            product p = productDao.getById(String.valueOf(id));
            if (p != null) productList.add(p);
        }

        adapter = new adapter_wish(productList, context);
        recyclerView.setAdapter(adapter);

        // Xử lý vuốt để xóa
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                product removedProduct = productList.get(position);

                wishlistDAO.removeFromWishlist(userId, removedProduct.getProduct_id());

                // Xóa khỏi danh sách và cập nhật giao diện
                productList.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

}
