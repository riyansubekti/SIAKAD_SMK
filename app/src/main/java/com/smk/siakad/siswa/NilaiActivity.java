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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.smk.siakad.R;
import com.smk.siakad.adapter.AdapterNilaiSiswa;
import com.smk.siakad.api.ApiClient;
import com.smk.siakad.api.ApiInterface;
import com.smk.siakad.model.Nilai;

import java.util.List;

public class NilaiActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private AdapterNilaiSiswa adapterNilaiSiswa;
    private List<Nilai> nilaiList;
    private RecyclerView recyclerView;
    private String search;
    private Button btnSearch;
    ApiInterface apiInterface;
    AdapterNilaiSiswa.RecyclerViewClickListener listener;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nilai);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.rcNilai);
        btnSearch = findViewById(R.id.btnSearch);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (search.equals("2")) {
                    // Get All Data
                    getNilai();
                }
                adapterNilaiSiswa.getFilter().filter(search);
            }
        });

        Spinner spinner = findViewById(R.id.spinnerNilaiSiswa);
        ArrayAdapter<CharSequence> adapterSpinnerNilai = ArrayAdapter.createFromResource(this, R.array.semester, android.R.layout.simple_spinner_item);
        adapterSpinnerNilai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinnerNilai);
        spinner.setOnItemSelectedListener(this);
    }

    public void getNilai(){
        String id_siswa = "160101001";
        Call<List<Nilai>> call = apiInterface.getNilai(id_siswa);
        call.enqueue(new Callback<List<Nilai>>() {
            @Override
            public void onResponse(Call<List<Nilai>> call, Response<List<Nilai>> response) {
                progressBar.setVisibility(View.GONE);
                nilaiList = response.body();
                Log.i(NilaiActivity.class.getSimpleName(), response.body().toString());
                adapterNilaiSiswa = new AdapterNilaiSiswa(nilaiList, NilaiActivity.this, listener);
                recyclerView.setAdapter(adapterNilaiSiswa);
                adapterNilaiSiswa.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Nilai>> call, Throwable t) {
                Toast.makeText(NilaiActivity.this, "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNilai();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        search = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
