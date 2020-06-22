package com.smk.siakad.model;

import com.google.gson.annotations.SerializedName;

public class Nilai {
    @SerializedName("nomer")
    private String nomer;
    @SerializedName("nama_mapel")
    private String nama_mapel;
    @SerializedName("nama")
    private String nama;
    @SerializedName("tugas")
    private String tugas;
    @SerializedName("uts")
    private String uts;
    @SerializedName("uas")
    private String uas;
    @SerializedName("nilai")
    private String nilai;
    @SerializedName("kkm")
    private String kkm;
    @SerializedName("status")
    private String status;
    @SerializedName("semester")
    private String semester;
    @SerializedName("message")
    private String massage;

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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTugas() {
        return tugas;
    }

    public void setTugas(String tugas) {
        this.tugas = tugas;
    }

    public String getUts() {
        return uts;
    }

    public void setUts(String uts) {
        this.uts = uts;
    }

    public String getUas() {
        return uas;
    }

    public void setUas(String uas) {
        this.uas = uas;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    public String getKkm() {
        return kkm;
    }

    public void setKkm(String kkm) {
        this.kkm = kkm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
