package com.example.app_ban_hang.API;


import com.example.app_ban_hang.Model.Province;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProvinceApi {
    @GET("api/p/")
    Call<List<Province>> getProvinces();
}