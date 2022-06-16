package com.example.minibis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.minibis.Adapter.Order;
import com.example.minibis.Adapter.OrderRequestRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OrderRequests extends AppCompatActivity {

    FirebaseUser currentUser;
    RecyclerView recycler;
    TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_requests);

        Intent callingIntent=getIntent();
        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser==null || !callingIntent.getBooleanExtra("isSeller",false)){
            finish();
            return;
        }

        recycler=(RecyclerView) findViewById(R.id.orderRequestRecycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        empty=(TextView) findViewById(R.id.orderRequestEmpty);

        ArrayList<Order> orderList=new ArrayList<Order>();
        FirebaseFirestore.getInstance().collection("Orders").whereEqualTo("SellerId",currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot doc:task.getResult()){
                        orderList.add((Order) doc.toObject(Order.class));
                    }
                    if(orderList.size()==0){
                        empty.setVisibility(View.VISIBLE);
                    }
                    else{

//                        Log.i("DATAAAAAAAA",orderList.get(0).getProductName());
                        OrderRequestRecyclerAdapter adapter=new OrderRequestRecyclerAdapter(orderList,OrderRequests.this);
                        recycler.setAdapter(adapter);
                    }
                }
            }
        });

    }
}