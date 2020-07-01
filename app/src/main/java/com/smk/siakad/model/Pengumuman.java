package com.smk.siakad.model;

import com.google.gson.annotations.SerializedName;

public class Pengumuman {
    @SerializedName("id_pengumuman")
    private String id_pengumuman;
    @SerializedName("judul")
    private String judul;
    @SerializedName("deskripsi")
    private String deskripsi;
    @SerializedName("penerima")
    private String penerima;

    public String getId_pengumuman() {
        return id_pengumuman;
    }

    public void setId_pengumuman(String id_pengumuman) {
        this.id_pengumuman = id_pengumuman;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getPenerima() {
        return penerima;
    }

    public void setPenerima(String penerima) {
        this.penerima = penerima;
    }
}
