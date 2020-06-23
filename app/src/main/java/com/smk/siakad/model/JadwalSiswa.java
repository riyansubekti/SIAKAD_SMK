package com.smk.siakad.model;

import com.google.gson.annotations.SerializedName;

public class JadwalSiswa {
    @SerializedName("nomer")
    private String nomer;
    @SerializedName("nama_mapel")
    private String nama_mapel;
    @SerializedName("waktu_pelajaran")
    private String waktu_pelajaran;
    @SerializedName("hari")
    private String hari;
    @SerializedName("nama")
    private String nama;
    @SerializedName("message")
    private String message;

    public String getNomer() {
        return nomer;
    }

    public void setNomer(String nomer) {
        this.nomer = nomer;
    }

    public String getNama_mapel() {
        return nama_mapel;
    }

    public void setNama_mapel(String nama_mapel) {
        this.nama_mapel = nama_mapel;
    }

    public String getWaktu_pelajaran() {
        return waktu_pelajaran;
    }

    public void setWaktu_pelajaran(String waktu_pelajaran) {
        this.waktu_pelajaran = waktu_pelajaran;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
