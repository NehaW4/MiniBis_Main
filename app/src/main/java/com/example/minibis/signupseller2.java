package com.example.minibis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signupseller2 extends AppCompatActivity {

    private Button createButton,previousButton;
    private EditText brandOwnerName, brandInstagram, brandOwnerAadhar, brandAddress, brandBankAcc, brandBankIFSC, brandBankUPI;
    private FirebaseDatabase auth;
    String brandName,brandMobile,brandEmail,brandPass,brandLogo,brandOwnerNameS,brandInstagramS,brandOwnerAadharS,brandBankAccS,brandBankIFSCS,brandBankUPIS,brandAddressS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupseller2);

        brandOwnerName = (EditText) findViewById(R.id.brandOwnerName);
        brandInstagram = (EditText) findViewById(R.id.brandInstagram);
        brandOwnerAadhar= (EditText) findViewById(R.id.brandOwnerAadhar);
        brandAddress = (EditText) findViewById(R.id.brandRegAddress);
        brandBankAcc = (EditText) findViewById(R.id.brandBankAccountNo);
        brandBankIFSC = (EditText) findViewById(R.id.brandBankAccountIFSC);
        brandBankAcc = (EditText) findViewById(R.id.brandBankUPI);
        createButton= (Button) findViewById(R.id.createSellerAccountFinalButton);
        previousButton = (Button) findViewById(R.id.previousButtonSeller);

        Intent dataIntent=getIntent();
        brandName=dataIntent.getStringExtra("name");
        brandMobile=dataIntent.getStringExtra("mobile");
        brandEmail=dataIntent.getStringExtra("email");
        brandPass=dataIntent.getStringExtra("pass");
        brandLogo=dataIntent.getStringExtra("logo");
//        Log.i("INFO",brandName+" "+brandMobile+" "+brandEmail+" "+brandPass+" "+brandLogo);

        Intent okIntent=new Intent(this,MainActivity.class);

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                brandOwnerNameS=brandOwnerName.getText().toString().trim();
                brandInstagramS=brandInstagram.getText().toString().trim();
                brandOwnerAadharS=brandOwnerAadhar.getText().toString().trim();
                brandBankAccS=brandBankAcc.getText().toString().trim();
                brandBankIFSCS=brandBankIFSC.getText().toString().trim();
                brandBankUPIS=brandBankUPI.getText().toString().trim();

            }
        });
    }
}