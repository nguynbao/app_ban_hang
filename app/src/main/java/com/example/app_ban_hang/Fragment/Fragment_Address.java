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
import com.example.app_ban_hang.Model.users;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.CartDao;
import com.example.app_ban_hang.database.OrderDao;
import com.example.app_ban_hang.database.OrderItemDao;
import com.example.app_ban_hang.database.ProductDao;
import com.example.app_ban_hang.database.UserDAO;
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
    EditText edtAddress;
    TextView edtPhone;
    Button btn_Order;
    String shippingAddress, phoneUser;
    boolean allInserted = true;

    // ProgressBars
    View progressBarCity, progressBarDistrict;

    public Fragment_Address() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__address, container, false);

        spinnerCity = view.findViewById(R.id.spinnerCity);
        spinnerDistrict = view.findViewById(R.id.spinnerDistrict);
        progressBarCity = view.findViewById(R.id.progressBar);
        progressBarDistrict = view.findViewById(R.id.progressBar2);
        btn_Order = view.findViewById(R.id.btn_Order);
        edtPhone = view.findViewById(R.id.edtPhone);
        edtAddress = view.findViewById(R.id.edtAddress);

        // Hiện Progress khi bắt đầu tải city
        progressBarCity.setVisibility(View.VISIBLE);

        ProvinceApi provinceApi = ApiClient.getClient().create(ProvinceApi.class);
        provinceApi.getProvinces().enqueue(new Callback<List<Province>>() {
            @Override
            public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                progressBarCity.setVisibility(View.GONE);
                List<Province> provinceList = response.body();
                if (provinceList != null) {
                    ArrayAdapter<Province> adapter = new ArrayAdapter<>(
                            getContext(), R.layout.spinner_selected_item, provinceList);
                    adapter.setDropDownViewResource(R.layout.item_spinner_custom);
                    spinnerCity.setAdapter(adapter);
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserSession", MODE_PRIVATE);
                    int userID = sharedPreferences.getInt("user_id", -1);
                    UserDAO userDAO1= new UserDAO(getContext());
                    users user1 = userDAO1.getUserById(userID);
                    String user_city = user1.getAddress();

                    int user_city_position = 0;
                    for (int a = 0; a < provinceList.size(); a++){
                        if (provinceList.get(a).getName().equals(user_city)){
                            user_city_position = a;
                            break;
                        }
                    }
                    Log.d("user_city", user_city + user_city_position);
                    spinnerCity.setSelection(user_city_position);
                    loadDistricts(provinceList.get(user_city_position).getCode(), spinnerDistrict);

                    spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Province selected = provinceList.get(i);
                            loadDistricts(selected.getCode(), spinnerDistrict);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {}
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Province>> call, Throwable t) {
                progressBarCity.setVisibility(View.GONE);
                Log.d("API", "onFailure called: " + t.getMessage());
                Toast.makeText(getContext(), "Tải danh sách tỉnh/thành thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        // Load thông tin user (phone)
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("UserSession", MODE_PRIVATE);
        int userID = sharedPreferences.getInt("user_id", -1);
        UserDAO userDAO = new UserDAO(getContext());
        users users = userDAO.getUserById(userID);
        edtPhone.setText(String.valueOf(users.getPhone()));

        // Xử lý khi bấm đặt hàng
        btn_Order.setOnClickListener(v -> {
            String detailAddress = edtAddress.getText().toString();
            String district = spinnerDistrict.getSelectedItem().toString();
            String city = spinnerCity.getSelectedItem().toString();
            shippingAddress = detailAddress + "," + district + "," + city;
            phoneUser = edtPhone.getText().toString();

            Log.d("shippingAddress", shippingAddress);

            if (!detailAddress.isEmpty() && !phoneUser.isEmpty()) {
                orderItem();
            } else {
                Toast.makeText(getContext(), "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void loadDistricts(int provinceCode, Spinner spinnerDistrict) {
        progressBarDistrict.setVisibility(View.VISIBLE);

        ProvinceApi provinceApi = ApiClient.getClient().create(ProvinceApi.class);
        provinceApi.getProvinceWithDistricts(provinceCode).enqueue(new Callback<ProvinceWithDistricts>() {
            @Override
            public void onResponse(Call<ProvinceWithDistricts> call, Response<ProvinceWithDistricts> response) {
                progressBarDistrict.setVisibility(View.GONE); // Kết thúc loading district
                if (response.isSuccessful() && response.body() != null) {
                    List<District> districts = response.body().getDistricts();
                    ArrayAdapter<District> adapter = new ArrayAdapter<>(getContext(),
                            R.layout.spinner_selected_item, districts);
                    adapter.setDropDownViewResource(R.layout.item_spinner_custom);
                    spinnerDistrict.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ProvinceWithDistricts> call, Throwable t) {
                progressBarDistrict.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Lỗi tải huyện", Toast.LENGTH_SHORT).show();
            }
        });
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
        order.setOrderDate(Calendar.getInstance().getTime());
        order.setPhone_no(phoneUser);

        OrderDao orderDao = new OrderDao(getContext());
        int orderID = Integer.parseInt(String.valueOf(orderDao.insertOrder(order)));

        if (orderID != -1) {
            for (int i : cartIDList) {
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
                if (result == -1) {
                    allInserted = false;
                    break;
                }
            }

            if (allInserted) {
                Toast.makeText(getContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                CartDao cartDao1 = new CartDao(getContext());
                for (int i : cartIDList) {
                    cartDao1.deletedCartByIdCart(i);
                }
                startActivity(new Intent(getContext(), page_home_activity.class));
                getActivity().finish();
            } else {
                Toast.makeText(getContext(), "Đặt hàng thất bại ở một sản phẩm", Toast.LENGTH_SHORT).show();
            }
        }
    }
}