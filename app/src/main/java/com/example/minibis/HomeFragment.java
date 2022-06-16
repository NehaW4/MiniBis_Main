package com.example.minibis;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.minibis.Adapter.HomepageProductListAdapter;
import com.example.minibis.Adapter.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.admin.v1.Index;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    Toolbar toolbar;
    ImageView slider;
    RecyclerView pproduct,pbrand,nproduct;
    Button ppButton,pbButton,npButton;
    FirebaseUser currentUser;
    FirebaseFirestore firestore;
    ArrayList<Product> pproductArray,nproductArray;

    public HomeFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        toolbar=(Toolbar) view.findViewById(R.id.maintoolbar);
        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        firestore=FirebaseFirestore.getInstance();
        pproduct=(RecyclerView) view.findViewById(R.id.popularProductRecycler);
        pbrand=(RecyclerView) view.findViewById(R.id.popularBrandRecycler);
        nproduct=(RecyclerView) view.findViewById(R.id.NewArrivalsRecycler);
        ppButton=(Button) view.findViewById(R.id.popularViewAllInHomepage);
        pbButton=(Button) view.findViewById(R.id.popularBrandViewAllInHomepage);
        npButton=(Button) view.findViewById(R.id.newArrivalsViewAllInHomepage);
        slider=(ImageView) view.findViewById(R.id.viewpager1);

        pproductArray=new ArrayList<>();
        nproductArray=new ArrayList<>();

        pproduct.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        nproduct.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));

        firestore.collection("Products").orderBy("ProductSellCount", Query.Direction.DESCENDING).limit(10).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot allDocs=task.getResult();
                    for(QueryDocumentSnapshot doc:allDocs){
                        Product p=doc.toObject(Product.class);
                        p.setProductId(doc.getId());
                        nproductArray.add(p);
                    }
                    HomepageProductListAdapter adapter=new HomepageProductListAdapter(pproductArray,getActivity().getApplicationContext());
                    pproduct.setAdapter(adapter);
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Product Fetch Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), "Product Fetch Failed", Toast.LENGTH_SHORT).show();
            }
        });

        firestore.collection("Products").orderBy("ProductAddedDate", Query.Direction.ASCENDING).limit(10).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    QuerySnapshot allDocs=task.getResult();
                    for(QueryDocumentSnapshot doc:allDocs){
                        Product pa=doc.toObject(Product.class);
                        pa.setProductId(doc.getId());
                        pproductArray.add(pa);
                    }
                    HomepageProductListAdapter adapter1=new HomepageProductListAdapter(nproductArray,getActivity().getApplicationContext());
                    nproduct.setAdapter(adapter1);
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Product Fetch Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), "Product Fetch Failed", Toast.LENGTH_SHORT).show();
            }
        });
        slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent okIntent=new Intent(view.getContext(),ProductListDisplay.class);
                okIntent.putExtra("category",3);
                startActivity(okIntent);
            }
        });
        ppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent okIntent=new Intent(view.getContext(),ProductListDisplay.class);
                okIntent.putExtra("category",8);
                startActivity(okIntent);
            }
        });
        npButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent okIntent=new Intent(view.getContext(),ProductListDisplay.class);
                okIntent.putExtra("category",9);
                startActivity(okIntent);
            }
        });

        return view;
    }
    @Override
    public void onCreate(@NonNull Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.homemenu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(currentUser==null) {
            currentUser = FirebaseAuth.getInstance().getCurrentUser();
        }
        if(firestore==null) {
            firestore = FirebaseFirestore.getInstance();
        }
        Log.i("INFO00000000","item:"+item.getItemId());
        switch (item.getItemId()) {
            case R.id.CartInHomepage:
                if(currentUser==null) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please Login First to proceed", Toast.LENGTH_SHORT).show();
                    Intent okIntent = new Intent(getActivity().getApplicationContext(), log_sign_options.class);
                    startActivity(okIntent);
                }
                else {
                    Intent okIntent = new Intent(getActivity().getApplicationContext(), CustomerCart.class);
                    startActivity(okIntent);
                }
                return true;
            case R.id.wishlistInHomepage:
                if(currentUser==null) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please Login First to proceed", Toast.LENGTH_SHORT).show();
                    Intent okIntent = new Intent(getActivity().getApplicationContext(), log_sign_options.class);
                    startActivity(okIntent);
                }
                else {
                    Intent okIntent = new Intent(getActivity().getApplicationContext(), CustomerWishlist.class);
                    startActivity(okIntent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}