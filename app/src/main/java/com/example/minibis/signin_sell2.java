package com.example.minibis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signin_sell2 extends AppCompatActivity {

    private EditText name, mob, email, address, adhaar, acc, ifsc;
    private FirebaseDatabase auth;

    private FirebaseDatabase db2 = FirebaseDatabase.getInstance();
    private DatabaseReference root2 = db2.getReference().child("Seller Details");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_sell2);


        name = findViewById(R.id.sname);
        mob = findViewById(R.id.smobile);
        email = findViewById(R.id.semailid);
        address = findViewById(R.id.saddress);
        acc = findViewById(R.id.sbankacc);
        ifsc = findViewById(R.id.sifsccode);
        adhaar = findViewById(R.id.sadhaarid);

        Button btnsnext = findViewById(R.id.snext);

        btnsnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(signin_sell2.this, login_in_seller.class);
                startActivity(intent4);

                String sname = name.getText().toString();
                String smobile = mob.getText().toString();
                String semail = email.getText().toString();
                String saddress = address.getText().toString();
                String sacc = acc.getText().toString();
                String sifsc = ifsc.getText().toString();
                String sadhaar = adhaar.getText().toString();


               /* HashMap<String, String> userMap = new HashMap<>();

                userMap.put("Seller Name", sname);
                userMap.put("Seller Mobile", smobile);
                userMap.put("Seller Email", semail);
                userMap.put("Seller Address", saddress);
                userMap.put("Seller Aadhaar", sadhaar);
                userMap.put("Seller Account", sacc);
                userMap.put("Seller IFSC", sifsc);

                root2.push().setValue(userMap);*/

            }
        });
    }
}