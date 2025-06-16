package com.example.app_ban_hang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_ban_hang.Model.order;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.OrderDao;

import java.util.List;

public class adapter_admin_All_order extends RecyclerView.Adapter<adapter_admin_All_order.ViewHolder> {
    List<order> orderList;
    public adapter_admin_All_order( List<order> orderList) {
        this.orderList = orderList;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView order_name_admin;
        AppCompatButton acpt_order;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            order_name_admin = itemView.findViewById(R.id.order_name_admin);
            acpt_order = itemView.findViewById(R.id.acpt_order);
        }
    }

    @NonNull
    @Override
    public adapter_admin_All_order.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_all_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter_admin_All_order.ViewHolder holder, int position) {
        order order = orderList.get(position);
        holder.order_name_admin.setText("Order ID: " + order.getOrderId());
        if ("Approved".equalsIgnoreCase(order.getStatus())) {
            holder.acpt_order.setBackgroundResource(R.drawable.btn_round_green);
            holder.acpt_order.setEnabled(false);
        } else {
            holder.acpt_order.setBackgroundResource(R.drawable.btn_round_gray);
            holder.acpt_order.setEnabled(true);
        }
        holder.acpt_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDao orderDao = new OrderDao(view.getContext());
                int result = orderDao.approveOrder(order.getOrderId());
                if (result > 0) {
                    Toast.makeText(view.getContext(), "Đã duyệt đơn hàng!", Toast.LENGTH_SHORT).show();
                    order.setStatus("Approved");
                    holder.acpt_order.setBackgroundResource(R.drawable.btn_round_green);
                    holder.acpt_order.setEnabled(false);
                } else {
                    Toast.makeText(view.getContext(), "Duyệt đơn thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
