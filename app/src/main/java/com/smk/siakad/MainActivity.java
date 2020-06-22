package com.smk.siakad;

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
import android.widget.TextView;
import android.widget.Toast;

import com.smk.siakad.adapter.AdapterSiswa;

import com.smk.siakad.api.ApiClient;
import com.smk.siakad.api.ApiInterface;
import com.smk.siakad.model.Siswa;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView txtTest;
    private RecyclerView recyclerView;
    private AdapterSiswa adapterSiswa;
    private List<Siswa> siswa;
    private Button btnFilter;
    private String oke, text;
    ApiInterface apiInterface;
    AdapterSiswa.RecyclerViewClickListener listener;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.rcSiswa);
        btnFilter = findViewById(R.id.btnFilter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTest = findViewById(R.id.txtTest);
        oke = "Faisal";
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterSiswa.getFilter().filter(text);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this, R.array.semester, android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinner);
        spinner.setOnItemSelectedListener(this);
//        txtTest.setText(siswa.get(0).getNama());
    }

    public void getSiswa(){

        Call<List<Siswa>> call = apiInterface.getSiswa();
        call.enqueue(new Callback<List<Siswa>>() {
            @Override
            public void onResponse(Call<List<Siswa>> call, Response<List<Siswa>> response) {
                progressBar.setVisibility(View.GONE);
                siswa = response.body();
                Log.i(MainActivity.class.getSimpleName(), response.body().toString());
                adapterSiswa = new AdapterSiswa(siswa, MainActivity.this, listener);
                recyclerView.setAdapter(adapterSiswa);
                adapterSiswa.notifyDataSetChanged();
                System.out.println("okeh1 : "+siswa.get(0).getNama());
                System.out.println("okeh1 : "+siswa.get(1).getNama());
            }

            @Override
            public void onFailure(Call<List<Siswa>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSiswa();
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
//        recyclerView.setVisibility(View.VISIBLE);
//        adapterSiswa.getFilter().filter(text);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
