package com.smk.siakad.siswa;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.smk.siakad.R;
import com.smk.siakad.BerandaFragment;
import com.smk.siakad.adapter.AdapterJadwalSiswa;
import com.smk.siakad.api.ApiClient;
import com.smk.siakad.api.ApiInterface;
import com.smk.siakad.login.LoginActivity;
import com.smk.siakad.model.JadwalSiswa;
import com.smk.siakad.model.Siswa;

import java.util.List;


public class HariFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private AdapterJadwalSiswa adapterJadwalSiswa;
    private List<JadwalSiswa> jadwalSiswaList;
    private List<Siswa> siswa;
    AdapterJadwalSiswa.RecyclerViewClickListener listener;
    private String id_mapel, hari, kelas, role, mKelas, mId_guru, gHari, gMapel, gJurusan, gWaktu, gId_mapel, kelasSiswa;
    private ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private Spinner spnKelas;
    private EditText etMapel, etJurusan, etWaktu, etHari;
    private TextView txtDialogJdl;

    public HariFragment(String getHari) {
        // Required empty public constructor
        hari = getHari;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hari, container, false);

        TextView txtFrag = view.findViewById(R.id.txtFrag);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        recyclerView = view.findViewById(R.id.rcJadwalSiswa);
        spnKelas = view.findViewById(R.id.spnKelas);
        TextView txtPilih = view.findViewById(R.id.txtPilihKelas);
        role = LoginActivity.prefConfig.readRole();

        if (role.equals("guru")) {
            listener = new AdapterJadwalSiswa.RecyclerViewClickListener() {
                @Override
                public void onRowClick(View view, int position) {
                    gId_mapel = adapterJadwalSiswa.jadwal.get(position).getId_mapel();
                    gMapel = adapterJadwalSiswa.jadwal.get(position).getNama_mapel();
                    gJurusan = adapterJadwalSiswa.jadwal.get(position).getJurusan();
                    gWaktu = adapterJadwalSiswa.jadwal.get(position).getWaktu_pelajaran();
                    gHari = adapterJadwalSiswa.jadwal.get(position).getHari();
                    dialogJadwal(position, gId_mapel, gMapel, gJurusan, gWaktu, gHari);
                    txtDialogJdl.setText("UPDATE JADWAL SISWA");
//                    Toast.makeText(getActivity(), "posisi "+position, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "ID1 "+jadwalSiswaList.get(position).getId_mapel(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "ID2 "+adapterJadwalSiswa.jadwal.get(position).getId_mapel(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "ID3 "+adapterJadwalSiswa.jadwalFilter.get(position).getId_mapel(), Toast.LENGTH_SHORT).show();
                }
            };
        }else {
            spnKelas.setVisibility(View.GONE);
            txtPilih.setVisibility(View.GONE);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        txtFrag.setText(hari);
        ArrayAdapter<CharSequence> adapterSpinnerNilai = ArrayAdapter.createFromResource(getActivity(), R.array.kelas, android.R.layout.simple_spinner_item);
        adapterSpinnerNilai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnKelas.setAdapter(adapterSpinnerNilai);
        spnKelas.setOnItemSelectedListener(this);

        return view;
    }

    private void dialogJadwal(int position, String mId_mapel, String mMapel, String mJurusan, String mWaktu, String mHari) {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_jadwal);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.show();

        Spinner spinner = dialog.findViewById(R.id.etKelas);
        Spinner spinner1 = dialog.findViewById(R.id.etIDGuru);
        Button btnSave = dialog.findViewById(R.id.btnSaveJadwal);
        Button btnsHapus = dialog.findViewById(R.id.btnDeleteJadwal);
        etMapel = dialog.findViewById(R.id.etMapel);
        etJurusan = dialog.findViewById(R.id.etJurusan);
        etWaktu = dialog.findViewById(R.id.etWaktu);
        etHari = dialog.findViewById(R.id.etHari);
        txtDialogJdl = dialog.findViewById(R.id.txtJadwalJudul);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        id_mapel = mId_mapel;
        etMapel.setText(mMapel);
        etJurusan.setText(mJurusan);
        etWaktu.setText(mWaktu);
        etHari.setText(hari);

        btnsHapus.setVisibility(View.VISIBLE);

        ArrayAdapter<CharSequence> adapterSpinnerNilai = ArrayAdapter.createFromResource(getActivity(), R.array.kelas, android.R.layout.simple_spinner_item);
        adapterSpinnerNilai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpinnerNilai);
        spinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterSpinnerNilai1 = ArrayAdapter.createFromResource(getActivity(), R.array.guru, android.R.layout.simple_spinner_item);
        adapterSpinnerNilai1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapterSpinnerNilai1);
        spinner1.setOnItemSelectedListener(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateJadwal(id_mapel);
            }
        });
        btnsHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteJadwal(id_mapel);
            }
        });
    }

    private void deleteJadwal(final String id) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Deleting...");
        progressDialog.show();

        Call<JadwalSiswa> call = apiInterface.deleteJadwal("delete",id);
        call.enqueue(new Callback<JadwalSiswa>() {
            @Override
            public void onResponse(Call<JadwalSiswa> call, Response<JadwalSiswa> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")){
                    getActivity().finish();
                    Toast.makeText(getActivity(), "Berhasil menghapus data Jadwal dengan ID : "+id, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<JadwalSiswa> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateJadwal(final String id) {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        String mapel = etMapel.getText().toString();
        String jurusan = etJurusan.getText().toString();
        String waktu = etWaktu.getText().toString();
        String hari = etHari.getText().toString();

        Call<JadwalSiswa> call = apiInterface.updateJadwal("update", id,
                mId_guru, mapel, jurusan, mKelas,waktu, hari);
        call.enqueue(new Callback<JadwalSiswa>() {
            @Override
            public void onResponse(Call<JadwalSiswa> call, Response<JadwalSiswa> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();

                if (value.equals("1")){
                    getActivity().finish();
                    Toast.makeText(getActivity(), "Berhasil mengubah data Jadwal dengan ID : "+id, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<JadwalSiswa> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getJadwalSiswa(String kelas){
        Call<List<JadwalSiswa>> call = apiInterface.getJadwalSiswa(kelas);
        call.enqueue(new Callback<List<JadwalSiswa>>() {
            @Override
            public void onResponse(Call<List<JadwalSiswa>> call, Response<List<JadwalSiswa>> response) {
                jadwalSiswaList = response.body();
                Log.i(JadwalSiswaActivity.class.getSimpleName(), response.body().toString());
                adapterJadwalSiswa = new AdapterJadwalSiswa(jadwalSiswaList, getActivity(), listener);
                recyclerView.setAdapter(adapterJadwalSiswa);
                adapterJadwalSiswa.getFilter().filter(hari);
                adapterJadwalSiswa.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<JadwalSiswa>> call, Throwable t) {
                Toast.makeText(getActivity(), "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSiswa() {
        String username = LoginActivity.prefConfig.readID();
        String roles = LoginActivity.prefConfig.readRole();

        Call<List<Siswa>> call =  apiInterface.loadSiswa(roles, username);
        call.enqueue(new Callback<List<Siswa>>() {
            @Override
            public void onResponse(Call<List<Siswa>> call, Response<List<Siswa>> response) {
                siswa = response.body();
                kelasSiswa = siswa.get(0).getKelas();
            }

            @Override
            public void onFailure(Call<List<Siswa>> call, Throwable t) {
                Toast.makeText(getActivity(), "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (BerandaFragment.kelas == null){
            BerandaFragment.kelas = "1-A";
        }
        getJadwalSiswa(BerandaFragment.kelas);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (role.equals("guru")) {
            kelas = adapterView.getItemAtPosition(i).toString();
            getJadwalSiswa(kelas);
            switch (adapterView.getId()) {
                case R.id.spnKelas:
                    kelas = adapterView.getItemAtPosition(i).toString();
//                    Toast.makeText(getActivity(), "Kelas "+kelas, Toast.LENGTH_SHORT).show();
                    getJadwalSiswa(kelas);
                    break;
                case R.id.etIDGuru:
                    mId_guru = adapterView.getItemAtPosition(i).toString();
//                    Toast.makeText(getActivity(), "ID Guru "+mId_guru, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.etKelas:
                    mKelas = adapterView.getItemAtPosition(i).toString();
//                    Toast.makeText(getActivity(), "mKelas "+mKelas, Toast.LENGTH_SHORT).show();
                default:
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
