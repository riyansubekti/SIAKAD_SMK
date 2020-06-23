package com.smk.siakad;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smk.siakad.api.ApiClient;
import com.smk.siakad.api.ApiInterface;
import com.smk.siakad.login.LoginActivity;
import com.smk.siakad.model.Siswa;
import com.smk.siakad.siswa.JadwalActivity;
import com.smk.siakad.siswa.NilaiActivity;

import java.util.List;


public class SiswaFragment extends Fragment {

    private List<Siswa> siswa;
    private TextView txtNamaUser;

    private OnLogoutListener logoutListener;

    public interface OnLogoutListener{
        public void logoutPerformed();
    }

    public SiswaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_siswa, container, false);
        txtNamaUser = view.findViewById(R.id.txtNamaUser);
        CardView cvNilai = view.findViewById(R.id.cvNilai);
        CardView cvJadwal = view.findViewById(R.id.cvJadwal);
        CardView cvDaftar = view.findViewById(R.id.cvDaftarUlang);
        CardView cvLogout = view.findViewById(R.id.cvLogout);
        TextView txtNilai = view.findViewById(R.id.txtNilai);
        TextView txtJadwal = view.findViewById(R.id.txtJadwal);
        TextView txtDaftar = view.findViewById(R.id.txtDaftarUlang);
        TextView txtLogout = view.findViewById(R.id.txtLogout);
        ImageView ivDaftar = view.findViewById(R.id.ivDaftarUlang);

        cvNilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NilaiActivity.class));
            }
        });

        cvDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), JadwalActivity.class));
            }
        });

        cvJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), JadwalSiswaActivity.class));
            }
        });

        cvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutListener.logoutPerformed();
            }
        });

        String role = LoginActivity.prefConfig.readName();
        if (role.equals("guru")) {
            txtNilai.setText("Data Nilai");
            txtJadwal.setText("Jadwal Mengajar");
            txtDaftar.setText("Data Siswa");
            ivDaftar.setImageDrawable(getResources().getDrawable(R.drawable.student));
        }

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<Siswa>> call =  apiInterface.loadSiswa(role);
        call.enqueue(new Callback<List<Siswa>>() {
            @Override
            public void onResponse(Call<List<Siswa>> call, Response<List<Siswa>> response) {
                siswa = response.body();
                txtNamaUser.setText(siswa.get(0).getNama());
            }

            @Override
            public void onFailure(Call<List<Siswa>> call, Throwable t) {

            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        logoutListener = (OnLogoutListener) activity;
    }
}
