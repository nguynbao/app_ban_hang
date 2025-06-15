package com.example.app_ban_hang.Fragment;

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
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.database.CartDao;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Address extends Fragment {
    Spinner spinnerDistrict, spinnerCity;
    EditText edtPhone, edtAddress;
    Button btn_Order;

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
        CartDao cartDao = new CartDao(getContext());
        List<CartItem> cartItemList = new ArrayList<>();
        if (getArguments() != null) {
            ArrayList<Integer> cartIDList = getArguments().getIntegerArrayList("cartIDList");
            for (int i : cartIDList){
                CartItem cartItem = cartDao.getByCartId(String.valueOf(i));
                Log.d("cartIDList", String.valueOf(i));
                cartItemList.add(cartItem);
            }
        }


        return view;
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