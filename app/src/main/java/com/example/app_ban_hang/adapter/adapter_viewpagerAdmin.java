package com.example.app_ban_hang.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.app_ban_hang.Fragment.Fragment_Admin_AddProduct;
import com.example.app_ban_hang.Fragment.Fragment_Admin_AllProduct;

public class adapter_viewpagerAdmin extends FragmentStateAdapter {
    public adapter_viewpagerAdmin(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Fragment();
            case 1:
                return new Fragment();
            case 2:
                return new Fragment();
            case 3:
                return new Fragment_Admin_AddProduct();
            case 4:
                return new Fragment_Admin_AllProduct();
            case 5:
                return new Fragment();
            case 6:
                return new Fragment();
            default:
                return new Fragment(); // fallback
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
