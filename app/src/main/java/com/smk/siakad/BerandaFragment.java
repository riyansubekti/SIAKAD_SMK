package com.smk.siakad;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.smk.siakad.login.LoginActivity;

public class BerandaFragment extends Fragment {

    private TextView txtRole;
    private Button btnLogout;
//    OnLogoutListener logoutListener;
//
//    public interface OnLogoutListener{
//        public void logoutPerformed();
//    }

    public BerandaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);
        btnLogout = view.findViewById(R.id.btnLogout);
        txtRole = view.findViewById(R.id.txtROLE);
        txtRole.setText("Selamat Datang "+ LoginActivity.prefConfig.readName());
//
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                logoutListener.logoutPerformed();
//            }
//        });
        return view;
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        Activity activity = (Activity) context;
//        logoutListener = (OnLogoutListener) activity;
//    }
}
