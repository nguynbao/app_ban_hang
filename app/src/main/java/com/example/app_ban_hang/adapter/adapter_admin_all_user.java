package com.example.app_ban_hang.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.Model.users;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.UserDAO;
import com.example.app_ban_hang.pages_admin.page_admin_Edit_user;

import java.util.List;

public class adapter_admin_all_user extends RecyclerView.Adapter<adapter_admin_all_user.ViewHolder>{
    List<users> usersList;

    public adapter_admin_all_user(List<users> usersList ){
        this.usersList = usersList;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView user_name_admin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name_admin = itemView.findViewById(R.id.user_name_admin);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_all_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        users user = usersList.get(position);
        holder.user_name_admin.setText(user.getUser_name());
        holder.user_name_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), page_admin_Edit_user.class);
                intent.putExtra("user_id", usersList.get(position).getUser_id());
                Log.d("user_id", String.valueOf(user.getUser_id()));
                view.getContext().startActivity(intent);
            }
        }
);  }
        @Override
    public int getItemCount() {
        return usersList.size();
    }
}
