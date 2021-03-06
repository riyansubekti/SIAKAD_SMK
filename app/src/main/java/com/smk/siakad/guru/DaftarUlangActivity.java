package com.smk.siakad.guru;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.smk.siakad.R;
import com.smk.siakad.utils.ReminderNotification;
import com.smk.siakad.api.ApiClient;
import com.smk.siakad.api.ApiInterface;
import com.smk.siakad.model.Siswa;

import java.util.ArrayList;
import java.util.List;

public class DaftarUlangActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManagerCompat;
    private EditText etKelas, etTagihan, etReminder;
    private Button btnInput;
    private ApiInterface apiInterface;
    private Spinner spnNis;
    private List<Siswa> siswa;
    private String nis;
    List<String> listSpinner = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_ulang);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Daftar Ulang");
        }

        notificationManagerCompat = NotificationManagerCompat.from(this);
        etKelas = findViewById(R.id.etDUKelas);
        etTagihan = findViewById(R.id.etDUTagihan);
        etReminder = findViewById(R.id.etReminder);
        btnInput = findViewById(R.id.btnDUInput);
        spnNis = findViewById(R.id.spnDUNis);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        spinnerNis();
        spnNis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nis = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(DaftarUlangActivity.this, "Anda memilih NIS : "+nis, Toast.LENGTH_SHORT).show();
                etKelas.setText(siswa.get(i).getKelas());
                etTagihan.setText(siswa.get(i).getTagihan());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
                sendNotif();
            }
        });
    }

    public void sendNotif() {
        int jam = Integer.parseInt(etReminder.getText().toString());
        String tagihan = etTagihan.getText().toString();
        Intent intent = new Intent(DaftarUlangActivity.this, ReminderNotification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(DaftarUlangActivity.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long timeAtButtonClick = System.currentTimeMillis();
        long timeSecondsInMillis = 3600 * 1000 * jam;
        alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick + timeSecondsInMillis, pendingIntent);
    }

    private void spinnerNis() {
        Call<List<Siswa>> call = apiInterface.getSiswa();
        call.enqueue(new Callback<List<Siswa>>() {
            @Override
            public void onResponse(Call<List<Siswa>> call, Response<List<Siswa>> response) {
                siswa = response.body();
                for (int i = 0; i < siswa.size(); i++) {
                    listSpinner.add(siswa.get(i).getId_siswa());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(DaftarUlangActivity.this, android.R.layout.simple_spinner_item, listSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnNis.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Siswa>> call, Throwable t) {
                Toast.makeText(DaftarUlangActivity.this, "Error Spinner...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void insertData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        String kelas = etKelas.getText().toString();
        String tagihan = etTagihan.getText().toString();

        Call<Siswa> call = apiInterface.insertDU(nis, kelas, tagihan);
        call.enqueue(new Callback<Siswa>() {
            @Override
            public void onResponse(Call<Siswa> call, Response<Siswa> response) {
                Log.i(DaftarUlangActivity.class.getSimpleName(), response.toString());
                String value = response.body().getValue();
                String message = response.body().getMassage();
                if (value.equals("1")){
                    finish();
                    Toast.makeText(DaftarUlangActivity.this, "Berhasil melakukan daftar ulang!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DaftarUlangActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Siswa> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DaftarUlangActivity.this, "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

    }}
