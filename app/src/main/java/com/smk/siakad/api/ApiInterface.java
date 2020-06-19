package com.smk.siakad.api;

import com.smk.siakad.model.Login;
import com.smk.siakad.model.Siswa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("siswa.php")
    Call<List<Siswa>> getSiswa();

    @GET("login.php")
    Call<Login> performUserLogin(@Query("username") String username, @Query("password") String Password);
}
