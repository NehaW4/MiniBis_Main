package com.example.minibis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class cloth_subcateg extends AppCompatActivity {

    ConstraintLayout view1,view2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_subcateg);
        view1=(ConstraintLayout) findViewById(R.id.westernClothingCategory);
        view2=(ConstraintLayout) findViewById(R.id.ethnicClothingCategory);

        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent okIntent=new Intent(cloth_subcateg.this,ProductListDisplay.class);
                okIntent.putExtra("category",0);
                startActivity(okIntent);
            }
        });
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent okIntent=new Intent(cloth_subcateg.this,ProductListDisplay.class);
                okIntent.putExtra("category",1);
                startActivity(okIntent);
            }
        });
    }
}