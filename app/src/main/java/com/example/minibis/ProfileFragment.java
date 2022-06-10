package com.example.minibis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ProfileFragment extends Fragment {

    Button log_profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        log_profile = (Button) view.findViewById(R.id.login_btn);

        log_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent10 = new Intent(view.getContext(),log_sign_options.class);
                startActivity(intent10);
                ((Activity)getActivity()).overridePendingTransition(0,0);

            }
        });

        return view;
    }
}