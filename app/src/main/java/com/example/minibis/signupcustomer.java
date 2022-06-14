package com.example.minibis;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signupcustomer extends AppCompatActivity {

    private EditText cfpass, cpass, fname, lname, mob, email;
    private Button btnsignup;
    private FirebaseFirestore firestore ;
    private FirebaseAuth firebaseAuth;
    private RadioGroup genderRadio;
    private String gender;
    private RadioButton male,fmale;
    private LottieAnimationView loading;

//    private FirebaseDatabase db = FirebaseDatabase.getInstance();
//    private DatabaseReference root = db.getReference().child("Customer");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupcustomer);

//        Toast.makeText(signupcustomer.this, "Sign In", Toast.LENGTH_LONG).show();

        fname = (EditText) findViewById(R.id.cfirstname);
        lname = (EditText) findViewById(R.id.clastname);
        mob = (EditText) findViewById(R.id.cmobile);
        email = (EditText) findViewById(R.id.cemailid);
        genderRadio=(RadioGroup) findViewById(R.id.genderRadioGroupOnCustomer);
        cpass = (EditText) findViewById(R.id.createPassword);
        cfpass = (EditText) findViewById(R.id.confirmPassword);
        btnsignup = (Button) findViewById(R.id.createAccountForCustomer);
        fmale=(RadioButton) findViewById(R.id.femaleGender);
        male=(RadioButton) findViewById(R.id.maleGender);
        loading=(LottieAnimationView) findViewById(R.id.loadingAnimationOnCustomer);

        Intent okIntent=new Intent(this,MainActivity.class);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loading.setVisibility(View.VISIBLE);
                String firstname = fname.getText().toString().trim();
                String lastname = lname.getText().toString().trim();
                String mobile = mob.getText().toString().trim();
                String cemail = email.getText().toString().trim();
                String pass = cpass.getText().toString().trim();
                String confirmpass = cfpass.getText().toString().trim();
                if(!male.isChecked() && !fmale.isChecked()){
                    Toast.makeText(signupcustomer.this, "Error: Select your Gender", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.INVISIBLE);
                }
                else if(male.isChecked()){
                    gender="Male";
                }
                else{
                    gender="Female";
                }

                if(TextUtils.isEmpty(firstname)){
                    Toast.makeText(signupcustomer.this, "Error: FirstName cannot be Empty", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.INVISIBLE);
                }
                else if(TextUtils.isEmpty(lastname)) {
                    Toast.makeText(signupcustomer.this, "Error: LastName cannot be Empty", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.INVISIBLE);
                }
                else if(TextUtils.isEmpty(mobile)) {
                    Toast.makeText(signupcustomer.this, "Error: Mobile Number cannot be Empty", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.INVISIBLE);
                }
                else if(TextUtils.isEmpty(cemail)) {
                    Toast.makeText(signupcustomer.this, "Error: Email cannot be Empty", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.INVISIBLE);
                }
                else if(TextUtils.isEmpty(pass)) {
                    Toast.makeText(signupcustomer.this, "Error: Password cannot be Empty", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.INVISIBLE);
                }
                else if(TextUtils.isEmpty(confirmpass)) {
                    Toast.makeText(signupcustomer.this, "Error: Confirm Password cannot be Empty", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.INVISIBLE);
                }
                else if(!pass.equals(confirmpass)){
                    Toast.makeText(signupcustomer.this, "Error: Password and Confirm Password does not match", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.INVISIBLE);
                }
                else{
                    firebaseAuth = FirebaseAuth.getInstance();
                    if(firebaseAuth.getCurrentUser()==null){
                        firebaseAuth.createUserWithEmailAndPassword(cemail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser currentUser=firebaseAuth.getCurrentUser();
                                    Log.d("","Account Created Successfully: "+currentUser.getEmail());
                                    Map<String,Object> userInfo=new HashMap<>();
                                    userInfo.put("Email",currentUser.getEmail());
                                    userInfo.put("isSeller",false);
                                    userInfo.put("FirstName",firstname);
                                    userInfo.put("LastName",lastname);
                                    userInfo.put("MobileNumber",mobile);
                                    userInfo.put("Gender",gender);
                                    userInfo.put("Profilepic",null);
                                    firestore=FirebaseFirestore.getInstance();
                                    firestore.collection("Users").document(currentUser.getUid()).set(userInfo)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Log.d("Done","Data Entered Successfully Uid: "+currentUser.getUid());
                                                            Toast.makeText(signupcustomer.this, "Account Created and Logged In", Toast.LENGTH_SHORT).show();
                                                        }
                                                    })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    loading.setVisibility(View.INVISIBLE);
                                                                    Log.w("Failed","Data Creation Failed",e);
                                                                    Toast.makeText(signupcustomer.this, "To Update Information Visit Edit Profile Page", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                    finishAffinity();
                                    startActivity(okIntent);
                                    finish();
                                }
                                else{
                                    Log.w("","Account Cannot be Created: ", task.getException());
                                    Toast.makeText(signupcustomer.this, "Sign Up Failed: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    loading.setVisibility(View.INVISIBLE);
//                                    startActivity(failedIntent);
//                                    finish();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

}