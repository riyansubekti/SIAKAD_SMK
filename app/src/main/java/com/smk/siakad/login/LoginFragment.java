package com.smk.siakad.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.smk.siakad.R;
import com.smk.siakad.model.Login;

public class LoginFragment extends Fragment {

    OnLoginFormActivityListener loginFormActivityListener;
    private EditText txtUID, txtPASS;
    private Button btnLogin;

    public interface OnLoginFormActivityListener {
        public void performLogin(String role, String id_login);

    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        txtUID = view.findViewById(R.id.txtUID);
        txtPASS = view.findViewById(R.id.txtPASS);
        btnLogin = view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        loginFormActivityListener = (OnLoginFormActivityListener) activity;
    }

    private void performLogin () {
        String username = txtUID.getText().toString();
        String password = txtPASS.getText().toString();

        Call<Login> call = LoginActivity.apiInterface.performUserLogin(username,password);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.body().getResponse().equals("ok")) {
                    LoginActivity.prefConfig.writeLoginStatus(true);
                    loginFormActivityListener.performLogin(response.body().getRole(), response.body().getId_login());
                    System.out.println("okeh123"+response.body().getRole()+response.body().getId_login());
                } else if (response.body().getResponse().equals("failed")) {
                    LoginActivity.prefConfig.displayToast("Username atau Password Salah...");
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });

        txtUID.setText("");
        txtPASS.setText("");
    }
}
