package com.example.api_rajaongkir.model;


import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ResponseProvinsi {

    @SerializedName("rajaongkir")
    private RajaOngkir rajaongkir;

    public RajaOngkir getRajaongkir() {
        return rajaongkir;
    }

    public class RajaOngkir {
        @SerializedName("results")
        private List<Provinsi> results;

        public List<Provinsi> getResults() {
            return results;
        }
    }
}

