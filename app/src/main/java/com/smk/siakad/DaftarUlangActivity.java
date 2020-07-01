package com.smk.siakad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smk.siakad.api.ApiClient;
import com.smk.siakad.api.ApiInterface;
import com.smk.siakad.model.Siswa;
import com.smk.siakad.siswa.NilaiActivity;

import static com.smk.siakad.App.CHANNEL;

public class DaftarUlangActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManagerCompat;
    private EditText etNis, etKelas, etTagihan, etReminder;
    private Button btnInput;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_ulang);

        notificationManagerCompat = NotificationManagerCompat.from(this);
        etNis = findViewById(R.id.etDUNis);
        etKelas = findViewById(R.id.etDUKelas);
        etTagihan = findViewById(R.id.etDUTagihan);
        etReminder = findViewById(R.id.etReminder);
        btnInput = findViewById(R.id.btnDUInput);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
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
        long timeSecondsInMillis = 1000 * jam;

        alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick + timeSecondsInMillis, pendingIntent);
//
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL)
//                .setSmallIcon(R.drawable.ic_notifi)
//                .setContentTitle("Reminder Untuk NIS : ")
//                .setContentText("Tagihan anda sebesar : ")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                .build();
//
//        SystemClock.sleep(total);
//        notificationManagerCompat.notify(1, notification);
//        Toast.makeText(this, "Notifikasi akan terkirim dalam waktu : " + total, Toast.LENGTH_SHORT).show();
    }

    public void insertData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        String nis = etNis.getText().toString();
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