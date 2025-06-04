package com.example.api_rajaongkir.model;

public class Provinsi {
    private String province_id;
    private String province;

    public String getProvinceId() {
        return province_id;
    }

    public String getProvince() {
        return province;
    }

    @Override
    public String toString() {
        return province;
    }
}

