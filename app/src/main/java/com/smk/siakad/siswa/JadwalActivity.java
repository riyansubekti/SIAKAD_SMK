package com.smk.siakad.siswa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.smk.siakad.R;
import com.smk.siakad.adapter.AdapterJadwalSiswa;
import com.smk.siakad.api.ApiClient;
import com.smk.siakad.api.ApiInterface;
import com.smk.siakad.model.JadwalSiswa;

import java.util.List;

public class JadwalActivity extends AppCompatActivity {
    private AdapterJadwalSiswa adapterJadwalSiswa;
    private List<JadwalSiswa> jadwalSiswaList;
    private RecyclerView recyclerView;
    private String hari, kelas;
    ApiInterface apiInterface;
    TextView oke, emptySenin;
    AdapterJadwalSiswa.RecyclerViewClickListener listener;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        progressBar = findViewById(R.id.progress1);
        recyclerView = findViewById(R.id.rcJadwalSiswa);
        oke = findViewById(R.id.txtJudul);
        emptySenin = findViewById(R.id.emptyRCSenin);
        hari = "Rabu";

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        getJadwalSiswa();

        oke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterJadwalSiswa.getFilter().filter(hari);
                adapterJadwalSiswa.notifyDataSetChanged();
            }
        });

//        adapterJadwalSiswa.getFilter().filter(hari);
//        adapterJadwalSiswa.notifyDataSetChanged();
//        if (adapterJadwalSiswa == null) {
//            Toast.makeText(this, "Gaada Hari", Toast.LENGTH_SHORT).show();
//        }
    }

    public void getJadwalSiswa(){
        kelas = "1-A";
        Call<List<JadwalSiswa>> call = apiInterface.getJadwalSiswa(kelas);
        call.enqueue(new Callback<List<JadwalSiswa>>() {
            @Override
            public void onResponse(Call<List<JadwalSiswa>> call, Response<List<JadwalSiswa>> response) {
                progressBar.setVisibility(View.GONE);
                jadwalSiswaList = response.body();
                Log.i(JadwalActivity.class.getSimpleName(), response.body().toString());
                adapterJadwalSiswa = new AdapterJadwalSiswa(jadwalSiswaList, JadwalActivity.this, listener);
                recyclerView.setAdapter(adapterJadwalSiswa);
                adapterJadwalSiswa.getFilter().filter("Selasa");
                adapterJadwalSiswa.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<JadwalSiswa>> call, Throwable t) {
                Toast.makeText(JadwalActivity.this, "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }



}