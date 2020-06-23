package com.smk.siakad.siswa;

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
import android.widget.TextView;
import android.widget.Toast;

import com.smk.siakad.R;
import com.smk.siakad.adapter.AdapterJadwalSiswa;
import com.smk.siakad.api.ApiClient;
import com.smk.siakad.api.ApiInterface;
import com.smk.siakad.model.JadwalSiswa;

import java.util.List;


public class HariFragment extends Fragment {
    private AdapterJadwalSiswa adapterJadwalSiswa;
    private List<JadwalSiswa> jadwalSiswaList;
    AdapterJadwalSiswa.RecyclerViewClickListener listener;
    private String hari, kelas;
    private ApiInterface apiInterface;
    private RecyclerView recyclerView;

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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        txtFrag.setText(hari);

        return view;
    }

    public void getJadwalSiswa(){
        kelas = "1-A";
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

    @Override
    public void onResume() {
        super.onResume();
        getJadwalSiswa();
    }
}
