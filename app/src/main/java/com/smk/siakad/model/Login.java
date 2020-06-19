package com.smk.siakad.model;

import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("response")
    private String Response;

    @SerializedName("role")
    private String Role;

    public String getResponse() {
        return Response;
    }

    public String getRole() {
        return Role;
    }
}
