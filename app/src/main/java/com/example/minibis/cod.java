package com.example.minibis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class cod extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cod);
    }
    public void backtocart(View view)
    {
        startActivity(new Intent(getApplicationContext(),CustomerCart.class));
        finish();
    }
    public void proccedtoshipping(View view)
    {
        startActivity(new Intent(getApplicationContext(),Shipping.class));
        finish();
    }
    public void payupi(View view)
    {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}