package com.smk.siakad.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smk.siakad.R;
import com.smk.siakad.filter.FilterJadwalSiswa;
import com.smk.siakad.model.JadwalSiswa;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterJadwalSiswa extends RecyclerView.Adapter<AdapterJadwalSiswa.MyViewHolder> implements Filterable {
    public List<JadwalSiswa> jadwal, jadwalFilter;
    private Context context;
    private RecyclerViewClickListener mListener;
    private FilterJadwalSiswa filter;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private RecyclerViewClickListener mListener;
        private TextView idj, mapel, waktu, guru;
        private LinearLayout mRowContainer;

        MyViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            idj = itemView.findViewById(R.id.txtJadwalID);
            mapel = itemView.findViewById(R.id.txtNamaJadwal);
            waktu = itemView.findViewById(R.id.txtWaktuJadwal);
            guru = itemView.findViewById(R.id.txtGuruJadwal);
            mRowContainer = itemView.findViewById(R.id.row_jadwal_siswa);

            mListener = listener;
            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.row_jadwal_siswa:
                    mListener.onRowClick(mRowContainer, getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }

    public interface RecyclerViewClickListener {
        void onRowClick(View view, int position);
    }

    public AdapterJadwalSiswa(List<JadwalSiswa> jadwal, Context context, RecyclerViewClickListener listener) {
        this.jadwal = jadwal;
        this.jadwalFilter = jadwal;
        this.context = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public AdapterJadwalSiswa.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jadwal_siswa, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterJadwalSiswa.MyViewHolder holder, int position) {
        holder.idj.setText(jadwal.get(position).getId_mapel());
        holder.mapel.setText(jadwal.get(position).getNama_mapel());
        holder.waktu.setText(jadwal.get(position).getWaktu_pelajaran());
        holder.guru.setText(jadwal.get(position).getNama());
    }

    @Override
    public Filter getFilter() {
        if (filter==null) {
            filter=new FilterJadwalSiswa((ArrayList<JadwalSiswa>) jadwalFilter,this);

        }
        return filter;
    }

    @Override
    public int getItemCount() {
        return jadwal.size();
    }


}
