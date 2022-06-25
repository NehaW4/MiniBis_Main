package com.example.minibis;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddProduct extends AppCompatActivity {

    private String selectedCategory;
    private Button addProductButton;
    private ImageView productImageView;
    private EditText productName,productPrice,productDesc;
    private String productImageString;
    private Date productCreationDate;
    private String SpinnerEntries[];
    private Spinner categorySpinner;
    private DocumentSnapshot currentUserData;

    private FirebaseUser currentUser;
    private FirebaseFirestore firestore;
    private static int CAMERA_PERMISSION_CODE=105;
    private static int IMAGE_CHOOSER_CODE=7777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        currentUser=FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser==null){
            finish();
            Toast.makeText(this, "Please Login as Seller In Order to Add Product", Toast.LENGTH_SHORT).show();
            return;
        }
        productImageView = (ImageView) findViewById(R.id.productImageInAddProduct);
        productName = (EditText) findViewById(R.id.productNameInAddProduct);
        productDesc= (EditText) findViewById(R.id.productDescInAddProduct);
        productPrice= (EditText) findViewById(R.id.productPriceInAddProduct);
        addProductButton= (Button) findViewById(R.id.addproductbtn);
        categorySpinner=(Spinner) findViewById(R.id.productTypeSpinner);

        SpinnerEntries= new String[]{"--- Select Product Category ---","Western Clothing","Ethnic Clothing","Accessories","Skin Care","Footwear","Home Decor"};
        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(this, R.layout.spinnerdialogitem,R.id.spinnerItemTextView,SpinnerEntries);
        categorySpinner.setAdapter(spinnerAdapter);

        categorySpinner.setDropDownVerticalOffset(5);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:
                        selectedCategory=null;
                        break;
                    case 1:
                        selectedCategory="westernClothing";
                        break;
                    case 2:
                        selectedCategory = "ethnicClothing";
                        break;
                    case 3:
                        selectedCategory = "accessories";
                        break;
                    case 4:
                        selectedCategory = "skinCare";
                        break;
                    case 5:
                        selectedCategory = "footwear";
                        break;
                    case 6:
                        selectedCategory = "homeDecor";
                        break;
                }
//                if(selectedCategory!=null)
//                    Toast.makeText(AddProduct.this, selectedCategory, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pname,pprice,pdesc;
                pname=productName.getText().toString().trim();
                pprice=productPrice.getText().toString().trim();
                pdesc=productDesc.getText().toString().trim();

                getAndSetCurrentUserData();
                if(productImageString == null){
                    Toast.makeText(AddProduct.this, "Error: Product Image is Required", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(pprice)){
                    Toast.makeText(AddProduct.this, "Error:Product price cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(pdesc)){
                    Toast.makeText(AddProduct.this, "Error: Product description cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(pname)){
                    Toast.makeText(AddProduct.this, "Error:Product name cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else if(selectedCategory==null){
                    Toast.makeText(AddProduct.this, "Error: Select Product Category", Toast.LENGTH_SHORT).show();
                }
                else if(currentUserData==null){
                    Toast.makeText(AddProduct.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
                else{
                    productCreationDate=new Date();
                    Map<String,Object> product = new HashMap<>();
                    product.put("ProductName",pname);
                    product.put("ProductPrice",pprice);
                    product.put("ProductDescription",pdesc);
                    product.put("ProductCategory",selectedCategory);
                    product.put("ProductImage",productImageString);
                    product.put("ProductSeller",currentUserData.getString("BrandName"));
                    product.put("ProductSellerLogo",currentUserData.getString("BrandLogo"));
                    product.put("ProductAddedDate",productCreationDate);
                    product.put("ProductSellCount",0);
                    product.put("ProductSellerUid",currentUser.getUid());

                    firestore.collection("Products").document().set(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AddProduct.this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                                productName.setText("");
                                productDesc.setText("");
                                productPrice.setText("");
                                productImageView.setImageDrawable(null);
                                productImageView.setBackground(getResources().getDrawable(R.drawable.layoutborderblack));
                                productImageString=null;
                                categorySpinner.setSelection(0);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddProduct.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        productImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,CAMERA_PERMISSION_CODE)){
                    getAndSetImageFromGallery();
                }
            }
        });

        firestore=FirebaseFirestore.getInstance();
        getAndSetCurrentUserData();
    }

    public boolean checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(getApplicationContext(), permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] { permission }, requestCode);
        }
        else {
            return true;
        }
        return false;
    }
    private void getAndSetImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        launchSomeActivity.launch(intent);
    }
    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult( new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();

                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap=null;
                        try {
                            selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                            selectedImageBitmap=ImageStringOperation.getCompressedBitmap(selectedImageBitmap,(800*600));
                            productImageView.setImageBitmap(selectedImageBitmap);
                            productImageView.setBackgroundColor(Color.WHITE);
                            productImageString=ImageStringOperation.getString(selectedImageBitmap);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Error:", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getAndSetImageFromGallery();
            } else {
                Toast.makeText(this, "Error: You need to grant storage permission to upload Image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getAndSetCurrentUserData(){
        if(currentUserData==null){
            firestore.collection("Users").document(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        currentUserData=task.getResult();
                        if(!currentUserData.exists()){
                            currentUserData=null;
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("Error","Cannot Fetch User Data",e);
                }
            });
        }
    }
}