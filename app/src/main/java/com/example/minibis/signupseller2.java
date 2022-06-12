package com.example.minibis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signupseller2 extends AppCompatActivity {

    private Button createButton,previousButton;
    private EditText brandOwnerName, brandInstagram, brandOwnerAadhar, brandAddress, brandBankAcc, brandBankIFSC, brandBankUPI;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    private String brandName,brandMobile,brandEmail,brandPass,brandLogo,brandOwnerNameS,brandInstagramS,brandOwnerAadharS,brandBankAccS,brandBankIFSCS,brandBankUPIS,brandAddressS;
    private static int INTERNET_PERMISSION_CODE=999;
    private LottieAnimationView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupseller2);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        loading=(LottieAnimationView) findViewById(R.id.loadingAnimationOnSeller);
        brandOwnerName = (EditText) findViewById(R.id.brandOwnerName);
        brandInstagram = (EditText) findViewById(R.id.brandInstagram);
        brandOwnerAadhar= (EditText) findViewById(R.id.brandOwnerAadhar);
        brandAddress = (EditText) findViewById(R.id.brandRegAddress);
        brandBankAcc = (EditText) findViewById(R.id.brandBankAccountNo);
        brandBankIFSC = (EditText) findViewById(R.id.brandBankAccountIFSC);
        brandBankUPI = (EditText) findViewById(R.id.brandBankUPI);
        createButton= (Button) findViewById(R.id.createSellerAccountFinalButton);
        previousButton = (Button) findViewById(R.id.previousButtonSeller);

        Intent dataIntent=getIntent();
        brandName=dataIntent.getStringExtra("name");
        brandMobile=dataIntent.getStringExtra("mobile");
        brandEmail=dataIntent.getStringExtra("email");
        brandPass=dataIntent.getStringExtra("pass");
        brandLogo=dataIntent.getStringExtra("logo");
//        Log.i("INFO",brandName+" "+brandMobile+" "+brandEmail+" "+brandPass+" "+brandLogo);


        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermission(Manifest.permission.INTERNET,100)){
                    loading.setVisibility(View.VISIBLE);
                    createNewSellerAccountWithInfo();
                }
            }
        });
    }

    private void createNewSellerAccountWithInfo(){

        brandOwnerNameS=brandOwnerName.getText().toString().trim();
        brandInstagramS=brandInstagram.getText().toString().trim();
        brandOwnerAadharS=brandOwnerAadhar.getText().toString().trim();
        brandBankAccS=brandBankAcc.getText().toString().trim();
        brandBankIFSCS=brandBankIFSC.getText().toString().trim();
        brandBankUPIS=brandBankUPI.getText().toString().trim();
        brandAddressS=brandAddress.getText().toString().trim();
        Intent okIntent=new Intent(this,MainActivity.class);

        if(TextUtils.isEmpty(brandInstagramS)){
            Toast.makeText(signupseller2.this, "Error: Instagram Id must not be Empty", Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.INVISIBLE);
        }
        else if(TextUtils.isEmpty(brandOwnerNameS)){
            Toast.makeText(signupseller2.this, "Error: Owner Name must not be Empty", Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.INVISIBLE);
        }
        else if(TextUtils.isEmpty(brandOwnerAadharS)){
            Toast.makeText(signupseller2.this, "Error: Aadhar Number is required", Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.INVISIBLE);
        }
        else if(TextUtils.isEmpty(brandBankAccS)){
            Toast.makeText(signupseller2.this, "Error: Enter Bank account number", Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.INVISIBLE);
        }
        else if(TextUtils.isEmpty(brandBankIFSCS)){
            Toast.makeText(signupseller2.this, "Error: Enter Bank IFSC Code", Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.INVISIBLE);
        }
        else if(TextUtils.isEmpty(brandBankUPIS)){
            Toast.makeText(signupseller2.this, "Error: UPI Id is Mandatory", Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.INVISIBLE);
        }
        else if(TextUtils.isEmpty(brandAddressS)){
            Toast.makeText(signupseller2.this, "Error: Enter registered Address for brand", Toast.LENGTH_SHORT).show();
            loading.setVisibility(View.INVISIBLE);
        }
        else{
            firebaseAuth = FirebaseAuth.getInstance();
            if(firebaseAuth.getCurrentUser()==null){
                firebaseAuth.createUserWithEmailAndPassword(brandEmail,brandPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser currentUser=firebaseAuth.getCurrentUser();
                            Log.d("","Brand Account Created Successfully: "+currentUser.getEmail());
                            Map<String,Object> userInfo=new HashMap<>();
                            userInfo.put("Email",currentUser.getEmail());
                            userInfo.put("isSeller",true);
                            userInfo.put("BrandName",brandName);
                            userInfo.put("BrandLogo",brandLogo);
                            userInfo.put("BrandMobileNumber",brandMobile);
                            userInfo.put("BrandOwnerName",brandOwnerNameS);
                            userInfo.put("BrandOwnerAadhar",brandOwnerAadharS);
                            userInfo.put("BrandInstagram",brandInstagramS);
                            userInfo.put("BrandAddress",brandAddressS);
                            userInfo.put("BrandBankAcc",brandBankAccS);
                            userInfo.put("BrandBankIFSC",brandBankIFSCS);
                            userInfo.put("BrandBankUPI",brandBankUPIS);
                            firestore= FirebaseFirestore.getInstance();
                            firestore.collection("Users").document(currentUser.getUid()).set(userInfo)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d("Done","Data Entered Successfully Uid: "+currentUser.getUid());
                                            Toast.makeText(signupseller2.this, "Account Created and Logged In", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("Failed","Data Creation Failed",e);
                                            Toast.makeText(signupseller2.this, "To Update Information Visit Edit Profile Page", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            finishAffinity();
                            startActivity(okIntent);
                            finish();
                        }
                        else{
                            if(CommonUtility.isInternetAvailable()){
                                Log.w("","Account Cannot be Created: ", task.getException());
                                Toast.makeText(signupseller2.this, "Sign Up Failed: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                Toast.makeText(signupseller2.this,"Please check you internet connection",Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.INVISIBLE);
                            }

                        }
                    }
                });
            }
        }
    }
    public boolean checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] { permission }, requestCode);
        }
        else {
            return true;
        }
        return false;
    }
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == INTERNET_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createNewSellerAccountWithInfo();
            } else {
                Toast.makeText(this, "Error: You need to grant INTERNET permission to Create Account", Toast.LENGTH_SHORT).show();
            }
        }
    }
}