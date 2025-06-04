package com.example.api_rajaongkir.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ResponseOngkir {

    @SerializedName("rajaongkir")
    private RajaOngkir rajaongkir;

    public RajaOngkir getRajaongkir() {
        return rajaongkir;
    }

    public static class RajaOngkir {
        @SerializedName("results")
        private List<Result> results;

        public List<Result> getResults() {
            return results;
        }
    }

    public static class Result {
        @SerializedName("costs")
        private List<Cost> costs;

        public List<Cost> getCosts() {
            return costs;
        }
    }

    public static class Cost {
        @SerializedName("cost")
        private List<Detail> cost;

        public List<Detail> getCost() {
            return cost;
        }
    }

    public static class Detail {
        @SerializedName("value")
        private int value;

        @SerializedName("etd")
        private String etd;

        public int getValue() {
            return value;
        }

        public String getEtd() {
            return etd;
        }
    }

}
