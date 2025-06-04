    package com.example.api_rajaongkir;

    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.*;
    import androidx.appcompat.app.AppCompatActivity;

    import com.example.api_rajaongkir.api.RegisterAPI;
    import com.example.api_rajaongkir.api.ServerAPI;
    import com.example.api_rajaongkir.model.Kota;
    import com.example.api_rajaongkir.model.Provinsi;
    import com.example.api_rajaongkir.model.ResponseKota;
    import com.example.api_rajaongkir.model.ResponseOngkir;
    import com.example.api_rajaongkir.model.ResponseProvinsi;

    import java.util.List;

    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;

    public class MainActivity extends AppCompatActivity {

        Spinner spinProvinsi, spinKota;
        Button btnCekOngkir;
        TextView tvOngkir, tvLamaKirim;

        List<Provinsi> listProvinsi;
        List<Kota> listKota;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            spinProvinsi = findViewById(R.id.spinProvinsi);
            spinKota = findViewById(R.id.spinKota);
            btnCekOngkir = findViewById(R.id.btnCekOngkir);
            tvOngkir = findViewById(R.id.tvOngkir);
            tvLamaKirim = findViewById(R.id.tvLamaKirim);

            getProvinsi();

            spinProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Provinsi selectedProv = (Provinsi) spinProvinsi.getSelectedItem();
                    getKota(selectedProv.getProvinceId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            btnCekOngkir.setOnClickListener(v -> {
                Kota kotaTujuan = (Kota) spinKota.getSelectedItem();
                getOngkir(kotaTujuan.getCityId(), "jne", 1000); // contoh berat 1000 gram
            });
        }

        private void getProvinsi() {
            RegisterAPI api = ServerAPI.getClient().create(RegisterAPI.class);
            Call<ResponseProvinsi> call = api.getProvinsi();
            call.enqueue(new Callback<ResponseProvinsi>() {
                @Override
                public void onResponse(Call<ResponseProvinsi> call, Response<ResponseProvinsi> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Provinsi> listProvinsi = response.body().getRajaongkir().getResults();

                        Log.d("API_PROVINSI", "Jumlah provinsi: " + listProvinsi.size());

                        if (!listProvinsi.isEmpty()) {
                            ArrayAdapter<Provinsi> adapter = new ArrayAdapter<>(MainActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item, listProvinsi);
                            spinProvinsi.setAdapter(adapter);
                        } else {
                            Toast.makeText(MainActivity.this, "Data provinsi kosong!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Response tidak sukses", Toast.LENGTH_SHORT).show();
                        Log.e("API_PROVINSI", "Status code: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ResponseProvinsi> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Gagal ambil data provinsi", Toast.LENGTH_SHORT).show();
                    Log.e("API_PROVINSI", "Error: " + t.getMessage());
                }
            });
        }

        private void getKota(String provinceId) {
            RegisterAPI api = ServerAPI.getClient().create(RegisterAPI.class);
            Call<ResponseKota> call = api.getKota(provinceId);
            call.enqueue(new Callback<ResponseKota>() {
                @Override
                public void onResponse(Call<ResponseKota> call, Response<ResponseKota> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Kota> listKota = response.body().getRajaongkir().getResults();

                        Log.d("API_KOTA", "Jumlah kota: " + listKota.size());

                        if (!listKota.isEmpty()) {
                            ArrayAdapter<Kota> adapter = new ArrayAdapter<>(MainActivity.this,
                                    android.R.layout.simple_spinner_dropdown_item, listKota);
                            spinKota.setAdapter(adapter);
                        } else {
                            Toast.makeText(MainActivity.this, "Data kota kosong!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Response kota tidak sukses", Toast.LENGTH_SHORT).show();
                        Log.e("API_KOTA", "Status code: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ResponseKota> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Gagal ambil data kota", Toast.LENGTH_SHORT).show();
                    Log.e("API_KOTA", "Error: " + t.getMessage());
                }
            });
        }


        private void getOngkir(String cityId, String kurir, int berat) {
            RegisterAPI api = ServerAPI.getClient().create(RegisterAPI.class);
            Call<ResponseOngkir> call = api.getOngkir(cityId, kurir, berat);
            call.enqueue(new Callback<ResponseOngkir>() {
                @Override
                public void onResponse(Call<ResponseOngkir> call, Response<ResponseOngkir> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<ResponseOngkir.Result> results = response.body().getRajaongkir().getResults();

                        if (results != null && !results.isEmpty()) {
                            List<ResponseOngkir.Cost> costs = results.get(0).getCosts();
                            if (costs != null && !costs.isEmpty()) {
                                List<ResponseOngkir.Detail> detailList = costs.get(0).getCost();
                                if (detailList != null && !detailList.isEmpty()) {
                                    ResponseOngkir.Detail detail = detailList.get(0);
                                    tvOngkir.setText("Rp " + detail.getValue());
                                    tvLamaKirim.setText(detail.getEtd() + " hari");
                                } else {
                                    tvOngkir.setText("Detail ongkir kosong");
                                    tvLamaKirim.setText("-");
                                }
                            } else {
                                tvOngkir.setText("Cost ongkir kosong");
                                tvLamaKirim.setText("-");
                            }
                        } else {
                            tvOngkir.setText("Hasil ongkir kosong");
                            tvLamaKirim.setText("-");
                        }
                    } else {
                        tvOngkir.setText("Response gagal");
                        tvLamaKirim.setText("-");
                        Log.e("ONGKIR", "Status code: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ResponseOngkir> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Gagal mengambil ongkir", Toast.LENGTH_SHORT).show();
                    Log.e("ONGKIR", "Error: " + t.getMessage());
                }
            });
        }

    }
