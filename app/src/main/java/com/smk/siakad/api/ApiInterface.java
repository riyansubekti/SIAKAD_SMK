package com.smk.siakad.api;

import com.smk.siakad.model.JadwalSiswa;
import com.smk.siakad.model.Login;
import com.smk.siakad.model.Nilai;
import com.smk.siakad.model.Pengumuman;
import com.smk.siakad.model.Siswa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("siswa.php")
    Call<List<Siswa>> getSiswa();

    @GET("login.php")
    Call<Login> performUserLogin(@Query("username") String username, @Query("password") String Password);

    @GET("get_siswa.php")
    Call<List<Siswa>> loadSiswa(@Query("role") String role, @Query("username") String username);

    @GET("get_pengumuman.php")
    Call<List<Pengumuman>> getPeng(@Query("role") String role);

    @GET("get_nilai.php")
    Call<List<Nilai>> getNilai(@Query("id_siswa") String id_siswa);

    @FormUrlEncoded
    @POST("delete_nilai.php")
    Call<Nilai> deleteNilai(
            @Field("key") String key,
            @Field("id_nilai") String id_nilai
    );

    @FormUrlEncoded
    @POST("delete_jadwal.php")
    Call<JadwalSiswa> deleteJadwal(
            @Field("key") String key,
            @Field("id_mapel") String id_mapel
    );

    @FormUrlEncoded
    @POST("update_nilai.php")
    Call<Nilai> updateNilai(
            @Field("key") String key,
            @Field("id_nilai") String id_nilai,
            @Field("id_mapel") String id_mapel,
            @Field("id_siswa") String id_siswa,
            @Field("tugas") String tugas,
            @Field("uts") String uts,
            @Field("uas") String uas,
            @Field("kkm") String kkm,
            @Field("semester") String semester
    );

    @FormUrlEncoded
    @POST("update_jadwal.php")
    Call<JadwalSiswa> updateJadwal(
            @Field("key") String key,
            @Field("id_mapel") String id_mapel,
            @Field("id_guru") String id_guru,
            @Field("nama_mapel") String nama_mapel,
            @Field("jurusan") String jurusan,
            @Field("kelas") String kelas,
            @Field("waktu_pelajaran") String waktu_pelajaran,
            @Field("hari") String hari
    );

    @FormUrlEncoded
    @POST("insert_nilai.php")
    Call<Nilai> insertNilai(
            @Field("key") String key,
            @Field("id_mapel") String id_mapel,
            @Field("id_siswa") String id_siswa,
            @Field("tugas") String tugas,
            @Field("uts") String uts,
            @Field("uas") String uas,
            @Field("kkm") String kkm,
            @Field("semester") String semester
    );

    @FormUrlEncoded
    @POST("insert_jadwal.php")
    Call<JadwalSiswa> insertJadwal(
            @Field("key") String key,
            @Field("id_guru") String id_guru,
            @Field("nama_mapel") String nama_mapel,
            @Field("jurusan") String jurusan,
            @Field("kelas") String kelas,
            @Field("waktu_pelajaran") String waktu_pelajaran,
            @Field("hari") String hari
    );

    @GET("get_jadwal_siswa.php")
    Call<List<JadwalSiswa>> getJadwalSiswa(@Query("kelas") String kelas);

    @FormUrlEncoded
    @POST("insert_siswa.php")
    Call<Siswa> insertSiswa(
            @Field("key") String key,
            @Field("username") String username,
            @Field("password") String password,
            @Field("role") String role,
            @Field("id_siswa") String id_siswa,
            @Field("nama") String nama,
            @Field("kelas") String kelas,
            @Field("alamat") String alamat,
            @Field("jurusan") String jurusan,
            @Field("tagihan") String tagihan,
            @Field("foto") String foto
    );

    @FormUrlEncoded
    @POST("update_siswa.php")
    Call<Siswa> updateSiswa(
            @Field("key") String key,
            @Field("id_siswa") String id_siswa,
            @Field("password") String password,
            @Field("nama") String nama,
            @Field("kelas") String kelas,
            @Field("alamat") String alamat,
            @Field("jurusan") String jurusan,
            @Field("tagihan") String tagihan,
            @Field("foto") String foto
    );

    @FormUrlEncoded
    @POST("delete_siswa.php")
    Call<Siswa> deleteSiswa(
            @Field("key") String key,
            @Field("id_siswa") String id_siswa,
            @Field("foto") String foto
    );

    @FormUrlEncoded
    @POST("insert_daftarulang.php")
    Call<Siswa> insertDU(
            @Field("id_siswa") String id_siswa,
            @Field("kelas") String kelas,
            @Field("tagihan") String tagihan
    );
}
