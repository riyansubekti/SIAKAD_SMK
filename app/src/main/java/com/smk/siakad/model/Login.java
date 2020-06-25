package com.smk.siakad.model;

import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("response")
    private String Response;

    @SerializedName("role")
    private String Role;

    @SerializedName("id_login")
    private String id_login;

    public String getResponse() {
        return Response;
    }

    public String getRole() {
        return Role;
    }

    public String getId_login() {
        return id_login;
    }
}
