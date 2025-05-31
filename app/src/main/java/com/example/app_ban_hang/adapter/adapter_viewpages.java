package com.example.app_ban_hang.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.app_ban_hang.Fragment.Fragment_Account;
import com.example.app_ban_hang.Fragment.Fragment_Categories;
import com.example.app_ban_hang.Fragment.Fragment_Search;
import com.example.app_ban_hang.Fragment.Fragment_WishList;
import com.example.app_ban_hang.Fragment.Fragment_home;

public class adapter_viewpages extends FragmentStateAdapter {
    public adapter_viewpages(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Fragment_home();
            case 1:
                return new Fragment_Categories();
            case 2:
                return new Fragment_Search();
            case 3:
                return new Fragment_WishList();
            case 4:
                return new Fragment_Account();
            default:
                return new Fragment_home();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
