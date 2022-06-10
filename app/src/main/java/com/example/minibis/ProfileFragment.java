package com.example.minibis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class ProfileFragment extends Fragment {

    Button log_profile;
    LinearLayout editprofalay,cartlay,wishlitlay,orderlistlay;
TextView editprofile,wishlist,ordelist,cart,faq,aboutus,contactus,termsandcond;
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
        editprofalay=(LinearLayout) view.findViewById(R.id.edditnamelay);
        cartlay=(LinearLayout) view.findViewById(R.id.cartlay);
        wishlitlay=(LinearLayout) view.findViewById(R.id.wishlistlay);
        orderlistlay=(LinearLayout) view.findViewById(R.id.orderlistlay);

        if(FirebaseAuth.getInstance()==null)
            {
                editprofalay.setVisibility(View.VISIBLE);
                wishlitlay.setVisibility(View.VISIBLE);
                orderlistlay.setVisibility(View.VISIBLE);
                cartlay.setVisibility(View.VISIBLE);

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
        return view;
    }
}