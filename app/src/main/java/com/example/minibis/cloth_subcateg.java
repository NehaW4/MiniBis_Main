package com.example.minibis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class cloth_subcateg extends AppCompatActivity {

    Button view1,view2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_subcateg);
        view1=(Button) findViewById(R.id.view_western);
        view2=(Button) findViewById(R.id.view_ethnic);

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(cloth_subcateg.this, cloth_western.class);
                startActivity(i1);
            }
        });
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2=new Intent(cloth_subcateg.this, cloth_ethnic.class);
                startActivity(i2);
            }
        });
    }
}