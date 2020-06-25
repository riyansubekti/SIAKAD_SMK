package com.smk.siakad.siswa;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.smk.siakad.R;
import com.smk.siakad.api.ApiClient;
import com.smk.siakad.api.ApiInterface;
import com.smk.siakad.login.LoginActivity;
import com.smk.siakad.model.Siswa;

import java.util.List;

public class ProfilSiswaActivity extends AppCompatActivity {

    private TextView txtNIS, txtNama, txtJurusan, txtKelas, txtAlamat, txtTagihan;
    private Button btnSave;
    private String nis, nama, jurusan, kelas, alamat, tagihan, role, id_login;
    private ApiInterface apiInterface;
    private List<Siswa> siswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_siswa);

        txtNIS = findViewById(R.id.txtProfilNIS);
        txtNama = findViewById(R.id.txtProfilNama);
        txtJurusan = findViewById(R.id.txtProfilJurusan);
        txtKelas = findViewById(R.id.txtProfilKelas);
        txtAlamat = findViewById(R.id.txtProfilAlamat);
        txtTagihan = findViewById(R.id.txtProfilTagihan);
        btnSave = findViewById(R.id.btnProfilSave);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        role = LoginActivity.prefConfig.readRole();
        id_login = LoginActivity.prefConfig.readID();

        Intent intent = getIntent();
        nis = intent.getStringExtra("nis");
        nama = intent.getStringExtra("nama");
        jurusan = intent.getStringExtra("jurusan");
        kelas = intent.getStringExtra("kelas");
        alamat = intent.getStringExtra("alamat");
        tagihan = intent.getStringExtra("tagihan");

        getSiswa("160101001");
    }

    private void getSiswa(String nis) {
        readMode();
        Call<List<Siswa>> call =  apiInterface.loadSiswa(null, id_login);
        call.enqueue(new Callback<List<Siswa>>() {
            @Override
            public void onResponse(Call<List<Siswa>> call, Response<List<Siswa>> response) {
                siswa = response.body();
                txtNIS.setText(siswa.get(0).getId_siswa());
                txtNama.setText(siswa.get(0).getNama());
                txtJurusan.setText(siswa.get(0).getJurusan());
                txtKelas.setText(siswa.get(0).getKelas());
                txtAlamat.setText(siswa.get(0).getAlamat());
                txtTagihan.setText(siswa.get(0).getTagihan());
            }

            @Override
            public void onFailure(Call<List<Siswa>> call, Throwable t) {
                Toast.makeText(ProfilSiswaActivity.this, "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
                System.out.println("okeh"+t);
            }
        });

    }

    private void deleteData(String nis) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting...");
        progressDialog.show();
    }

    private void readMode() {
        txtNIS.setFocusableInTouchMode(false);
        txtNama.setFocusableInTouchMode(false);
        txtJurusan.setFocusableInTouchMode(false);
        txtKelas.setFocusableInTouchMode(false);
        txtAlamat.setFocusableInTouchMode(false);
        txtTagihan.setFocusableInTouchMode(false);
    }

    private void editMode() {
        txtNIS.setFocusableInTouchMode(true);
        txtNama.setFocusableInTouchMode(true);
        txtJurusan.setFocusableInTouchMode(true);
        txtKelas.setFocusableInTouchMode(true);
        txtAlamat.setFocusableInTouchMode(true);
        txtTagihan.setFocusableInTouchMode(true);
    }
}
