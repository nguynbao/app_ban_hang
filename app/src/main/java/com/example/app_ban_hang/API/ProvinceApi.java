package com.example.app_ban_hang.API;


import com.example.app_ban_hang.Model.Province;
import com.example.app_ban_hang.Model.ProvinceWithDistricts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProvinceApi {
    @GET("api/p/")
    Call<List<Province>> getProvinces();
    @GET("api/p/{code}?depth=2")
    Call<ProvinceWithDistricts> getProvinceWithDistricts(@Path("code") int code);
}