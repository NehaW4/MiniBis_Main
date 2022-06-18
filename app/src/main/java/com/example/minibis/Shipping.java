package com.example.minibis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minibis.Adapter.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Shipping extends AppCompatActivity {

    TextView name,status,desc,price,transaction,address,sellerName,sellerEmail,sellerInstagram,date;
    ImageView image,seller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);

        name=(TextView) findViewById(R.id.shippingOrderName);
        status=(TextView) findViewById(R.id.shippingOrderStatus);
        desc=(TextView) findViewById(R.id.shippingOrderDesc);
        image=(ImageView) findViewById(R.id.shippingOrderImage);
        price=(TextView) findViewById(R.id.shippinOrderPrice);
        transaction=(TextView) findViewById(R.id.shippingPageTransactionNo);
        address=(TextView) findViewById(R.id.shippingPageAddress);
        sellerName=(TextView) findViewById(R.id.shippingPageSellerName);
        sellerEmail=(TextView) findViewById(R.id.shippingPageSellerEmail);
        sellerInstagram=(TextView) findViewById(R.id.shippingPageSellerInstagram);
        seller=(ImageView) findViewById(R.id.shippingPageSellerPhoto);
        date=(TextView) findViewById(R.id.shippingPageOrderDate);

        Intent callingIntent=getIntent();
        Order order=(Order) callingIntent.getSerializableExtra("order");
        String s=order.getStatus();
        name.setText(order.getProductName());
        status.setText("Status: "+ s);
        image.setImageBitmap(ImageStringOperation.getImage(order.getProductImage()));
        price.setText("Price: "+order.getProductPrice()+" Rs");
        transaction.setText("Tr No: "+order.getTransactionNo());
        address.setText("Address: "+ order.getDeliveryAddress());
        date.setText("Order Date: "+new SimpleDateFormat("dd/MM/yyyy").format(order.getOrderDate()));

        FirebaseFirestore.getInstance().collection("Users").document(order.getSellerId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc=task.getResult();
                    if(doc.exists()){
                        seller.setImageBitmap(ImageStringOperation.getImage(doc.getString("BrandLogo")));
                        sellerName.setText(doc.getString("BrandName"));
                        sellerEmail.setText("Email: "+doc.getString("Email"));
                        sellerInstagram.setText("Instagram: "+doc.getString("BrandInstagram"));
                    }
                    else{
                        Toast.makeText(Shipping.this, "Seller Info Fetch Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Shipping.this, "Seller Info Fetch Failed", Toast.LENGTH_SHORT).show();
            }
        });

        if(s.equals("Pending")){
            desc.setText("The order is yet to be checked by the Seller. Once the payment is verified, the order will be delivered to your address in 2-3 working days.");
        }
        else if(s.equals("Approved")){
            desc.setText("The order is approved by the Seller, the order will be delivered to your address in 2-3 working days.");
        }
        else{
            desc.setText("The order is rejected by seller. Possible reasons are Invalid Payment, Insufficient Supply, Unrecheable Address. To refund the payment get in touch with our helpdesk by visiting Contact Us Page");
        }

    }
}