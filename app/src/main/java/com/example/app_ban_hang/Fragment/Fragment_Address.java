package com.example.app_ban_hang.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_ban_hang.API.ApiClient;
import com.example.app_ban_hang.API.ProvinceApi;
import com.example.app_ban_hang.Model.CartItem;
import com.example.app_ban_hang.Model.District;
import com.example.app_ban_hang.Model.Province;
import com.example.app_ban_hang.Model.ProvinceWithDistricts;
import com.example.app_ban_hang.Model.order;
import com.example.app_ban_hang.Model.orderItem;
import com.example.app_ban_hang.Model.product;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.CartDao;
import com.example.app_ban_hang.database.OrderDao;
import com.example.app_ban_hang.database.OrderItemDao;
import com.example.app_ban_hang.database.ProductDao;
import com.example.app_ban_hang.pages.page_home_activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Address extends Fragment {
    Spinner spinnerDistrict, spinnerCity;
    EditText edtPhone, edtAddress;
    Button btn_Order;
    String shippingAddress, phoneUser;

    public Fragment_Address() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__address, container, false);
        spinnerCity = view.findViewById(R.id.spinnerCity);
        spinnerDistrict = view.findViewById(R.id.spinnerDistrict);
        ProvinceApi provinceApi = ApiClient.getClient().create(ProvinceApi.class);
        provinceApi.getProvinces().enqueue(new Callback<List<Province>>() {
            @Override
            public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                List<Province> provinceList = response.body();
                ArrayAdapter<Province> adapter = new ArrayAdapter<>(
                        getContext(),
                        android.R.layout.simple_spinner_item,
                        provinceList
                ){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        TextView tv = (TextView) view.findViewById(android.R.id.text1);

                        // Đổi màu chữ item đang hiển thị trên Spinner
                        tv.setTextColor(Color.parseColor("#000000")); // màu cam hoặc bạn chọn màu khác

                        return view;
                    }
                };
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCity.setAdapter(adapter);
                spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Province selected = provinceList.get(i);
                        loadDistricts(selected.getCode(), spinnerDistrict);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Province>> call, Throwable t) {
                Log.d("API", "onFailure called: " + t.getMessage());
            }
        });
        btn_Order = view.findViewById(R.id.btn_Order);
        edtPhone = view.findViewById(R.id.edtPhone);
        edtAddress = view.findViewById(R.id.edtAddress);
        btn_Order.setOnClickListener(v -> {
            String detailAddress = edtAddress.getText().toString();
            String district = spinnerDistrict.getSelectedItem().toString();
            String city = spinnerCity.getSelectedItem().toString();
            shippingAddress = detailAddress + "," + district + "," + city;
            phoneUser = edtPhone.getText().toString();
            Log.d("shippingAddress", shippingAddress);
            if (!detailAddress.isEmpty() && !phoneUser.isEmpty()){
                orderItem();
            }else {
                Toast.makeText(getContext(), "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
            }

        });
        return view;
    }

    private void orderItem() {
        CartDao cartDao = new CartDao(getContext());
        ArrayList<Integer> cartIDList = new ArrayList<>();
        List<CartItem> cartItemList = new ArrayList<>();
        Float totalAll = 0f;
        if (getArguments() != null) {
            cartIDList = getArguments().getIntegerArrayList("cartIDList");
            totalAll = getArguments().getFloat("totalAll");
            Log.d("totalAll", totalAll.toString());
        }
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserSession", MODE_PRIVATE);
        int userID = sharedPreferences.getInt("user_id", -1);
        order order = new order();
        order.setUserId(userID);
        order.setShippingAddress(shippingAddress);
        order.setTotalAmount(totalAll);
        Date now = Calendar.getInstance().getTime();
        order.setOrderDate(now);
        order.setPhone_no(phoneUser);
        OrderDao orderDao = new OrderDao(getContext());
        int orderID = Integer.parseInt(String.valueOf(orderDao.insertOrder(order)));
        if (orderID != -1){
            for (int i : cartIDList){
                CartItem cartItem = cartDao.getByCartId(String.valueOf(i));
                int productID = cartItem.getProduct_id();
                int quantity = cartItem.getQuantity();
                ProductDao productDao = new ProductDao(getContext());
                product product = productDao.getById(String.valueOf(productID));
                orderItem orderItem = new orderItem();
                orderItem.setOrderId(orderID);
                orderItem.setProductId(productID);
                orderItem.setQuantity(quantity);
                orderItem.setUnitPrice(product.getProduct_price());
                orderItem.setSubtotal(quantity * product.getProduct_price());
                Log.d("setSubtotal", String.valueOf(orderItem.getSubtotal()));
                OrderItemDao orderItemDao = new OrderItemDao(getContext());
                long result = orderItemDao.insertOrderItem(orderItem);
                if (result != -1){
                    Toast.makeText(getContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                    List<orderItem> orderItemList = orderItemDao.getAll();
                    for (orderItem orderItem1 : orderItemList){
                        Log.d("orderItem1", String.valueOf(orderItem1.getOrderItemId()));
                        Log.d("orderItem1", String.valueOf(orderItem1.getOrderId()));
                        Log.d("orderItem1", String.valueOf(orderItem1.getProductId()));
                        Log.d("orderItem1", String.valueOf(orderItem1.getQuantity()));
                        Log.d("orderItem1", String.valueOf(orderItem1.getUnitPrice()));
                        Log.d("orderItem1", String.valueOf(orderItem1.getSubtotal()));

                    }
                    Intent intent = new Intent(getContext(), page_home_activity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getContext(), "Đặt hàng thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    private void loadDistricts(int provinceCode, Spinner spinnerDistrict) {
        ProvinceApi provinceApi = ApiClient.getClient().create(ProvinceApi.class);
        provinceApi.getProvinceWithDistricts(provinceCode).enqueue(new Callback<ProvinceWithDistricts>() {
            @Override
            public void onResponse(Call<ProvinceWithDistricts> call, Response<ProvinceWithDistricts> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<District> districts = response.body().getDistricts();
                    ArrayAdapter<District> adapter = new ArrayAdapter<>(getContext(),
                            android.R.layout.simple_spinner_item, districts){
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            TextView tv = (TextView) view.findViewById(android.R.id.text1);

                            // Đổi màu chữ item đang hiển thị trên Spinner
                            tv.setTextColor(Color.parseColor("#000000")); // màu cam hoặc bạn chọn màu khác

                            return view;
                        }
                    };
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDistrict.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ProvinceWithDistricts> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi tải huyện", Toast.LENGTH_SHORT).show();
            }
        });
    }
    }