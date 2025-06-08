package com.example.app_ban_hang.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.Model.categories;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.CategoryDao;
import com.example.app_ban_hang.pages_admin.page_admin_Edit_cate;

import java.util.List;

public class adapter_admin_AllCate extends RecyclerView.Adapter<adapter_admin_AllCate.ViewHolder> {
    List<categories> categoriesList;
    public adapter_admin_AllCate(List<categories> categoriesList) {
        this.categoriesList = categoriesList;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_cate;
        AppCompatButton edit_allCate, btn_delte;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_cate = itemView.findViewById(R.id.Name_ALLcate);
            edit_allCate = itemView.findViewById(R.id.edit_allCate);
            btn_delte = itemView.findViewById(R.id.delete_allCate);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_all_cate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_admin_AllCate.ViewHolder holder, int position) {
        categories cate = categoriesList.get(position);
        holder.name_cate.setText(cate.getCategory_name());
        holder.edit_allCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), page_admin_Edit_cate.class);
                intent.putExtra("category_id", cate.getCategory_id());
                view.getContext().startActivity(intent);
                Toast.makeText(view.getContext(), "Edit", Toast.LENGTH_SHORT).show();
            }
            });
        holder.btn_delte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoryDao categoryDao = new CategoryDao(view.getContext());
                categoryDao.deleteCategory(cate.getCategory_id());
                categoriesList.remove(position);
                notifyItemRemoved(position); // Cập nhật adapter
                notifyItemRangeChanged(position, categoriesList.size());
                Toast.makeText(view.getContext(), "Delete", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }
}
