package com.example.minibis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minibis.Adapter.Order;

public class Shipping extends AppCompatActivity {

    TextView name,status,desc;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);

        name=(TextView) findViewById(R.id.shippingOrderName);
        status=(TextView) findViewById(R.id.shippingOrderStatus);
        desc=(TextView) findViewById(R.id.shippingOrderDesc);
        image=(ImageView) findViewById(R.id.shippingOrderImage);

        Intent callingIntent=getIntent();
        Order order=(Order) callingIntent.getSerializableExtra("order");
        String s=order.getStatus();
        name.setText(order.getProductName());
        status.setText("Status: "+ s);
        image.setImageBitmap(ImageStringOperation.getImage(order.getProductImage()));
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