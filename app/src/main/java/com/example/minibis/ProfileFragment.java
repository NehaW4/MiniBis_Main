package com.example.minibis;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
    LinearLayout editprofalay,cartlay,wishlitlay,orderlistlay;
    TextView editprofile,wishlist,ordelist,cart,faq,aboutus,contactus,termsandcond,headline;
    FirebaseFirestore firestore;
    Boolean isSeller;
    DocumentSnapshot currentUserDataDoc;
    ShapeableImageView profileIcon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        log_profile = (Button) view.findViewById(R.id.login_btn);
        editprofile=(TextView)view.findViewById(R.id.editprof);
        wishlist=(TextView)view.findViewById(R.id.wishlist);
        ordelist=(TextView)view.findViewById(R.id.orderlist);
        cart=(TextView)view.findViewById(R.id.cart);
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

        if(FirebaseAuth.getInstance().getCurrentUser()==null)
            {
                editprofalay.setVisibility(View.GONE);
                wishlitlay.setVisibility(View.GONE);
                orderlistlay.setVisibility(View.GONE);
                cartlay.setVisibility(View.GONE);
                ((Button) view.findViewById(R.id.logoutbtn)).setVisibility(View.GONE);
                headline.setText("Welcome to MiniBis");
            }
        else{
            if(currentUserDataDoc==null){
                retriveData();
            }
            else{
                setData();
            }
            log_profile.setVisibility(View.GONE);
        }

        log_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent10 = new Intent(view.getContext(),log_sign_options.class);
                startActivity(intent10);
                ((Activity)getActivity()).overridePendingTransition(0,0);

            }
        });
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent10 = new Intent(view.getContext(),EditProfilePage.class);
                startActivity(intent10);
                ((Activity)getActivity()).overridePendingTransition(0,0);

            }
        });
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent10 = new Intent(view.getContext(),EditProfilePage.class);
                startActivity(intent10);
                ((Activity)getActivity()).overridePendingTransition(0,0);

            }
        });

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent10 = new Intent(view.getContext(),contactus.class);
                startActivity(intent10);
                ((Activity)getActivity()).overridePendingTransition(0,0);
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent10 = new Intent(view.getContext(),contactus.class);
                startActivity(intent10);
                ((Activity)getActivity()).overridePendingTransition(0,0);

            }
        });
        termsandcond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent10 = new Intent(view.getContext(),contactus.class);
                startActivity(intent10);
                ((Activity)getActivity()).overridePendingTransition(0,0);

            }
        });
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent10 = new Intent(view.getContext(),contactus.class);
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
            }
            else{
                headline.setText("Welcome, "+currentUserDataDoc.getString("FirstName"));
            }
        }
        else{
            headline.setText("Welcome to MiniBis");
        }
    }
}
