package com.example.minibis;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CategoryFragment extends Fragment {


    ConstraintLayout expand1,expand2,expand3,expand4,expand5;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category,container,false);

        expand1=(ConstraintLayout) view.findViewById(R.id.clothingCategory);
        expand2=(ConstraintLayout) view.findViewById(R.id.accessoriesCategory);
        expand3=(ConstraintLayout) view.findViewById(R.id.skincareCategory);
        expand4=(ConstraintLayout) view.findViewById(R.id.footwearCatagory);
        expand5=(ConstraintLayout) view.findViewById(R.id.homedecorCategory);

        expand1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), cloth_subcateg.class);
                startActivity(intent);

            }
        });
        expand2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(view.getContext(),access_sub.class);
                startActivity(intent1);
            }
        });
        expand3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2= new Intent(view.getContext(),footwear_sub.class);
                startActivity(intent2);
            }
        });
        expand4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3= new Intent(view.getContext(),skincare_sub.class);
                startActivity(intent3);
            }
        });
        expand5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4= new Intent(view.getContext(),homedecor_sub.class);
                startActivity(intent4);
            }
        });
        return view;
    }
}