package com.example.minibis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minibis.Adapter.HomepageProductListAdapter;
import com.example.minibis.Adapter.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    Toolbar toolbar;
    ImageView slider;
    RecyclerView pproduct,nproduct;
    Button ppButton,pbButton,npButton;
    FirebaseUser currentUser;
    FirebaseFirestore firestore;
    ArrayList<Product> pproductArray,nproductArray;
    Context mContext;

    public HomeFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        toolbar= view.findViewById(R.id.maintoolbar);
        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        pproduct= view.findViewById(R.id.popularProductRecycler);
//        pbrand=(RecyclerView) view.findViewById(R.id.popularBrandRecycler);
        nproduct= view.findViewById(R.id.NewArrivalsRecycler);
        ppButton= view.findViewById(R.id.popularViewAllInHomepage);
        pbButton= view.findViewById(R.id.popularBrandViewAllInHomepage);
        npButton= view.findViewById(R.id.newArrivalsViewAllInHomepage);
        slider= view.findViewById(R.id.viewpager1);
        try {
            mContext = requireActivity().getApplicationContext();
        }catch(NullPointerException e){
            mContext=null;
        }
        if(mContext==null){
            return view;
        }

        firestore=FirebaseFirestore.getInstance();
//        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                .setPersistenceEnabled(false)
//                .build();
//        firestore.setFirestoreSettings(settings);

        pproductArray=new ArrayList<>();
        nproductArray=new ArrayList<>();


        nproduct.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));

        firestore.collection("Products").orderBy("ProductSellCount", Query.Direction.DESCENDING).limit(10).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                QuerySnapshot allDocs=task.getResult();
                for(QueryDocumentSnapshot doc:allDocs){
                    Product p=doc.toObject(Product.class);
                    p.setProductId(doc.getId());
                    pproductArray.add(p);
                }
                try{
                    HomepageProductListAdapter adapter=new HomepageProductListAdapter(pproductArray,mContext);
                    pproduct.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
                    pproduct.setAdapter(adapter);
                }catch(Exception e){
                    Log.w("FE","Data Fetch Error: Popular Products",e);
                }
            }
            else{
                Toast.makeText(mContext, "Product Fetch Failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(mContext, "Product Fetch Failed", Toast.LENGTH_SHORT).show());

        firestore.collection("Products").orderBy("ProductAddedDate", Query.Direction.ASCENDING).limit(10).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                QuerySnapshot allDocs=task.getResult();
                for(QueryDocumentSnapshot doc:allDocs){
                    Product pa=doc.toObject(Product.class);
                    pa.setProductId(doc.getId());
                    nproductArray.add(pa);
                }

                HomepageProductListAdapter adapter1=new HomepageProductListAdapter(nproductArray,mContext);
                nproduct.setAdapter(adapter1);
                Log.i("AAAAAAA","AdapterAttached with size: "+nproductArray.size());
            }
            else{
                Toast.makeText(mContext, "Product Fetch Failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(mContext, "Product Fetch Failed", Toast.LENGTH_SHORT).show());


        slider.setOnClickListener(view1 -> {
            Intent okIntent=new Intent(view1.getContext(),ProductListDisplay.class);
            okIntent.putExtra("category",3);
            startActivity(okIntent);
        });
        ppButton.setOnClickListener(view12 -> {
            Intent okIntent=new Intent(view12.getContext(),ProductListDisplay.class);
            okIntent.putExtra("category",8);
            startActivity(okIntent);
        });
        npButton.setOnClickListener(view13 -> {
            Intent okIntent=new Intent(view13.getContext(),ProductListDisplay.class);
            okIntent.putExtra("category",9);
            startActivity(okIntent);
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
                    Toast.makeText(mContext, "Please Login First to proceed", Toast.LENGTH_SHORT).show();
                    Intent okIntent = new Intent(mContext, log_sign_options.class);
                    startActivity(okIntent);
                }
                else {
                    Intent okIntent = new Intent(mContext, CustomerCart.class);
                    startActivity(okIntent);
                }
                return true;
            case R.id.wishlistInHomepage:
                if(currentUser==null) {
                    Toast.makeText(mContext, "Please Login First to proceed", Toast.LENGTH_SHORT).show();
                    Intent okIntent = new Intent(mContext, log_sign_options.class);
                    startActivity(okIntent);
                }
                else {
                    Intent okIntent = new Intent(mContext, CustomerWishlist.class);
                    startActivity(okIntent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}