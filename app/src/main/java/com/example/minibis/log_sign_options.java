package com.example.minibis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;


public class log_sign_options extends AppCompatActivity {

    Button btncust, btnseller,loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_sign_options);

        loginBtn=(Button) findViewById(R.id.loginButton);
        btncust=(Button) findViewById(R.id.csignup);
        btnseller=(Button)findViewById(R.id.ssignup);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Log.d("Info",FirebaseAuth.getInstance().getCurrentUser().getEmail());
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }

//        Toast.makeText(log_sign_options.this, "Welcome", Toast.LENGTH_LONG).show();
        btncust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), signupcustomer.class);
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
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Login_in_activity.class);
                startActivity(i);
                finish();
            }
        });
    }

}