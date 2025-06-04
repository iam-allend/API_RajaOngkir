package com.example.api_rajaongkir.api;

import com.example.api_rajaongkir.model.ResponseKota;
import com.example.api_rajaongkir.model.ResponseOngkir;
import com.example.api_rajaongkir.model.ResponseProvinsi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RegisterAPI {
    @GET("get_province.php")
    Call<ResponseProvinsi> getProvinsi();

    @GET("get_city.php")
    Call<ResponseKota> getKota(@Query("province") String province_id);

    @FormUrlEncoded
    @POST("api_ongkir.php")
    Call<ResponseOngkir> getOngkir(
            @Field("kota_tujuan") String kota_tujuan,
            @Field("kurir") String kurir,
            @Field("berat") int berat
    );
}
