package com.smk.siakad.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.smk.siakad.R;
import com.smk.siakad.model.Siswa;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterSiswa extends RecyclerView.Adapter<AdapterSiswa.MyViewHolder> {

    public List<Siswa> siswa, siswaFilter;
    private Context context;
    private RecyclerViewClickListener mListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecyclerViewClickListener mListener;
        private ImageView mPicture, btnDelete;
        private TextView mID, mNama;
        private RelativeLayout mRowContainer;

        MyViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            mPicture = itemView.findViewById(R.id.picture);
            mID = itemView.findViewById(R.id.txtID);
            mNama = itemView.findViewById(R.id.txtNama);
            mRowContainer = itemView.findViewById(R.id.row_container);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            mListener = listener;
            mRowContainer.setOnClickListener(this);
            btnDelete.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.row_container:
                    mListener.onRowClick(mRowContainer, getAdapterPosition());
                    break;
                case R.id.btnDelete:
                    mListener.onDeleteClick(btnDelete, getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }

        public AdapterSiswa(List<Siswa> siswa, Context context, RecyclerViewClickListener listener) {
            this.siswa = siswa;
            this.siswaFilter = siswa;
            this.context = context;
            this.mListener = listener;
        }

        public interface RecyclerViewClickListener {
            void onRowClick(View view, int position);
            void onDeleteClick(View view, int position);
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new MyViewHolder(view, mListener);
        }

        @SuppressLint("CheckResult")
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.mID.setText(siswa.get(position).getId_siswa());
            holder.mNama.setText(siswa.get(position).getNama());

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.ic_launcher_background);
            requestOptions.error(R.drawable.ic_launcher_background);

            Glide.with(context)
                    .load(siswa.get(position).getFoto())
                    .apply(requestOptions)
                    .into(holder.mPicture);
        }

        @Override
        public int getItemCount() {
            return siswa.size();
        }
    }
