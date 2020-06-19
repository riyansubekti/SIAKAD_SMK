package com.smk.siakad.model;

import com.google.gson.annotations.SerializedName;

public class Siswa {
    @SerializedName("id_siswa")
    private String id_siswa;
    @SerializedName("id_login")
    private String id_login;
    @SerializedName("nama")
    private String nama;
    @SerializedName("kelas")
    private String kelas;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("jurusan")
    private String jurusan;
    @SerializedName("tagihan")
    private String tagihan;
    @SerializedName("foto")
    private String foto;
    @SerializedName("message")
    private String massage;

    public String getId_siswa() {
        return id_siswa;
    }

    public void setId_siswa(String id_siswa) {
        this.id_siswa = id_siswa;
    }

    public String getId_login() {
        return id_login;
    }

    public void setId_login(String id_login) {
        this.id_login = id_login;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJurusan() {
        return jurusan;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public String getTagihan() {
        return tagihan;
    }

    public void setTagihan(String tagihan) {
        this.tagihan = tagihan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
