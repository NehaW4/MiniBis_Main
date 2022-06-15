package com.example.minibis.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minibis.ImageStringOperation;
import com.example.minibis.ProductListDisplay;
import com.example.minibis.ProductPage;
import com.example.minibis.R;

import java.util.ArrayList;
import java.util.Arrays;

public class ProductListRecyclerAdapter extends RecyclerView.Adapter<ProductListRecyclerAdapter.ProductListRecyclerViewHolder> {
    private ArrayList<Product> productList=new ArrayList<>();
    private Context mContext;
    private boolean isSeller;

    public ProductListRecyclerAdapter(ArrayList<Product> products, Context mContext,boolean isseller) {
        this.productList = products;
        this.mContext = mContext;
        this.isSeller=isseller;
    }

    public class ProductListRecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        TextView price;
        ConstraintLayout container;
        TextView sales,category;
        ProductListRecyclerViewHolder(View itemView){
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.productImageInProductList);
            name=(TextView) itemView.findViewById(R.id.productNameInProductList);
            price=(TextView) itemView.findViewById(R.id.productPriceInProductList);
            container=(ConstraintLayout) itemView.findViewById(R.id.containerInProductList);
            sales=(TextView) itemView.findViewById(R.id.totalSalesInProductList);
            category=(TextView) itemView.findViewById(R.id.productCategoryInProductList);
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
        public void setProductCategory(String Category){
            if(isSeller){
                category.setText("Category: "+Category);
            }
            else
                category.setVisibility(View.GONE);
        }
        public void setProductSales(String Sales){
            if(isSeller){
                sales.setText("Total sales: "+Sales);
            }
            else
                sales.setVisibility(View.GONE);
        }
        public void setOnClickListener(Product product){
            if(!isSeller) {
                container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent okIntent = new Intent(mContext, ProductPage.class);
                        okIntent.putExtra("product", product);
                        okIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(okIntent);
                    }
                });
            }
        }
    }

    @Override
    public ProductListRecyclerAdapter.ProductListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.productrecycleritem, parent, false);
        return new ProductListRecyclerViewHolder(view);
    }
    @Override
    public int getItemCount(){
        return productList==null?0:productList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListRecyclerViewHolder holder, int position) {
        final Product product= productList.get(position);
        holder.setProductImage(product.getProductImage());
        holder.setProductName(product.getProductName());
        holder.setProductPrice(product.getProductPrice());
        holder.setProductCategory(product.getProductCategory());
        holder.setProductSales(product.getProductSellCount()+"");
        holder.setOnClickListener(product);
    }
}
