package com.example.minibis.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minibis.ImageStringOperation;
import com.example.minibis.ProductPage;
import com.example.minibis.R;
import com.example.minibis.Shipping;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderListRecyclerAdapter extends RecyclerView.Adapter<OrderListRecyclerAdapter.OrderListRecyclerViewHolder> {
    private ArrayList<Order> orderList=new ArrayList<>();
    private Context mContext;

    public OrderListRecyclerAdapter(ArrayList<Order> orders, Context mContext) {
        this.orderList = orders;
        this.mContext = mContext;
    }

    public class OrderListRecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        TextView price;
        Button screenshot,approve,reject;
        TextView address,status,transaction;
        String screenshotImage;
        ConstraintLayout container;

        OrderListRecyclerViewHolder(View itemView){
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.orderListImage);
            name=(TextView) itemView.findViewById(R.id.orderListName);
            price=(TextView) itemView.findViewById(R.id.orderListPrice);
            screenshot=(Button) itemView.findViewById(R.id.orderListSSButton);
            address=(TextView) itemView.findViewById(R.id.orderListAddress);
            status=(TextView) itemView.findViewById(R.id.orderListStatus);
            transaction=(TextView) itemView.findViewById(R.id.orderListTransaction);
            container=(ConstraintLayout) itemView.findViewById(R.id.orderListItemContainer);
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
        public void setProductAddress(String Address){address.setText("Address: "+Address);}
        public void setProductStatus(String Status){status.setText("Status:"+Status);}
        public void setTransaction(String Transaction){transaction.setText("Transaction No: "+Transaction);}
        public void setTransactionImage(String image){screenshotImage=image;}
        public void setOnClickListener(Order order){
            screenshot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View dialogView = LayoutInflater.from(mContext).inflate(R.layout.paymentssdialog, null);
                    ImageView img=(ImageView) dialogView.findViewById(R.id.paymentSSDialogImage);
                    Button ok=(Button) dialogView.findViewById(R.id.paymentSSDialogButton);
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setView(dialogView);
                    img.setImageBitmap(ImageStringOperation.getImage(screenshotImage));
                    AlertDialog dialog=builder.create();
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
            });
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent okIntent = new Intent(mContext, Shipping.class);
                    okIntent.putExtra("order", order);
                    okIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(okIntent);
                }
            });
        }
    }

    @Override
    public OrderListRecyclerAdapter.OrderListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.orderlistrecycleritem, parent, false);
        return new OrderListRecyclerViewHolder(view);
    }
    @Override
    public int getItemCount(){
        return orderList==null?0:orderList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListRecyclerViewHolder holder, int position) {
        final Order order= orderList.get(position);
        holder.setProductImage(order.getProductImage());
        holder.setProductName(order.getProductName());
        holder.setProductPrice(order.getProductPrice());
        holder.setProductAddress(order.getDeliveryAddress());
        holder.setProductStatus(order.getStatus());
        holder.setTransaction(order.getTransactionNo());
        holder.setTransactionImage(order.getTransactionScreenshot());
        holder.setOnClickListener(order);
    }
}
