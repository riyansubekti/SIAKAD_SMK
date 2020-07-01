package com.smk.siakad.siswa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smk.siakad.R;
import com.smk.siakad.adapter.AdapterJadwalSiswa;
import com.smk.siakad.adapter.AdapterNilaiSiswa;
import com.smk.siakad.api.ApiClient;
import com.smk.siakad.api.ApiInterface;
import com.smk.siakad.login.LoginActivity;
import com.smk.siakad.model.JadwalSiswa;
import com.smk.siakad.model.Nilai;

import java.util.ArrayList;
import java.util.List;

public class NilaiActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private AdapterNilaiSiswa adapterNilaiSiswa;
    private List<Nilai> nilaiList;
    private RecyclerView recyclerView;
    private String search, username, role;
    private Button btnSearch;
    private Dialog dialog;
    private FloatingActionButton fabNilaiInsert;
    private EditText etMapel, etTugas, etUTS, etUAS, etKKM, etSemester;
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
        fabNilaiInsert = findViewById(R.id.fabNilaiInsert);

        role = LoginActivity.prefConfig.readRole();
        username = LoginActivity.prefConfig.readID();
        Intent intent = getIntent();
        if (role.equals("guru")) {
            username = intent.getStringExtra("username");
            listener = new AdapterNilaiSiswa.RecyclerViewClickListener() {
                @Override
                public void onRowClick(View view, int position) {
                    dialogNilai("update", position);
                }
            };
            fabNilaiInsert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogNilai("insert",0);
                }
            });
        }else {
            fabNilaiInsert.setVisibility(View.GONE);
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterNilaiSiswa.getFilter().filter(search);
            }
        });

        Spinner spinner = findViewById(R.id.spinnerNilaiSiswa);
        ArrayAdapter<CharSequence> adapterSpinnerNilai = ArrayAdapter.createFromResource(this, R.array.semester, android.R.layout.simple_spinner_item);
        adapterSpinnerNilai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinnerNilai);
        spinner.setOnItemSelectedListener(this);
    }

    private void dialogNilai(String key, int position) {
        dialog = new Dialog(NilaiActivity.this);
        dialog.setContentView(R.layout.dialog_nilai);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.show();

        TextView txtJudul = dialog.findViewById(R.id.txtNilaiJudul);
        etMapel = dialog.findViewById(R.id.etNamaMapel);
        etTugas = dialog.findViewById(R.id.etNilaiTugas);
        etUTS = dialog.findViewById(R.id.etNilaiUTS);
        etUAS = dialog.findViewById(R.id.etNilaiUAS);
        etKKM = dialog.findViewById(R.id.etNilaiKKM);
        etSemester = dialog.findViewById(R.id.etNilaiSemester);
        Button btnDelete = dialog.findViewById(R.id.btnDeleteNilai);
        Button btnSave = dialog.findViewById(R.id.btnSaveNilai);

        if (key.equals("update")) {
            final String id_nilai = nilaiList.get(position).getId_nilai();
            String mapel = nilaiList.get(position).getId_mapel();
            final String id_siswa = nilaiList.get(position).getId_siswa();
            String tugas = nilaiList.get(position).getTugas();
            String uts = nilaiList.get(position).getUts();
            String uas = nilaiList.get(position).getUas();
            String kkm = nilaiList.get(position).getKkm();
            String semester = nilaiList.get(position).getSemester();
            txtJudul.setText("EDIT NILAI SISWA "+id_siswa);
            etMapel.setText(mapel);
            etTugas.setText(tugas);
            etUTS.setText(uts);
            etUAS.setText(uas);
            etKKM.setText(kkm);
            etSemester.setText(semester);
            btnDelete.setVisibility(View.VISIBLE);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateNilai(id_nilai, id_siswa);
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteNilai("delete", id_nilai);
                }
            });
        }else if (key.equals("insert")) {
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    insertNilai();
                }
            });
        }else{
            Toast.makeText(this, "ERORR Tidak ada KEY", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateNilai(String id_nilai, String id_siswa) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        String key = "update";
        String id_mapel = etMapel.getText().toString();
        String tugas = etTugas.getText().toString();
        String uts = etUTS.getText().toString();
        String uas = etUAS.getText().toString();
        String kkm = etKKM.getText().toString();
        String semester = etSemester.getText().toString();

        Call<Nilai> call = apiInterface.updateNilai(key, id_nilai, id_mapel, id_siswa, tugas,
                uts, uas, kkm, semester);
        call.enqueue(new Callback<Nilai>() {
            @Override
            public void onResponse(Call<Nilai> call, Response<Nilai> response) {
                Log.i(NilaiActivity.class.getSimpleName(), response.toString());
                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    finish();
                    Toast.makeText(NilaiActivity.this, "Berhasil mengubah data Siswa", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NilaiActivity.this, message, Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Nilai> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(NilaiActivity.this, "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteNilai(String key, final String id_nilai) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting...");
        progressDialog.show();

        Call<Nilai> call = apiInterface.deleteNilai(key, id_nilai);
        call.enqueue(new Callback<Nilai>() {
            @Override
            public void onResponse(Call<Nilai> call, Response<Nilai> response) {
                Log.i(NilaiActivity.class.getSimpleName(), response.toString());
                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    finish();
                    Toast.makeText(NilaiActivity.this, "Berhasil menghapus data Nilai Siswa dengan ID : "+id_nilai, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NilaiActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Nilai> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(NilaiActivity.this, "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insertNilai() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        String key = "insert";
        String id_mapel = etMapel.getText().toString();
        String tugas = etTugas.getText().toString();
        String uts = etUTS.getText().toString();
        String uas = etUAS.getText().toString();
        String kkm = etKKM.getText().toString();
        String semester = etSemester.getText().toString();

        Call<Nilai> call = apiInterface.insertNilai(key, id_mapel, username, tugas, uts, uas, kkm, semester);
        call.enqueue(new Callback<Nilai>() {
            @Override
            public void onResponse(Call<Nilai> call, Response<Nilai> response) {
                Log.i(NilaiActivity.class.getSimpleName(), response.toString());
                String value = response.body().getValue();
                String message = response.body().getMassage();
                if (value.equals("1")){
                    finish();
                    Toast.makeText(NilaiActivity.this, "Berhasil menambahkan data Nilai", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NilaiActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Nilai> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(NilaiActivity.this, "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getNilai(){
        Call<List<Nilai>> call = apiInterface.getNilai(username);
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
