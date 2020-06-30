package com.smk.siakad.model;

import com.google.gson.annotations.SerializedName;

public class JadwalSiswa {
    @SerializedName("nomer")
    private String nomer;
    @SerializedName("id_mapel")
    private String id_mapel;
    @SerializedName("id_guru")
    private String id_guru;
    @SerializedName("nama_mapel")
    private String nama_mapel;
    @SerializedName("jurusan")
    private String jurusan;
    @SerializedName("waktu_pelajaran")
    private String waktu_pelajaran;
    @SerializedName("kelas")
    private String kelas;
    @SerializedName("hari")
    private String hari;
    @SerializedName("nama")
    private String nama;
    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String message;

    public String getNomer() {
        return nomer;
    }

    public void setNomer(String nomer) {
        this.nomer = nomer;
    }

    public String getId_mapel() {
        return id_mapel;
    }

    public void setId_mapel(String id_mapel) {
        this.id_mapel = id_mapel;
    }

    public String getId_guru() {
        return id_guru;
    }

    public void setId_guru(String id_guru) {
        this.id_guru = id_guru;
    }

    public String getNama_mapel() {
        return nama_mapel;
    }

    public void setNama_mapel(String nama_mapel) {
        this.nama_mapel = nama_mapel;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getWaktu_pelajaran() {
        return waktu_pelajaran;
    }

    public void setWaktu_pelajaran(String waktu_pelajaran) {
        this.waktu_pelajaran = waktu_pelajaran;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
