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
import android.widget.Toast;

import com.example.minibis.Adapter.HomepageProductListAdapter;
import com.example.minibis.Adapter.Product;
import com.example.minibis.Adapter.ProductListRecyclerAdapter;
import com.google.android.gms.common.internal.service.Common;
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

public class ProductListDisplay extends AppCompatActivity {

    TextView headline,subHeadline;
    RecyclerView recyclerView;
    Intent callingIntent;
    String query;
    FirebaseFirestore firestore;
    FirebaseUser currentUser;
    QuerySnapshot allDocuments;
    ArrayList<Product> productListArray;
    ProductListRecyclerAdapter productListAdapter;
    TextView noProductFound;
    Boolean isSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_display);

        callingIntent=getIntent();
        headline=(TextView) findViewById(R.id.productListHeadline);
        subHeadline=(TextView) findViewById(R.id.productListSubHeadline);
        noProductFound=(TextView) findViewById(R.id.EmptyProductListInProductList);

        recyclerView=(RecyclerView) findViewById(R.id.productListRecyclerView);

        currentUser=FirebaseAuth.getInstance().getCurrentUser();
        firestore=FirebaseFirestore.getInstance();

        if(!CommonUtility.isInternetAvailable()){Toast.makeText(this,"Turn on Intenet Connection",Toast.LENGTH_SHORT).show();}

        int num=callingIntent.getIntExtra("category",-1);
        switch(num) {
            case 0:
                headline.setText("Western Clothing");
                query = "westernClothing";
                break;
            case 1:
                headline.setText("Ethnic Clothing");
                query = "ethnicClothing";
                break;
            case 2:
                headline.setText("Accessories");
                query = "accessories";
                break;
            case 3:
                headline.setText("SkinCare");
                query = "skinCare";
                break;
            case 4:
                headline.setText("Footwear");
                query = "footwear";
                break;
            case 5:
                headline.setText("Home-Decor");
                query = "homeDecor";
                break;
            case 6:
                headline.setText("Search Result");
                query = "search";
                break;
            case 7:
                headline.setText("Product List");
                String uid=callingIntent.getStringExtra("currentUserUid");
                isSeller=callingIntent.getBooleanExtra("isSeller",false);
                if(uid!=null){
                    query=uid;
                }
                else
                    query= currentUser.getUid();
                break;
            case 8:
                headline.setText("Popular Products");
                break;
            case 9:
                headline.setText("New Arrivals");
                break;
            default:
                finish();
                return;
        }
        subHeadline.setVisibility(View.VISIBLE);
//        subHeadline.setText(query);
        if(callingIntent.getStringExtra("subHeading")!=null){
            subHeadline.setText(callingIntent.getStringExtra("subHeading"));
            subHeadline.setVisibility(View.VISIBLE);
        }

        productListArray=new ArrayList<Product>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        if(num==6){
            //search Query
        }
        else if(num==7){
            firestore.collection("Products").whereEqualTo("ProductSellerUid",query).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        allDocuments=task.getResult();
                        for(QueryDocumentSnapshot doc:allDocuments){
//                            Log.d("Data:",doc.getData()+"");
                            Product p=doc.toObject(Product.class);
                            p.setProductId(doc.getId());
                            productListArray.add(p);
                        }
                        if(productListArray.isEmpty()){
                            noProductFound.setVisibility(View.VISIBLE);
                        }
                        productListAdapter=new ProductListRecyclerAdapter(productListArray,getApplicationContext(),isSeller);
                        recyclerView.setAdapter(productListAdapter);
                    }
                    else{
                        Toast.makeText(ProductListDisplay.this, "Data Error", Toast.LENGTH_SHORT).show();
                        noProductFound.setVisibility(View.VISIBLE);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProductListDisplay.this, "Data Fetch Error", Toast.LENGTH_SHORT).show();
                    noProductFound.setVisibility(View.VISIBLE);
                }
            });
        }
        else if(num==8){
            firestore.collection("Products").orderBy("ProductSellCount", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        allDocuments=task.getResult();
                        for(QueryDocumentSnapshot doc:allDocuments){
                            Product p=doc.toObject(Product.class);
                            p.setProductId(doc.getId());
                            productListArray.add(p);
                        }
                        if(productListArray.isEmpty()){
                            noProductFound.setVisibility(View.VISIBLE);
                        }
                        productListAdapter=new ProductListRecyclerAdapter(productListArray,getApplicationContext(),false);
                        recyclerView.setAdapter(productListAdapter);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Data Error", Toast.LENGTH_SHORT).show();
                        noProductFound.setVisibility(View.VISIBLE);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Product Fetch Failed", Toast.LENGTH_SHORT).show();
                    noProductFound.setVisibility(View.VISIBLE);
                }
            });
        }
        else if(num==9){
            firestore.collection("Products").orderBy("ProductAddedDate", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        allDocuments=task.getResult();
                        for(QueryDocumentSnapshot doc:allDocuments){
                            Product p=doc.toObject(Product.class);
                            p.setProductId(doc.getId());
                            productListArray.add(p);
                        }
                        if(productListArray.isEmpty()){
                            noProductFound.setVisibility(View.VISIBLE);
                        }
                        productListAdapter=new ProductListRecyclerAdapter(productListArray,getApplicationContext(),false);
                        recyclerView.setAdapter(productListAdapter);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Data Error", Toast.LENGTH_SHORT).show();
                        noProductFound.setVisibility(View.VISIBLE);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Product Fetch Failed", Toast.LENGTH_SHORT).show();
                    noProductFound.setVisibility(View.VISIBLE);
                }
            });
        }
        else{
            firestore.collection("Products").whereEqualTo("ProductCategory",query).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        allDocuments=task.getResult();
                        for(QueryDocumentSnapshot doc:allDocuments){
//                            Log.d("Data:",doc.getData()+"");
                            Product p=doc.toObject(Product.class);
                            p.setProductId(doc.getId());
                            productListArray.add(p);
                        }
                        if(productListArray.isEmpty()){
                            noProductFound.setVisibility(View.VISIBLE);
                        }
                        productListAdapter=new ProductListRecyclerAdapter(productListArray,getApplicationContext(),false);
                        recyclerView.setAdapter(productListAdapter);
                    }
                    else{
                        Toast.makeText(ProductListDisplay.this, "Data Error", Toast.LENGTH_SHORT).show();
                        noProductFound.setVisibility(View.VISIBLE);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProductListDisplay.this, "Data Fetch Error", Toast.LENGTH_SHORT).show();
                    noProductFound.setVisibility(View.VISIBLE);
                }
            });
        }


    }
}
