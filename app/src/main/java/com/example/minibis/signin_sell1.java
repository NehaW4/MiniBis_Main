package com.example.minibis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class signin_sell1 extends AppCompatActivity {

    Button btnnext;
    private EditText binsta, brandname, bmob, bemail, baddress;
    private FirebaseDatabase auth;
    private FirebaseDatabase db1 = FirebaseDatabase.getInstance();
    private DatabaseReference root1 = db1.getReference().child("Brand Details");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_sell1);

        binsta = findViewById(R.id.binstaid);
        brandname = findViewById(R.id.bbrandname);
        bmob = findViewById(R.id.bmobile);
        bemail = findViewById(R.id.bemailid);
        baddress = findViewById(R.id.baddress);
        btnnext = findViewById(R.id.nextButton);

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(signin_sell1.this,signin_sell2.class);
                startActivity(intent3);

                String instaID = binsta.getText().toString();
                String name = brandname.getText().toString();
                String mobile = bmob.getText().toString();
                String email = bemail.getText().toString();
                String address = baddress.getText().toString();


              /*  HashMap<String, String> userMap = new HashMap<>();

                userMap.put("Brand ID", instaID);
                userMap.put("Brand Name", name);
                userMap.put("Brand Mobile", mobile);
                userMap.put("Brand Email", email);
                userMap.put("Brand Address", address);

                root1.push().setValue(userMap);*/

            }
        });


    }
}