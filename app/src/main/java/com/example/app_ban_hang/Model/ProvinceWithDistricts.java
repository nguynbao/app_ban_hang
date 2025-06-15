package com.example.app_ban_hang.Model;

import java.util.List;

public class ProvinceWithDistricts {
    private int code;
    private String name;
    private List<District> districts;

    public List<District> getDistricts() {
        return districts;
    }
}
