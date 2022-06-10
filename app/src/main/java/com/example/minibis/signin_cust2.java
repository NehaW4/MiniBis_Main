package com.example.minibis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;

public class signin_cust2<FirebaseFirestore, FirebaseAuth> extends AppCompatActivity {

    private EditText cid, cpass, fname, lname, mob, email, address;
    private Button btnsignup;
    private FirebaseDatabase mAuth;
    private FirebaseFirestore firestore ;
    private FirebaseAuth AuthListener;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Customer");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_cust2);

        Toast.makeText(signin_cust2.this, "Sign In", Toast.LENGTH_LONG).show();

        cid = findViewById(R.id.cusername);
        cpass = findViewById(R.id.cpassword);
        fname = findViewById(R.id.cfirstname);
        lname = findViewById(R.id.clastname);
        mob = findViewById(R.id.cmobile);
        email = findViewById(R.id.cemailid);
        address = findViewById(R.id.caddress);
        btnsignup = findViewById(R.id.cbutton);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(signin_cust2.this,Login_in_acitvity.class);
               // Intent intent1 = new Intent(signin_cust2.this,activity_edit_profile_page.class);
                startActivity(intent1);

                String id = cid.getText().toString();
                String pass = cpass.getText().toString();
                String firstname = fname.getText().toString();
                String lastname = lname.getText().toString();
                String mobile = mob.getText().toString();
                String cemail = email.getText().toString();
                String caddress = address.getText().toString();


           /*     HashMap<String, String> userMap = new HashMap<>();

                userMap.put("ID", id);
                userMap.put("Pass", pass);
                userMap.put("First Name", firstname);
                userMap.put("Last Name", lastname);
                userMap.put("Mobile", mobile);
                userMap.put("Email", cemail);
                userMap.put("Address", caddress);

                root.push().setValue(userMap);*/

            }
        });
    }

}