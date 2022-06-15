package com.example.minibis.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minibis.ImageStringOperation;
import com.example.minibis.PaymentPage;
import com.example.minibis.ProductPage;
import com.example.minibis.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class WishlistProductListRecyclerAdapter extends RecyclerView.Adapter<WishlistProductListRecyclerAdapter.WishlistProductListRecyclerViewHolder> {
    private ArrayList<Product> productList=new ArrayList<>();
    private Context mContext;

    public WishlistProductListRecyclerAdapter(ArrayList<Product> products, Context mContext) {
        this.productList = products;
        this.mContext = mContext;
    }

    public class WishlistProductListRecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        TextView price;
        Button cart;
        TextView category;
        ImageView delete;
        boolean addedToCart;
        WishlistProductListRecyclerViewHolder(View itemView){
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.productImageInWishlist);
            name=(TextView) itemView.findViewById(R.id.productNameInWishlist);
            price=(TextView) itemView.findViewById(R.id.productPriceInWishlist);
            cart=(Button) itemView.findViewById(R.id.addToCartButtonInWishlist);
            category=(TextView) itemView.findViewById(R.id.productCategoryInWishlist);
            delete=(ImageView) itemView.findViewById(R.id.deleteFromWishlistButtonInWishlist);
        }
        public void setProductImage(String image) {
            Bitmap b= ImageStringOperation.getImage(image);
            if(b!=null){
                img.setImageBitmap(b);
            }
        }
        public void setProductName(String Name){
            name.setText(Name);
        }
        public void setProductPrice(String Price){
            price.setText("Price: "+Price+" Rs");
        }
        public void setProductCategory(String Category){category.setText("Category: "+Category);}
        public void setOnClickListener(Product product){
            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("Cart").document(product.getProductId()).set(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(mContext, "Product Added to Cart", Toast.LENGTH_SHORT).show();
                                cart.setEnabled(false);
                            } else {
                                Toast.makeText(mContext, "Failed to add to Cart", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mContext, "Failed to add to Cart", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent kIntent = new Intent(mContext, ProductPage.class);
                    kIntent.putExtra("product", product);
                    kIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(kIntent);
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("Wishlist").document(product.getProductId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(mContext, "Product Removed from Wishlist", Toast.LENGTH_SHORT).show();
                                productList.remove(getAdapterPosition());
                                notifyItemRemoved(getAdapterPosition());
                                notifyItemRangeChanged(getAdapterPosition(),productList.size());
//                                if(productList.size()==0){
//                                    ((TextView)itemView.findViewById(R.id.emptyProductListInWishlist)).setVisibility(View.VISIBLE);
//                                }
                            } else {
                                Toast.makeText(mContext, "Failed to Remove from Wishlist", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(mContext, "Failed to Remove from Wishlist", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    @Override
    public WishlistProductListRecyclerAdapter.WishlistProductListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.wishlistrecyclerproductitem, parent, false);
        return new WishlistProductListRecyclerViewHolder(view);
    }
    @Override
    public int getItemCount(){
        return productList==null?0:productList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistProductListRecyclerViewHolder holder, int position) {
        final Product product= productList.get(position);
        holder.setProductImage(product.getProductImage());
        holder.setProductName(product.getProductName());
        holder.setProductPrice(product.getProductPrice());
        holder.setProductCategory(product.getProductCategory());
        holder.setOnClickListener(product);
    }
}
