package com.example.minibis;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class signupseller1 extends AppCompatActivity {

    private Button btnnext,uploadImage;
    private ImageView uploadedImage;
    private EditText brandname, brandmobile, brandemail, brandpassword, brandConfirmPassword;
    private static int CAMERA_PERMISSION_CODE=105;
    private static int IMAGE_CHOOSER_CODE=7777;
    private String brandLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupseller1);

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            Toast.makeText(this, "Already Logged In", Toast.LENGTH_SHORT).show();
            finish();
        }

        brandname =(EditText) findViewById(R.id.brandName);
        brandmobile = (EditText) findViewById(R.id.brandMobile);
        brandemail = (EditText) findViewById(R.id.brandEmailId);
        brandpassword = (EditText) findViewById(R.id.brandCreatePassword);
        brandConfirmPassword = (EditText) findViewById(R.id.brandConfirmPassword);
        btnnext =(Button) findViewById(R.id.createSellerAccountFinalButton);
        uploadImage=(Button) findViewById(R.id.selectimagebutton);
        uploadedImage=(ImageView) findViewById(R.id.selectimageview);

        Intent okIntent = new Intent(signupseller1.this, signupseller2.class);

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,CAMERA_PERMISSION_CODE)){
                    getAndSetImageFromGallery();
                }
            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = brandname.getText().toString().trim();
                String mobile = brandmobile.getText().toString().trim();
                String email = brandemail.getText().toString().trim();
                String cpass = brandpassword.getText().toString().trim();
                String cfpass = brandConfirmPassword.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(signupseller1.this,"Error: BrandName cannot be Empty",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(mobile)) {
                    Toast.makeText(signupseller1.this, "Error: Mobile Number cannot be Empty", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(email)){
                    Toast.makeText(signupseller1.this,"Error: Email cannot be Empty",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(cpass)){
                    Toast.makeText(signupseller1.this,"Error: Password cannot be Empty",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(cfpass)){
                    Toast.makeText(signupseller1.this,"Error: Confirm Password cannot be Empty",Toast.LENGTH_SHORT).show();
                }
                else if(!cpass.equals(cfpass)){
                    Toast.makeText(signupseller1.this,"Error: Password and Confirm Password Does not match",Toast.LENGTH_SHORT).show();
                }
                else{
                    okIntent.putExtra("name",name);
                    okIntent.putExtra("mobile",mobile);
                    okIntent.putExtra("email",email);
                    okIntent.putExtra("pass",cpass);
                    okIntent.putExtra("logo",brandLogo);
                    startActivity(okIntent);
                }
            }
        });
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
                            selectedImageBitmap=ImageStringOperation.getCompressedBitmap(selectedImageBitmap,800*600);
                            uploadedImage.setImageBitmap(selectedImageBitmap);
                            brandLogo=ImageStringOperation.getString(selectedImageBitmap);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
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
}