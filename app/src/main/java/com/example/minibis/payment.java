package com.example.minibis;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.minibis.Adapter.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.EventListener;

public class payment extends AppCompatActivity {

    QuerySnapshot allProductsInCart;
    String priceup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         TextView price=(TextView) findViewById(R.id.amounttopay);
        FirebaseFirestore firestore= FirebaseFirestore.getInstance();
        DocumentReference df=firestore.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("Cart").document();


            setContentView(R.layout.activity_payment);
    }
    public void paycod(View view)
    {
        startActivity(new Intent(getApplicationContext(),cod.class));
        finish();
    }
    public void payupi(View view)
    {
        startActivity(new Intent(getApplicationContext(),upipay.class));
        finish();
    }
    public void backtocart(View view)
    {
        startActivity(new Intent(getApplicationContext(),CustomerCart.class));
        finish();
    }
}