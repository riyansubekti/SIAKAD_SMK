package com.smk.siakad;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.gson.internal.$Gson$Preconditions;
import com.smk.siakad.api.ApiClient;
import com.smk.siakad.api.ApiInterface;
import com.smk.siakad.login.LoginActivity;
import com.smk.siakad.model.JadwalSiswa;
import com.smk.siakad.siswa.ProfilSiswaActivity;
import com.smk.siakad.ui.tabs.SectionsPagerAdapter;

public class JadwalSiswaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String kelas, id_guru, role;
    private EditText etMapel, etJurusan, etWaktu, etHari;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_siswa);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        role = LoginActivity.prefConfig.readRole();
        FloatingActionButton fabInsertJadwal = findViewById(R.id.fabInsertJadwal);
        fabInsertJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogJadwal();
            }
        });
        if (!role.equals("guru")) {
            fabInsertJadwal.setVisibility(View.GONE);
        }else{
            fabInsertJadwal.setVisibility(View.VISIBLE);
        }
    }

    private void dialogJadwal() {
        Dialog dialog = new Dialog(JadwalSiswaActivity.this);
        dialog.setContentView(R.layout.dialog_jadwal);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.show();

        Spinner spinner = dialog.findViewById(R.id.etKelas);
        Spinner spinner1 = dialog.findViewById(R.id.etIDGuru);
        Button btnSave = dialog.findViewById(R.id.btnSaveJadwal);
        etMapel = dialog.findViewById(R.id.etMapel);
        etJurusan = dialog.findViewById(R.id.etJurusan);
        etWaktu = dialog.findViewById(R.id.etWaktu);
        etHari = dialog.findViewById(R.id.etHari);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertJadwal();
            }
        });

        ArrayAdapter<CharSequence> adapterSpinnerNilai = ArrayAdapter.createFromResource(this, R.array.kelas, android.R.layout.simple_spinner_item);
        adapterSpinnerNilai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinnerNilai);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterSpinnerNilai1 = ArrayAdapter.createFromResource(this, R.array.guru, android.R.layout.simple_spinner_item);
        adapterSpinnerNilai1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapterSpinnerNilai1);
        spinner1.setOnItemSelectedListener(this);
    }

    private void insertJadwal() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        String mapel = etMapel.getText().toString();
        String jurusan = etJurusan.getText().toString();
        String waktu = etWaktu.getText().toString();
        String hari = etHari.getText().toString();
        Call<JadwalSiswa> call = apiInterface.insertJadwal("insert",id_guru,mapel,jurusan,kelas,waktu,hari);
        call.enqueue(new Callback<JadwalSiswa>() {
            @Override
            public void onResponse(Call<JadwalSiswa> call, Response<JadwalSiswa> response) {
                Log.i(JadwalSiswaActivity.class.getSimpleName(), response.toString());
                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")){
                    finish();
                    Toast.makeText(JadwalSiswaActivity.this, "Berhasil menambahkan data Jadwal", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(JadwalSiswaActivity.this, message, Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<JadwalSiswa> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(JadwalSiswaActivity.this, "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.etKelas:
                kelas = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(this, "Kelas "+kelas, Toast.LENGTH_SHORT).show();
                break;
            case R.id.etIDGuru:
                id_guru = adapterView.getItemAtPosition(i).toString();
//                Toast.makeText(this, "ID Guru "+id_guru, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}