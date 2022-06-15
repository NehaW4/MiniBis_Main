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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minibis.ImageStringOperation;
import com.example.minibis.PaymentPage;
import com.example.minibis.ProductPage;
import com.example.minibis.R;

import java.util.ArrayList;

public class CartProductListRecyclerAdapter extends RecyclerView.Adapter<CartProductListRecyclerAdapter.CartProductListRecyclerViewHolder> {
    private ArrayList<Product> productList=new ArrayList<>();
    private Context mContext;

    public CartProductListRecyclerAdapter(ArrayList<Product> products, Context mContext) {
        this.productList = products;
        this.mContext = mContext;
    }

    public class CartProductListRecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        TextView price;
        Button checkout;
        TextView category;
        CartProductListRecyclerViewHolder(View itemView){
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.productImageInCart);
            name=(TextView) itemView.findViewById(R.id.productNameInCart);
            price=(TextView) itemView.findViewById(R.id.productPriceInCart);
            checkout=(Button) itemView.findViewById(R.id.checkoutButtonInCart);
            category=(TextView) itemView.findViewById(R.id.productCategoryInCart);
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
            checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent okIntent = new Intent(mContext, PaymentPage.class);
                    okIntent.putExtra("product", product);
                    okIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(okIntent);
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
        }
    }

    @Override
    public CartProductListRecyclerAdapter.CartProductListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cartrecyclerproductitem, parent, false);
        return new CartProductListRecyclerViewHolder(view);
    }
    @Override
    public int getItemCount(){
        return productList==null?0:productList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull CartProductListRecyclerViewHolder holder, int position) {
        final Product product= productList.get(position);
        holder.setProductImage(product.getProductImage());
        holder.setProductName(product.getProductName());
        holder.setProductPrice(product.getProductPrice());
        holder.setProductCategory(product.getProductCategory());
        holder.setOnClickListener(product);
    }
}
