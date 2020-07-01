package com.smk.siakad.siswa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.smk.siakad.R;

import java.util.ArrayList;
import java.util.HashMap;

public class KelasActivity extends AppCompatActivity {

    ListView lstKelas;
    ProgressBar progressBar;
    String[] namaKelas={"1-A", "1-B", "1-C", "2-A", "2-B", "2-C", "3-A","3-B", "3-C"};
    String[] namaJurusan={"RPL", "TKJ", "Multimedia", "RPL", "TKJ", "Multimedia", "RPL", "TKJ", "Multimedia"};
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelas);
        lstKelas = findViewById(R.id.lvKelas);
        progressBar = findViewById(R.id.progress);
        setKelas();
    }

    private void setKelas() {
        progressBar.setVisibility(View.GONE);
        HashMap<String, String> map = new HashMap<String, String>();
        for (int i = 0; i<namaKelas.length; i++) {
            HashMap<String,String> hashMap=new HashMap<>();//create a hashmap to store the data in key value pair
            hashMap.put("kelas",namaKelas[i]);
            hashMap.put("jurusan",namaJurusan[i]);
            arrayList.add(hashMap);//add the hashmap into arrayList
        }

        String[] from={"kelas","jurusan"};//string array
        int[] to={R.id.txtKelas,R.id.txtJurusan};//int array of views id's
        SimpleAdapter simpleAdapter=new SimpleAdapter(KelasActivity.this, arrayList,R.layout.item_kelas, from, to);//Create object and set the parameters for simpleAdapter
        lstKelas.setAdapter(simpleAdapter);//sets the adapter for listView

        lstKelas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(KelasActivity.this, namaKelas[position], Toast.LENGTH_LONG).show();//show the selected image in toast according to position
                Intent intent = new Intent(KelasActivity.this, JadwalSiswaActivity.class);
                intent.putExtra("kelas", namaKelas[position]);
                startActivity(intent);
            }
        });
    }
}
