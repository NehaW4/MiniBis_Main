package com.example.minibis.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minibis.ImageStringOperation;
import com.example.minibis.ProductPage;
import com.example.minibis.R;

import java.util.ArrayList;

public class HomepageProductListAdapter extends RecyclerView.Adapter<HomepageProductListAdapter.HomepageProductListViewHolder> {
    private ArrayList<Product> productList=new ArrayList<>();
    private final Context mContext;

    public HomepageProductListAdapter(ArrayList<Product> products, Context mContext) {
        this.productList = products;
        this.mContext = mContext;
    }

    public class HomepageProductListViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        TextView price;
        CardView container;
        HomepageProductListViewHolder(View itemView){
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.homepageRecyclerItemImage);
            name=(TextView) itemView.findViewById(R.id.homepageRecyclerItemName);
            price=(TextView) itemView.findViewById(R.id.homepageRecyclerItemPrice);
            container=(CardView) itemView.findViewById(R.id.productRecyclerItemContainerHomepage);
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
        public void setOnClickListener(Product product){
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

    @Override
    public HomepageProductListAdapter.HomepageProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.homepageproductrecycleritem, parent, false);
        return new HomepageProductListViewHolder(view);
    }
    @Override
    public int getItemCount(){
        return productList==null?0:productList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull HomepageProductListViewHolder holder, int position) {
        final Product product= productList.get(position);
        Log.i("AAAAAAA","Count: "+position);
        holder.setProductImage(product.getProductImage());
        holder.setProductName(product.getProductName());
        holder.setProductPrice(product.getProductPrice());
        holder.setOnClickListener(product);
    }
}
