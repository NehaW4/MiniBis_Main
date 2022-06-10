package com.example.minibis;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login_in_acitvity extends AppCompatActivity {

    Button log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in_acitvity);

        log = findViewById(R.id.loginfinal);

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent intent7 = new Intent(Login_in_acitvity.this,Bottom_Navigation.class);
                Intent intent8 = new Intent(Login_in_acitvity.this,MainActivity.class);
                //Intent intent7 = new Intent(getApplicationContext(),activity_edit_profile_page.class);
                startActivity(intent8);

            }

        });

        /*FirebaseAuth firebaseAuth;
        FirebaseAuth.AuthStateListener mAuthListener;
        firebaseAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public  void  onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(Login_in_acitvity.this,log_sign_options.class);
                    startActivity(intent);
                    finish();
                }
            }


        };*/
    }
}











