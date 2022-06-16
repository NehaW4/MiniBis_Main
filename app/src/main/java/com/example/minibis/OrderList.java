package com.example.minibis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.minibis.Adapter.Order;
import com.example.minibis.Adapter.OrderListRecyclerAdapter;
import com.example.minibis.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OrderList extends AppCompatActivity {

    FirebaseUser currentUser;
    RecyclerView recycler;
    TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser==null){
            finish();
            return;
        }

        recycler=(RecyclerView) findViewById(R.id.orderListRecycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        empty=(TextView) findViewById(R.id.orderListEmpty);

        ArrayList<Order> orderList=new ArrayList<Order>();
        FirebaseFirestore.getInstance().collection("Orders").whereEqualTo("CustomerId",currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                        OrderListRecyclerAdapter adapter=new OrderListRecyclerAdapter(orderList,OrderList.this);
                        recycler.setAdapter(adapter);
                    }
                }
                else{
                    empty.setVisibility(View.VISIBLE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                empty.setVisibility(View.VISIBLE);
            }
        });
    }
}