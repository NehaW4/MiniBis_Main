package com.example.minibis;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CategoryFragment extends Fragment {


    ConstraintLayout expand1,expand2,expand3,expand4,expand5;
    ImageView search;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category,container,false);

        expand1=(ConstraintLayout) view.findViewById(R.id.clothingCategory);
        expand2=(ConstraintLayout) view.findViewById(R.id.accessoriesCategory);
        expand3=(ConstraintLayout) view.findViewById(R.id.skincareCategory);
        expand4=(ConstraintLayout) view.findViewById(R.id.footwearCatagory);
        expand5=(ConstraintLayout) view.findViewById(R.id.homedecorCategory);
        search=(ImageView) view.findViewById(R.id.searchIconInCategories);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.searchdialog, null);
                Button ok=(Button) dialogView.findViewById(R.id.searchProductButton);
                Button exit=(Button) dialogView.findViewById(R.id.searchProductCancel);
                EditText textQuery=(EditText) dialogView.findViewById(R.id.searchProductInput);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setView(dialogView);
                AlertDialog dialog=builder.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        exit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String text=textQuery.getText().toString().trim();
                                if(TextUtils.isEmpty(text)){
                                    Toast.makeText(view.getContext(), "Search Query cannot be Empty", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Intent okIntent=new Intent(view.getContext(),ProductListDisplay.class);
                                    okIntent.putExtra("category",6);
                                    okIntent.putExtra("searchQuery",text);
                                    dialog.dismiss();
                                    startActivity(okIntent);
                                }
                            }
                        });
                    }
                });
                dialog.show();
            }
        });

        expand1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent okIntent=new Intent(view.getContext(),cloth_subcateg.class);
                startActivity(okIntent);
            }
        });
        expand2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent okIntent=new Intent(view.getContext(),ProductListDisplay.class);
                okIntent.putExtra("category",2);
                startActivity(okIntent);
            }
        });
        expand3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent okIntent=new Intent(view.getContext(),ProductListDisplay.class);
                okIntent.putExtra("category",3);
                startActivity(okIntent);
            }
        });
        expand4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent okIntent=new Intent(view.getContext(),ProductListDisplay.class);
                okIntent.putExtra("category",4);
                startActivity(okIntent);
            }
        });
        expand5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent okIntent=new Intent(view.getContext(),ProductListDisplay.class);
                okIntent.putExtra("category",5);
                startActivity(okIntent);
            }
        });
        return view;
    }
}