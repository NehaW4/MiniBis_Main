package com.example.minibis;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class AddProduct extends Fragment {

    String selected_cate = "";
    Button imgbtn;
    private Button addbtn;
    private EditText pname,pdesc,pprice;
    private ImageView pimg;

    private RadioButton clothingobj,footwearobj,skincareobj,accessoryobj,homedecorobj,category;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("product");


    // One Preview Image
    ImageView imgview;
    int SELECT_PICTURE = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seller__add_product,container,false);

        imgbtn = view.findViewById(R.id.productimgbtn);
        imgview = view.findViewById(R.id.productimg);

        pname=view.findViewById(R.id.productname);
        pdesc=view.findViewById(R.id.productdesc);
        pprice=view.findViewById(R.id.productprice);
        addbtn=view.findViewById(R.id.addproductbtn);

        clothingobj=view.findViewById(R.id.clothing);
        footwearobj=view.findViewById(R.id.footwear);
        skincareobj=view.findViewById(R.id.skincare);
        accessoryobj=view.findViewById(R.id.accessories);
        homedecorobj=view.findViewById(R.id.homedecor);


        // handle the Choose Image button to trigger
        // the image chooser function
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(pname.getText().toString()) && !TextUtils.isEmpty(pdesc.getText().toString()) && !TextUtils.isEmpty(pprice.getText().toString())){

                    HashMap<String,Object> userMap = new HashMap<>();
                    userMap.put("product name",pname.getText().toString());
                    userMap.put("product description",pdesc.getText().toString());
                    userMap.put("product price",pprice.getText().toString());
                    if(clothingobj.isChecked()){
                        userMap.put("product category",clothingobj.getText().toString());
                        Toast.makeText(getContext(), "Selected"+clothingobj.getText(), Toast.LENGTH_SHORT).show();
                        selected_cate=clothingobj.getText().toString();
                    }
                    if(footwearobj.isChecked()){
                        userMap.put("product category",footwearobj.getText().toString());
                        Toast.makeText(getContext(), "Selected"+footwearobj.getText(), Toast.LENGTH_SHORT).show();
                        selected_cate=clothingobj.getText().toString();
                    }
                    if(skincareobj.isChecked()){
                        userMap.put("product category",skincareobj.getText().toString());
                        Toast.makeText(getContext(), "Selected"+skincareobj.getText(), Toast.LENGTH_SHORT).show();
                        selected_cate=clothingobj.getText().toString();
                    }
                    if(accessoryobj.isChecked()){
                        userMap.put("product category",accessoryobj.getText().toString());
                        Toast.makeText(getContext(), "Selected"+accessoryobj.getText(), Toast.LENGTH_SHORT).show();
                        selected_cate=clothingobj.getText().toString();
                    }
                    if(homedecorobj.isChecked()){
                        userMap.put("product category",homedecorobj.getText().toString());
                        Toast.makeText(getContext(), "Selected"+homedecorobj.getText(), Toast.LENGTH_SHORT).show();
                        selected_cate=clothingobj.getText().toString();
                    }

                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child("Product")
                            .child("01")
                            .setValue(userMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setCancelable(true);
                                    builder.setMessage("Added Successfully !!!");
                                    builder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            pname.setText("");
                                            pdesc.setText("");
                                            pprice.setText("");
                                            clothingobj.setChecked(false);
                                            footwearobj.setChecked(false);
                                            skincareobj.setChecked(false);
                                            accessoryobj.setChecked(false);
                                            homedecorobj.setChecked(false);

                                            dialog.cancel();
                                        }
                                    });
                                    builder.show();
                                }
                            })

                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(),"Something went wrong \n Try again Later.....",Toast.LENGTH_LONG);
                                }
                            });

                }
                else{
                    Toast.makeText(getContext(), "Fill all the Details", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }

    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    imgview.setImageURI(selectedImageUri);
                }
            }
        }
    }

}