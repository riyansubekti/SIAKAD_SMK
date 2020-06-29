package com.smk.siakad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smk.siakad.adapter.AdapterSiswa;

import com.smk.siakad.api.ApiClient;
import com.smk.siakad.api.ApiInterface;
import com.smk.siakad.model.Siswa;
import com.smk.siakad.siswa.NilaiActivity;
import com.smk.siakad.siswa.ProfilSiswaActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterSiswa adapterSiswa;
    private List<Siswa> siswa;
    private FloatingActionButton fabInsert;
    private String menu;
    private TextView txtJudul;
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
        fabInsert = findViewById(R.id.fabInsert);
        txtJudul = findViewById(R.id.txtJudulDesk);

        Intent intent = getIntent();
        menu = intent.getStringExtra("menu");
        System.out.println("okeh "+menu);
        if (menu.equals("nilai")) {
            fabInsert.setVisibility(View.GONE);
            txtJudul.setText("Klik untuk mengubah data nilai siswa");
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listener = new AdapterSiswa.RecyclerViewClickListener() {
            @Override
            public void onRowClick(View view, int position) {
                if (menu.equals("nilai")) {
                    Intent intent = new Intent(MainActivity.this, NilaiActivity.class);
                    intent.putExtra("username", siswa.get(position).getId_siswa());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, ProfilSiswaActivity.class);
                    intent.putExtra("username", siswa.get(position).getId_siswa());
                    intent.putExtra("key", "update");
                    startActivity(intent);
                }
            }

            @Override
            public void onDeleteClick(View view, int position) {
                if (menu.equals("nilai")) {
                    Intent intent = new Intent(MainActivity.this, NilaiActivity.class);
                    intent.putExtra("username", siswa.get(position).getId_siswa());
                    Toast.makeText(MainActivity.this, "Klik data untuk menghapusnya...", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else{
                    String nis = siswa.get(position).getId_siswa();
                    String key = "delete";
                    String foto = siswa.get(position).getFoto();
                    deleteSiswa(key, nis, foto);
                }

            }
        };



        fabInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProfilSiswaActivity.class);
                intent.putExtra("key", "insert");
                startActivity(intent);
            }
        });

    }

    private void deleteSiswa(String key, final String nis, String foto) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting...");
        progressDialog.show();

        Call<Siswa> call = apiInterface.deleteSiswa(key, nis,foto);
        call.enqueue(new Callback<Siswa>() {
            @Override
            public void onResponse(Call<Siswa> call, Response<Siswa> response) {
                Log.i(ProfilSiswaActivity.class.getSimpleName(), response.toString());
                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    finish();
                    Toast.makeText(MainActivity.this, "Berhasil menghapus data Siswa dengan NIM : "+nis, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Siswa> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
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
    }
}
