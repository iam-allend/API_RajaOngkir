package com.example.api_rajaongkir.model;

public class Kota {
    private String city_id;
    private String city_name;

    public String getCityId() {
        return city_id;
    }

    public String getCityName() {
        return city_name;
    }

    @Override
    public String toString() {
        return city_name;
    }
}
