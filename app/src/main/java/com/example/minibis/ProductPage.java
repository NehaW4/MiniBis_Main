package com.example.minibis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductPage extends AppCompatActivity {

    TextView pname,pcat,pdesc,pprice,pdate,sname;
    ImageView plogo,slogo;
    ConstraintLayout sellerContainer;
    Button cartButton,wishlistButton;
    Product product;
    FirebaseFirestore firestore;
    FirebaseUser currentUser;
    Boolean addedToWishlist,addedToCart;
    Button checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);
        pname=(TextView) findViewById(R.id.productNameInProductPage);
        pcat=(TextView) findViewById(R.id.productCategoryInProductPage);
        pdesc=(TextView) findViewById(R.id.productDescInProductPage);
        pprice=(TextView) findViewById(R.id.productPriceInProductPage);
        pdate=(TextView) findViewById(R.id.productDateInProductPage);
        sname=(TextView) findViewById(R.id.sellerNameInProductPage);
        sellerContainer=(ConstraintLayout) findViewById(R.id.brandInfoInProductPage);
        plogo=(ImageView) findViewById(R.id.productImageInProductPage);
        slogo=(ImageView) findViewById(R.id.sellerLogoInProductPage);
        cartButton=(Button) findViewById(R.id.addToCartButtonInProductPage);
        wishlistButton= (Button) findViewById(R.id.wishlistButtonInProductPage);
        checkout=(Button) findViewById(R.id.checkoutButtonInProductPage);

        Intent callingIntent=getIntent();
        product=(Product) callingIntent.getSerializableExtra("product");

        pname.setText(product.getProductName());
        pcat.setText(product.getProductCategory());
        pdesc.setText(product.getProductDescription());
        pprice.setText(product.getProductPrice()+" Rs");
        pdate.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(String.valueOf(product.getProductAddedDate()))));
        sname.setText(product.getProductSeller());
        plogo.setImageBitmap(ImageStringOperation.getImage(product.getProductImage()));
        slogo.setImageBitmap(ImageStringOperation.getImage(product.getProductSellerLogo()));

        sellerContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent okIntent=new Intent(ProductPage.this,ProductListDisplay.class);
                okIntent.putExtra("category",7);
                okIntent.putExtra("currentUserUid",product.getProductSellerUid());
                okIntent.putExtra("subHeading","All Products of "+product.getProductSeller());
                startActivity(okIntent);
            }
        });
        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        firestore=FirebaseFirestore.getInstance();
        if(currentUser!=null) {
            checkWishlist();
            checkCart();
        }
        else{
            addedToCart=false;
            addedToWishlist=false;
        }

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent okIntnet=new Intent(ProductPage.this,payment.class);
                okIntnet.putExtra("product",product);
                startActivity(okIntnet);
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!addedToCart) {
                    if (currentUser == null) {
                        Toast.makeText(ProductPage.this, "Please Login First to Proceed", Toast.LENGTH_SHORT).show();
                    } else {
                        firestore.collection("Users").document(currentUser.getUid()).collection("Cart").document(product.getProductId()).set(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ProductPage.this, "Product Added to Cart", Toast.LENGTH_SHORT).show();
                                    addedToCart=true;
                                    checkout.setVisibility(View.VISIBLE);
                                    cartButton.setText("Remove from Cart");
                                } else {
                                    Toast.makeText(ProductPage.this, "Failed to add to Cart", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProductPage.this, "Failed to add to Cart", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                else{
                    if (currentUser == null) {
                        Toast.makeText(ProductPage.this, "Please Login First to Proceed", Toast.LENGTH_SHORT).show();
                    } else {
                        firestore.collection("Users").document(currentUser.getUid()).collection("Cart").document(product.getProductId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ProductPage.this, "Product Removed from Cart", Toast.LENGTH_SHORT).show();
                                    addedToCart=false;
                                    cartButton.setText("Add to Cart");
                                    checkout.setVisibility(View.INVISIBLE);
                                } else {
                                    Toast.makeText(ProductPage.this, "Failed to Remove from Cart", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProductPage.this, "Failed to Remove from Cart", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });

        wishlistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!addedToWishlist) {
                    if (currentUser == null) {
                        Toast.makeText(ProductPage.this, "Please Login First to Proceed", Toast.LENGTH_SHORT).show();
                    } else {
                        firestore.collection("Users").document(currentUser.getUid()).collection("Wishlist").document(product.getProductId()).set(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ProductPage.this, "Product Added to Wishlist", Toast.LENGTH_SHORT).show();
                                    addedToWishlist=true;
                                    wishlistButton.setText("Remove from Wishlist");
                                } else {
                                    Toast.makeText(ProductPage.this, "Failed to add to Wishlist", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProductPage.this, "Failed to add to Wishlist", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                else{
                    if (currentUser == null) {
                        Toast.makeText(ProductPage.this, "Please Login First to Proceed", Toast.LENGTH_SHORT).show();
                    } else {
                        firestore.collection("Users").document(currentUser.getUid()).collection("Wishlist").document(product.getProductId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ProductPage.this, "Product Removed from Wishlist", Toast.LENGTH_SHORT).show();
                                    addedToWishlist=false;
                                    wishlistButton.setText("Add to wishlist");
                                } else {
                                    Toast.makeText(ProductPage.this, "Failed to Remove from Wishlist", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProductPage.this, "Failed to Remove from Wishlist", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });

        updateSellerInfo();
    }

    private void updateSellerInfo(){
        firestore.collection("Users").document(product.getProductSellerUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc=task.getResult();
                    if(doc.exists()){
                        product.setProductSellerLogo(doc.getString("BrandLogo"));
                        Bitmap b=ImageStringOperation.getImage(product.getProductSellerLogo());
                        if(b!=null)
                            slogo.setImageBitmap(b);
                        product.setProductSeller(doc.getString("BrandName"));
                    }
                }
            }
        });
    }
    void checkWishlist(){
        firestore.collection("Users").document(currentUser.getUid()).collection("Wishlist").document(product.getProductId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        addedToWishlist=true;
                        wishlistButton.setText("Remove from Wishlist");

                    }
                    else {
                        addedToWishlist = false;
                        wishlistButton.setText("Add to Wishlist");
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                addedToWishlist=false;
            }
        });
    }
    void checkCart(){
        firestore.collection("Users").document(currentUser.getUid()).collection("Cart").document(product.getProductId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){
                        addedToCart=true;
                        cartButton.setText("Remove from Cart");
                        checkout.setVisibility(View.VISIBLE);
                    }
                    else{
                        addedToCart=false;
                        cartButton.setText("Add to Cart");
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                addedToCart=false;
            }
        });
    }
}