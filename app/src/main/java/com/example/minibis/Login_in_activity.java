package com.example.minibis;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login_in_activity extends AppCompatActivity {

    Button login;
    EditText username,password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in_activity);

        login = findViewById(R.id.loginButtonFromLoginPage);
        username=(EditText) findViewById(R.id.login_id);
        password=(EditText) findViewById(R.id.login_pass);

        Intent okIntent = new Intent(Login_in_activity.this,MainActivity.class);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname=username.getText().toString().trim();
                String pass=password.getText().toString().trim();

                if(TextUtils.isEmpty(uname)){
                    Toast.makeText(getApplicationContext(),"Error: Username cannot be Empty",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(pass)){
                    Toast.makeText(Login_in_activity.this, "Error: Password cannot be Empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    firebaseAuth = FirebaseAuth.getInstance();
                    if(firebaseAuth.getCurrentUser()==null){
                        firebaseAuth.signInWithEmailAndPassword(uname,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Login_in_activity.this, "Sign in Success. Redirecting", Toast.LENGTH_SHORT).show();
                                    Log.d("","Login Successful UID: "+firebaseAuth.getCurrentUser().getEmail());
                                    startActivity(okIntent);
                                    finish();
                                }
                                else{
                                    Log.w("","Login Failure: ", task.getException());
                                    password.setText("");
                                    Toast.makeText(Login_in_activity.this, "Login Failed: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }
}











