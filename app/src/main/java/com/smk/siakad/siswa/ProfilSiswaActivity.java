package com.smk.siakad.siswa;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smk.siakad.R;
import com.smk.siakad.api.ApiClient;
import com.smk.siakad.api.ApiInterface;
import com.smk.siakad.login.LoginActivity;
import com.smk.siakad.model.Siswa;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ProfilSiswaActivity extends AppCompatActivity {

    private EditText txtNIS, txtNama, txtJurusan, txtKelas, txtAlamat, txtTagihan, txtPassword, txtxComfirm;
    private Button btnSave;
    private String role, username, key;
    private ApiInterface apiInterface;
    private List<Siswa> siswa;
    private CircleImageView ciPicture;
    private Bitmap bitmap;
    private FloatingActionButton fbChooseImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_siswa);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Profil Siswa");
        }

        txtNIS = findViewById(R.id.txtProfilNIS);
        txtNama = findViewById(R.id.txtProfilNama);
        txtJurusan = findViewById(R.id.txtProfilJurusan);
        txtKelas = findViewById(R.id.txtProfilKelas);
        txtAlamat = findViewById(R.id.txtProfilAlamat);
        txtTagihan = findViewById(R.id.txtProfilTagihan);
        txtPassword = findViewById(R.id.txtProfilPassword);
        txtxComfirm = findViewById(R.id.txtProfilConfirm);
        btnSave = findViewById(R.id.btnProfilSave);
        ciPicture = findViewById(R.id.ciPicture);
        fbChooseImage = findViewById(R.id.fabChoosePic);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        username = LoginActivity.prefConfig.readID();
        role = LoginActivity.prefConfig.readRole();
        Intent intent = getIntent();
        key = intent.getStringExtra("key");

        if (key.equals("insert")) {
            txtNIS.setText("");
            txtPassword.setText("");
            txtxComfirm.setText("");
            txtNama.setText("");
            txtJurusan.setText("");
            txtKelas.setText("");
            txtAlamat.setText("");
            txtTagihan.setText("");
            editMode();
        } else if (key.equals("update")) {
            username = intent.getStringExtra("username");
            role = "siswa";
            getSiswa();
            editMode();
        } else if (key.equals("profil")){
            btnSave.setVisibility(View.GONE);
            getSiswa();
        }else {
            Toast.makeText(this, "PROFIL SISWA", Toast.LENGTH_SHORT).show();
            btnSave.setVisibility(View.GONE);
            getSiswa();
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (key.equals("insert")) {
                    insertData();
                } else if (key.equals("update")) {
                    btnSave.setText("UPDATE DATA");
                    updateData();
                } else {
                    Toast.makeText(ProfilSiswaActivity.this, "Anda tidak memiliki akses untuk ini!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fbChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFile();
            }
        });

    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                ciPicture.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void getSiswa() {
        Call<List<Siswa>> call =  apiInterface.loadSiswa(role, username);
        call.enqueue(new Callback<List<Siswa>>() {
            @SuppressLint("CheckResult")
            @Override
            public void onResponse(Call<List<Siswa>> call, Response<List<Siswa>> response) {
                siswa = response.body();

                String nis = siswa.get(0).getId_siswa();
                if (role.equals("guru")) {
                    getSupportActionBar().setTitle("Profil Guru");
                    nis = siswa.get(0).getId_guru();
                    txtKelas.setVisibility(View.GONE);
                    txtJurusan.setVisibility(View.GONE);
                    txtTagihan.setVisibility(View.GONE);
                    txtNIS.setHint("ID GURU");
                    btnSave.setVisibility(View.GONE);
                }
                String password = siswa.get(0).getPassword();
                String nama = siswa.get(0).getNama();
                String jurusan = siswa.get(0).getJurusan();
                String kelas = siswa.get(0).getKelas();
                String alamat = siswa.get(0).getAlamat();
                String tagihan = siswa.get(0).getTagihan();
                String foto = siswa.get(0).getFoto();

                txtNIS.setText(nis);
                txtPassword.setText(password);
                txtxComfirm.setText(password);
                txtNama.setText(nama);
                txtJurusan.setText(jurusan);
                txtKelas.setText(kelas);
                txtAlamat.setText(alamat);
                txtTagihan.setText(tagihan);

                RequestOptions requestOptions = new RequestOptions();
                requestOptions.skipMemoryCache(true);
                requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
                requestOptions.placeholder(R.drawable.ic_profil);
                requestOptions.error(R.drawable.ic_profil);

                Glide.with(ProfilSiswaActivity.this)
                        .load(foto)
                        .apply(requestOptions)
                        .into(ciPicture);
            }

            @Override
            public void onFailure(Call<List<Siswa>> call, Throwable t) {
                Toast.makeText(ProfilSiswaActivity.this, "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void insertData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        String nis = txtNIS.getText().toString();
        String nama = txtNama.getText().toString();
        String kelas = txtKelas.getText().toString();
        String alamat = txtAlamat.getText().toString();
        String jurusan = txtJurusan.getText().toString();
        String tagihan = txtTagihan.getText().toString();
        String password = txtPassword.getText().toString();
        String foto = "";
        if (bitmap == null) {
            foto = "";
        } else {
            foto = getStringImage(bitmap);
        }

        Call<Siswa> call =  apiInterface.insertSiswa(key,nis,
                password,"siswa",nis,nama, kelas,alamat,
                jurusan,tagihan,foto);
        call.enqueue(new Callback<Siswa>() {
            @Override
            public void onResponse(Call<Siswa> call, Response<Siswa> response) {

                Log.i(ProfilSiswaActivity.class.getSimpleName(), response.toString());
                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    finish();
                    Toast.makeText(ProfilSiswaActivity.this, "Berhasil menambahkan data Siswa", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfilSiswaActivity.this, message, Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Siswa> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ProfilSiswaActivity.this, "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        String nis = txtNIS.getText().toString();
        String nama = txtNama.getText().toString();
        String kelas = txtKelas.getText().toString();
        String alamat = txtAlamat.getText().toString();
        String jurusan = txtJurusan.getText().toString();
        String tagihan = txtTagihan.getText().toString();
        String password = txtPassword.getText().toString();
        String foto = "";
        if (bitmap == null) {
            foto = "";
        } else {
            foto = getStringImage(bitmap);
        }

        Call<Siswa> call = apiInterface.updateSiswa(key,nis,password,nama,
                kelas,alamat,jurusan,tagihan,foto);
        call.enqueue(new Callback<Siswa>() {
            @Override
            public void onResponse(Call<Siswa> call, Response<Siswa> response) {
                Log.i(ProfilSiswaActivity.class.getSimpleName(), response.toString());
                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    finish();
                    Toast.makeText(ProfilSiswaActivity.this, "Berhasil mengubah data Siswa", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfilSiswaActivity.this, message, Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Siswa> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ProfilSiswaActivity.this, "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editMode() {
        txtNIS.setEnabled(true);
        txtNama.setEnabled(true);
        txtJurusan.setEnabled(true);
        txtKelas.setEnabled(true);
        txtAlamat.setEnabled(true);
        txtTagihan.setEnabled(true);
        txtTagihan.setEnabled(true);
        txtPassword.setEnabled(true);
        txtxComfirm.setEnabled(true);
        fbChooseImage.setVisibility(View.VISIBLE);
    }
}
