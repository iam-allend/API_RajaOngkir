package com.example.api_rajaongkir.model;


import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ResponseKota {

    @SerializedName("rajaongkir")
    private RajaOngkir rajaongkir;

    public RajaOngkir getRajaongkir() {
        return rajaongkir;
    }

    public class RajaOngkir {
        @SerializedName("results")
        private List<Kota> results;

        public List<Kota> getResults() {
            return results;
        }
    }
}

