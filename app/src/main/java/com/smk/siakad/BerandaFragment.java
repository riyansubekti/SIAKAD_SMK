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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smk.siakad.api.ApiClient;
import com.smk.siakad.api.ApiInterface;
import com.smk.siakad.guru.DaftarUlangActivity;
import com.smk.siakad.login.LoginActivity;
import com.smk.siakad.model.Pengumuman;
import com.smk.siakad.model.Siswa;
import com.smk.siakad.siswa.JadwalSiswaActivity;
import com.smk.siakad.siswa.NilaiActivity;
import com.smk.siakad.siswa.ProfilSiswaActivity;
import com.smk.siakad.siswa.SiswaActivity;

import java.util.List;


public class BerandaFragment extends Fragment {

    private List<Siswa> siswa;
    private List<Pengumuman> pengumuman;
    private TextView txtNamaUser, txtTagihan, txtNIS, txtJudulPeng, txtDeskPeng;
    public static String username = null;
    public static String role = null;
    public static String kelas = null;

    private ApiInterface apiInterface;

    private OnLogoutListener logoutListener;

    public interface OnLogoutListener{
        public void logoutPerformed();
    }

    public BerandaFragment() {
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
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);
        txtNamaUser = view.findViewById(R.id.txtNamaUser);
        txtTagihan = view.findViewById(R.id.txtTagihan);
        txtNIS = view.findViewById(R.id.txtNIS);
        txtJudulPeng = view.findViewById(R.id.txtPengumumanJudul);
        txtDeskPeng = view.findViewById(R.id.txtPengumumanDesk);
        CardView cvNilai = view.findViewById(R.id.cvNilai);
        CardView cvJadwal = view.findViewById(R.id.cvJadwal);
        CardView cvProfil = view.findViewById(R.id.cvProfil);
        CardView cvLogout = view.findViewById(R.id.cvLogout);
        TextView txtNilai = view.findViewById(R.id.txtNilai);
        TextView txtJadwal = view.findViewById(R.id.txtJadwal);
        TextView txtLogout = view.findViewById(R.id.txtLogout);
        TextView txtProfil = view.findViewById(R.id.txtProfil);
        ImageView ivLogout = view.findViewById(R.id.ivLogout);
        RelativeLayout btnLogout = view.findViewById(R.id.btnLogout);
        TextView btnProfil = view.findViewById(R.id.btnProfil);
        LinearLayout l_tagihan = view.findViewById(R.id.l_tagihan);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        role = LoginActivity.prefConfig.readRole();
        username = LoginActivity.prefConfig.readID();

        cvNilai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NilaiActivity.class));
            }
        });

        cvProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProfilSiswaActivity.class);
                intent.putExtra("key", "profil");
                startActivity(intent);
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

        if (role.equals("guru")) {
            txtNilai.setText("Data Nilai");
            txtJadwal.setText("Data Jadwal");
            txtProfil.setText("Data Siswa");
            txtLogout.setText("Daftar Ulang");
            btnLogout.setVisibility(View.VISIBLE);
            btnProfil.setVisibility(View.VISIBLE);
            l_tagihan.setVisibility(View.GONE);

            ivLogout.setImageDrawable(getResources().getDrawable(R.drawable.repeat));

            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logoutListener.logoutPerformed();
                }
            });

            btnProfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ProfilSiswaActivity.class);
                    intent.putExtra("key", "profil");
                    startActivity(intent);
                }
            });

            cvNilai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), SiswaActivity.class);
                    intent.putExtra("menu", "nilai");
                    startActivity(intent);
                }
            });

            cvProfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), SiswaActivity.class);
                    intent.putExtra("menu", "profil");
                    startActivity(intent);
                }
            });

            cvJadwal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), JadwalSiswaActivity.class);
                    intent.putExtra("kelas", kelas);
                    startActivity(intent);
                }
            });

            cvLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), DaftarUlangActivity.class));
                }
            });
        }
        return view;
    }

    private void getPengumuman() {
        Call<List<Pengumuman>> call = apiInterface.getPeng(role);
        call.enqueue(new Callback<List<Pengumuman>>() {
            @Override
            public void onResponse(Call<List<Pengumuman>> call, Response<List<Pengumuman>> response) {
                pengumuman = response.body();
                txtJudulPeng.setText(pengumuman.get(0).getJudul());
                txtDeskPeng.setText(pengumuman.get(0).getDeskripsi());
            }

            @Override
            public void onFailure(Call<List<Pengumuman>> call, Throwable t) {
                Toast.makeText(getActivity(), "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
                System.out.println("okeh"+t);
            }
        });
    }

    public void getSiswa() {
        role = LoginActivity.prefConfig.readRole();
        username = LoginActivity.prefConfig.readID();
        Call<List<Siswa>> call =  apiInterface.loadSiswa(role, username);
        call.enqueue(new Callback<List<Siswa>>() {
            @Override
            public void onResponse(Call<List<Siswa>> call, Response<List<Siswa>> response) {
                siswa = response.body();
                if (role.equals("siswa")) {
                    txtNamaUser.setText(siswa.get(0).getNama());
                    txtTagihan.setText(siswa.get(0).getTagihan());
                    txtNIS.setText(siswa.get(0).getId_siswa());
                    kelas = siswa.get(0).getKelas();
                } else {
                    txtNamaUser.setText(siswa.get(0).getNama());
                    txtNIS.setText(siswa.get(0).getId_guru());
                }
            }

            @Override
            public void onFailure(Call<List<Siswa>> call, Throwable t) {
                Toast.makeText(getActivity(), "rp :"+
                                t.getMessage().toString(),
                        Toast.LENGTH_SHORT).show();
                System.out.println("okeh"+t);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getPengumuman();
        getSiswa();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        logoutListener = (OnLogoutListener) activity;
    }
}
