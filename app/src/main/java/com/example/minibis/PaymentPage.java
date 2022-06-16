package com.example.minibis;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minibis.Adapter.Product;
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

public class PaymentPage extends AppCompatActivity {

    Product product;
    DocumentSnapshot sellerInfo;
    FirebaseUser currentUser;
    FirebaseFirestore firestore;
    ImageView productImage;
    TextView pname,pprice,sname,sbank,sifsc,supi;
    Button addSS,viewSS,placeOrder;
    EditText transaction,addressInput;
    Bitmap paymentSS;
    String transactionNo,address;
    private static int CAMERA_PERMISSION_CODE=105;
    private static int IMAGE_CHOOSER_CODE=7777;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);

        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser==null){
            finish();
            return;
        }

        product=(Product) getIntent().getSerializableExtra("product");
        productImage=(ImageView) findViewById(R.id.checkoutPageProductImage);
        pname=(TextView) findViewById(R.id.checkoutPageProductName);
        pprice=(TextView) findViewById(R.id.checkoutPageProductPrice);
        sname=(TextView) findViewById(R.id.checkoutPageSellerName);
        sbank=(TextView) findViewById(R.id.checkoutPageSellerBankAcc);
        sifsc=(TextView) findViewById(R.id.checkoutPageSellerBankIFSC);
        supi=(TextView) findViewById(R.id.checkoutPageSellerBankUpi);
        addSS=(Button) findViewById(R.id.checkoutPageUploadScreenshot);
        viewSS=(Button) findViewById(R.id.checkoutPageViewScreenshot);
        placeOrder=(Button) findViewById(R.id.checkoutPagePlaceOrderButton);
        addressInput=(EditText) findViewById(R.id.checkoutPageAddressInput);
        transaction=(EditText) findViewById(R.id.checkoutPageTransactionId);
        viewSS.setEnabled(false);
        firestore=FirebaseFirestore.getInstance();

        firestore.collection("Users").document(product.getProductSellerUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    sellerInfo=task.getResult();
                    if(sellerInfo.exists()){
                        sbank.setText(sellerInfo.getString("BrandBankAcc"));
                        sifsc.setText(sellerInfo.getString("BrandBankIFSC"));
                        supi.setText(sellerInfo.getString("BrandBankUPI"));
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PaymentPage.this, "Please try again.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        productImage.setImageBitmap(ImageStringOperation.getImage(product.getProductImage()));
        pname.setText(product.getProductName());
        pprice.setText("Total Price: "+product.getProductPrice()+" Rs");
        sname.setText(product.getProductSeller());

        addSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,CAMERA_PERMISSION_CODE)) {
                    getAndSetImageFromGallery();
                }
            }
        });
        viewSS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(paymentSS!=null){
                    View dialogView = LayoutInflater.from(PaymentPage.this).inflate(R.layout.paymentssdialog, null);
                    ImageView img=(ImageView) dialogView.findViewById(R.id.paymentSSDialogImage);
                    Button ok=(Button) dialogView.findViewById(R.id.paymentSSDialogButton);
                    AlertDialog.Builder builder = new AlertDialog.Builder(PaymentPage.this);
                    builder.setView(dialogView);
                    img.setImageBitmap(paymentSS);
                    dialog=builder.create();
                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    });
                    dialog.show();
                }
                else{
                    Toast.makeText(PaymentPage.this, "Add Payment Screenshot First", Toast.LENGTH_SHORT).show();
                }
            }
        });

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transactionNo=transaction.getText().toString().trim();
                address=addressInput.getText().toString().trim();
                if(TextUtils.isEmpty(transactionNo)){
                    Toast.makeText(PaymentPage.this, "Enter Transaction Number!", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(address)){
                    Toast.makeText(PaymentPage.this, "Enter the Address", Toast.LENGTH_SHORT).show();
                }
                else if(paymentSS==null){
                    Toast.makeText(PaymentPage.this, "Upload Payment Screenshot to proceed", Toast.LENGTH_SHORT).show();
                }
                else{
                    Map<String,Object> map=new HashMap<>();
                    map.put("ProductId",product.getProductId());
                    map.put("ProductName",product.getProductName());
                    map.put("ProductPrice",product.getProductPrice());
                    map.put("ProductImage",product.getProductImage());
                    map.put("SellerId",product.getProductSellerUid());
                    map.put("Status","Pending");
                    map.put("OrderDate",new Date());
                    map.put("TransactionNo",transactionNo);
                    map.put("TransactionScreenshot",ImageStringOperation.getString(paymentSS));
                    map.put("DeliveryAddress",address);
                    map.put("CustomerId",currentUser.getUid());
                    firestore.collection("Orders").document(transactionNo).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PaymentPage.this, "Order Placed Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                                firestore.collection("Users").document(currentUser.getUid()).collection("Cart").document(product.getProductId()).delete();
                            }
                            else{
                                Toast.makeText(PaymentPage.this, "Please try Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(PaymentPage.this, "Please try Again", Toast.LENGTH_SHORT).show();
                        }
                    });
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
                        try {
                            paymentSS = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                            viewSS.setEnabled(true);
                        } catch (IOException e) {
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