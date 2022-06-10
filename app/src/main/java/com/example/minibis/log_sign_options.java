package com.example.minibis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;


public class log_sign_options extends AppCompatActivity {

    Button btncust, btnseller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_sign_options);

        btncust=(Button) findViewById(R.id.csignup);
        btnseller=(Button)findViewById(R.id.ssignup);

        Toast.makeText(log_sign_options.this, "Welcome", Toast.LENGTH_LONG).show();
        btncust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(),signin_cust2.class);
                startActivity(i1);
                finish();
            }
        });
        btnseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getApplicationContext(),signin_sell1.class);
                startActivity(i2);
                finish();
            }
        });
    }

}