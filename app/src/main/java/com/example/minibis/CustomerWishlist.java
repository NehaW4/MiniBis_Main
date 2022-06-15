package com.example.minibis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.minibis.Adapter.CartProductListRecyclerAdapter;
import com.example.minibis.Adapter.Product;
import com.example.minibis.Adapter.WishlistProductListRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CustomerWishlist extends AppCompatActivity {

    RecyclerView wishlistRecycler;
    TextView empty;
    FirebaseUser currentUser;
    FirebaseFirestore firestore;
    QuerySnapshot allProductsInCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_wishlist);

        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser==null){
            finish();
            return;
        }
        wishlistRecycler=(RecyclerView) findViewById(R.id.WishlistProductRecycler);
        empty=(TextView) findViewById(R.id.emptyProductListInWishlist);
        firestore=FirebaseFirestore.getInstance();

        ArrayList<Product> wishlistRecyclerArray=new ArrayList<>();
        firestore.collection("Users").document(currentUser.getUid()).collection("Wishlist").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    allProductsInCart=task.getResult();
                    for(QueryDocumentSnapshot doc:allProductsInCart){
                        wishlistRecyclerArray.add((Product) doc.toObject(Product.class));
                    }
                    wishlistRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    if(wishlistRecyclerArray.isEmpty()){
                        empty.setVisibility(View.VISIBLE);
                    }
                    else{
                        WishlistProductListRecyclerAdapter adapter=new WishlistProductListRecyclerAdapter(wishlistRecyclerArray,getApplicationContext());
                        wishlistRecycler.setAdapter(adapter);
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