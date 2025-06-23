package com.example.app_ban_hang.pages_admin;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_ban_hang.Model.order;
import com.example.app_ban_hang.Model.orderItem;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.OrderDao;
import com.example.app_ban_hang.database.OrderItemDao;

import java.util.List;

public class page_admin_detail_order extends AppCompatActivity {

    private TextView order_customer, order_ID, order_quanty, order_city;
    private AppCompatButton order_acpt;
    int orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page_admin_detail_order);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các View
        order_customer = findViewById(R.id.order_customer);
        order_ID = findViewById(R.id.order_ID);
        order_quanty = findViewById(R.id.order_quanty);
        order_city = findViewById(R.id.order_city);
        order_acpt = findViewById(R.id.order_acpt);

        // Lấy order_id từ Intent
      orderId = getIntent().getIntExtra("order_id", -1);

        if (orderId != -1) {
            order_ID.setText("Mã đơn: " + orderId);
            OrderItemDao orderItemDao = new OrderItemDao(this);
            List<orderItem> itemList = orderItemDao.getOrderItemsByOrderId(orderId);
            if (!itemList.isEmpty()) {
                int totalQuantity = 0;
                String city = getIntent().getStringExtra("city");
                for (orderItem item : itemList) {
                    totalQuantity += item.getQuantity();

                }
                order_quanty.setText("Số lượng: " + totalQuantity);
                order_customer.setText("Khách hàng: " + itemList.get(0).getOrderId());
                order_city.setText(city);
                order_acpt.setOnClickListener(v -> {
                    acceptOrder(orderId);
                    finish();
                });


            } else {
                Toast.makeText(this, "Không có sản phẩm nào trong đơn hàng!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Không tìm thấy mã đơn hàng!", Toast.LENGTH_SHORT).show();
        }
}
    private void acceptOrder(int orderId) {
        OrderDao orderDao = new OrderDao(this);

        order currentOrder = orderDao.getOrderById(orderId);
        if (currentOrder == null) {
            Toast.makeText(this, "Đơn hàng không tồn tại!", Toast.LENGTH_SHORT).show();
            return;
        }

        if ("approved".equalsIgnoreCase(currentOrder.getStatus())) {
            Toast.makeText(this, "Đơn hàng đã được duyệt trước đó!", Toast.LENGTH_SHORT).show();
        } else {
            orderDao.approveOrder(orderId);
            Toast.makeText(this, "Đơn hàng đã được chấp nhận!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
