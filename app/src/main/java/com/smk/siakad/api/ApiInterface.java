package com.smk.siakad.api;

import com.smk.siakad.model.JadwalSiswa;
import com.smk.siakad.model.Login;
import com.smk.siakad.model.Nilai;
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

    @GET("get_nilai.php")
    Call<List<Nilai>> getNilai(@Query("id_siswa") String id_siswa);

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
}
