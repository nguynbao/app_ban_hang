package com.example.app_ban_hang.Fragment;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_ban_hang.Model.categories;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.CategoryDao;

public class Fragment_Admin_AddCate extends Fragment {
    TextView name_cate;
    AppCompatButton add_cate;
    public Fragment_Admin_AddCate() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__admin__add_cate, container, false);
        name_cate = view.findViewById(R.id.Name_cate);
        add_cate = view.findViewById(R.id.add_cate);
        add_cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = name_cate.getText().toString();
                if (name.isEmpty()){
                    Toast.makeText(getContext(), "Vui lòng nhập tên danh mục", Toast.LENGTH_SHORT).show();
                    return;
                }
               else {
                    // Thêm danh mục

                    categories cate = new categories(name, R.drawable.product_tee1);
                    CategoryDao categoryDao = new CategoryDao(getContext());
                    long id  = categoryDao.insertCategory(cate);
                    cate.setCategory_id((int) id);
                    Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
                    name_cate.setText("");
                    Log.d("TAG", "onClick: "+categoryDao.getAllCategoryNames());
                }
            }
        });
        return view;
    }

}
