package com.smk.siakad.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.smk.siakad.BerandaFragment;
import com.smk.siakad.PrefConfig;
import com.smk.siakad.R;
import com.smk.siakad.SiswaFragment;
import com.smk.siakad.api.ApiClient;
import com.smk.siakad.api.ApiInterface;

public class LoginActivity extends AppCompatActivity implements LoginFragment.OnLoginFormActivityListener, SiswaFragment.OnLogoutListener {

    public static PrefConfig prefConfig;
    public static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefConfig = new PrefConfig(this);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            if (prefConfig.readLoginStatus()) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new SiswaFragment()).commit();
            } else {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new LoginFragment()).commit();
            }
        }
    }

    @Override
    public void performLogin(String role, String username) {
        prefConfig.writeRole(role);
        prefConfig.writeID(username);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SiswaFragment()).commit();
    }

    @Override
    public void logoutPerformed() {
        prefConfig.writeLoginStatus(false);
        prefConfig.writeRole("Role");
        prefConfig.writeID("Username");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment()).commit();
    }
}
