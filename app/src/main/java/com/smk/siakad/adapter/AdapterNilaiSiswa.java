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
import com.smk.siakad.filter.FilterNilaiSiswa;
import com.smk.siakad.model.Nilai;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterNilaiSiswa extends RecyclerView.Adapter<AdapterNilaiSiswa.MyViewHolder> implements Filterable {

    public List<Nilai> nilai, nilaiFilter;
    private Context context;
    private RecyclerViewClickListener mListener;
    FilterNilaiSiswa filter;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerViewClickListener mListener;
        private TextView nomer, namaMapel, tugas, uts, uas, nilai, kkm;
        private LinearLayout mRowContainer;

        MyViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            nomer = itemView.findViewById(R.id.txtNomer);
            namaMapel = itemView.findViewById(R.id.txtNamaMapel);
            tugas = itemView.findViewById(R.id.txtNilaiTugas);
            uts = itemView.findViewById(R.id.txtNilaiUTS);
            uas = itemView.findViewById(R.id.txtNilaiUAS);
            nilai = itemView.findViewById(R.id.txtNilaiTotal);
            kkm = itemView.findViewById(R.id.txtNilaiKKM);
            mRowContainer = itemView.findViewById(R.id.row_nilai);

            mListener = listener;
            mRowContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.row_nilai:
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

    public AdapterNilaiSiswa(List<Nilai> nilai, Context context, RecyclerViewClickListener listener) {
        this.nilai = nilai;
        this.nilaiFilter = nilai;
        this.context = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public AdapterNilaiSiswa.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nilai, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNilaiSiswa.MyViewHolder holder, int position) {
        holder.nomer.setText(nilai.get(position).getNomer());
        holder.namaMapel.setText(nilai.get(position).getNama_mapel());
        holder.tugas.setText(nilai.get(position).getTugas());
        holder.uts.setText(nilai.get(position).getUts());
        holder.uas.setText(nilai.get(position).getUas());
        holder.nilai.setText(nilai.get(position).getNilai());
        holder.kkm.setText(nilai.get(position).getKkm());
    }

    @Override
    public Filter getFilter() {
        if (filter==null) {
            filter=new FilterNilaiSiswa((ArrayList<Nilai>) nilaiFilter,this);

        }
        return filter;
    }

    @Override
    public int getItemCount() {
        return nilai.size();
    }
}
