package com.example.minibis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileFragment extends Fragment {

    Button log_profile,logout;
    LinearLayout editprofalay,cartlay,wishlitlay,orderlistlay,addproductlay,orderrequestlay,allproductlay;
    TextView faq,aboutus,contactus,termsandcond,headline;
    FirebaseFirestore firestore;
    Boolean isSeller;
    DocumentSnapshot currentUserDataDoc;
    ShapeableImageView profileIcon;
    Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        log_profile = (Button) view.findViewById(R.id.login_btn);
        faq =(TextView)view.findViewById(R.id.faqs);
        aboutus =(TextView)view.findViewById(R.id.aboutus);
        contactus=(TextView)view.findViewById(R.id.contactus);
        termsandcond =(TextView)view.findViewById(R.id.tremanddpol);
        editprofalay=(LinearLayout) view.findViewById(R.id.editnamelay);
        cartlay=(LinearLayout) view.findViewById(R.id.cartlay);
        wishlitlay=(LinearLayout) view.findViewById(R.id.wishlistlay);
        orderlistlay=(LinearLayout) view.findViewById(R.id.orderlistlay);
        logout=(Button) view.findViewById(R.id.logoutbtn);
        headline=(TextView) view.findViewById(R.id.headlineOfProfileFragment);
        profileIcon=(ShapeableImageView) view.findViewById(R.id.ProfileFragmentProfileIcon);
        addproductlay=(LinearLayout) view.findViewById(R.id.addProductLay);
        orderrequestlay=(LinearLayout) view.findViewById(R.id.viewOrderRequestLay);
        allproductlay=(LinearLayout) view.findViewById(R.id.allProductsLay);
        mContext=view.getContext();

        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            if(currentUserDataDoc==null){
                retriveData();
            }
            else{
                setData();
            }
            log_profile.setVisibility(View.GONE);
            editprofalay.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);
        }
        else{
            headline.setText("Welcome to Minibis");
        }

        log_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent10 = new Intent(mContext,log_sign_options.class);
                startActivity(intent10);
                ((Activity)getActivity()).overridePendingTransition(0,0);

            }
        });
        editprofalay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent10 = new Intent(mContext,EditProfilePage.class);
                intent10.putExtra("isSeller",isSeller);
                if(isSeller)
                    intent10.putExtra("Picture",currentUserDataDoc.getString("BrandLogo"));
                else
                    intent10.putExtra("Picture",currentUserDataDoc.getString("ProfilePic"));
                startActivity(intent10);
                ((Activity)getActivity()).overridePendingTransition(0,0);
            }
        });
        wishlitlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent10 = new Intent(mContext,CustomerWishlist.class);
                startActivity(intent10);
                ((Activity)getActivity()).overridePendingTransition(0,0);

            }
        });
        cartlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent okIntent=new Intent(mContext,CustomerCart.class);
                startActivity(okIntent);
                ((Activity)getActivity()).overridePendingTransition(0,0);

            }
        });

        orderlistlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent okIntent=new Intent(mContext,OrderList.class);
                startActivity(okIntent);
                ((Activity)getActivity()).overridePendingTransition(0,0);
            }
        });

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent10 = new Intent(mContext,contactus.class);
                startActivity(intent10);
                ((Activity)getActivity()).overridePendingTransition(0,0);
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent10 = new Intent(mContext,aboutus.class);
                startActivity(intent10);
                ((Activity)getActivity()).overridePendingTransition(0,0);

            }
        });
        termsandcond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent10 = new Intent(mContext,termandpolicy.class);
                startActivity(intent10);
                ((Activity)getActivity()).overridePendingTransition(0,0);

            }
        });
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent10 = new Intent(mContext,FAQ.class);
                startActivity(intent10);
                ((Activity)getActivity()).overridePendingTransition(0,0);

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(), "Logout Success", Toast.LENGTH_SHORT).show();
                new Handler().post(new Runnable() {

                    @Override
                    public void run()
                    {
                        Intent intent = getActivity().getIntent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_NO_ANIMATION);

                        getActivity().overridePendingTransition(0, 0);
                        getActivity().finish();
                        getActivity().overridePendingTransition(0, 0);
                        startActivity(intent);
                    }
                });
            }
        });
        orderrequestlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent okIntent = new Intent(mContext,OrderRequests.class);
                okIntent.putExtra("isSeller",isSeller);
                startActivity(okIntent);
                ((Activity)getActivity()).overridePendingTransition(0,0);
            }
        });
        return view;
    }
    public void retriveData(){
        FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
        firestore=FirebaseFirestore.getInstance();
        firestore.collection("Users").document(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    currentUserDataDoc=task.getResult();
                    setData();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                headline.setText("Welcome to MiniBis");
                Log.w("N/W","Data Fetch Failed",e);
            }
        });
    }
    void setData(){
        if(currentUserDataDoc.exists()){
            isSeller = currentUserDataDoc.getBoolean("isSeller");
            if(isSeller){
                headline.setText("Welcome, "+currentUserDataDoc.getString("BrandName"));
                Bitmap logo=ImageStringOperation.getImage(currentUserDataDoc.getString("BrandLogo"));
                if(logo!=null){
                    profileIcon.setImageBitmap(logo);
                }
                addproductlay.setVisibility(View.VISIBLE);
                orderrequestlay.setVisibility(View.VISIBLE);
                allproductlay.setVisibility(View.VISIBLE);
            }
            else{
                headline.setText("Welcome, "+currentUserDataDoc.getString("FirstName"));
                Bitmap Logo=ImageStringOperation.getImage(currentUserDataDoc.getString("ProfilePic"));
                if(Logo!=null)
                    profileIcon.setImageBitmap(Logo);
                cartlay.setVisibility(View.VISIBLE);
                wishlitlay.setVisibility(View.VISIBLE);
                orderlistlay.setVisibility(View.VISIBLE);
            }
        }
        else{
            headline.setText("Welcome to MiniBis");
        }
        addproductlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent okIntent=new Intent(mContext,AddProduct.class);
                startActivity(okIntent);
                ((Activity)getActivity()).overridePendingTransition(0,0);
            }
        });
        allproductlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent okIntent=new Intent(mContext,ProductListDisplay.class);
                okIntent.putExtra("category",7);
                okIntent.putExtra("isSeller",true);
                startActivity(okIntent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
            retriveData();
    }
}
