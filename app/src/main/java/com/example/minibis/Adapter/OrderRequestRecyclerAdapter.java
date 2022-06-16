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
import androidx.recyclerview.widget.RecyclerView;

import com.example.minibis.ImageStringOperation;
import com.example.minibis.ProductPage;
import com.example.minibis.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderRequestRecyclerAdapter extends RecyclerView.Adapter<OrderRequestRecyclerAdapter.OrderRequestRecyclerViewHolder> {
    private ArrayList<Order> orderList=new ArrayList<>();
    private Context mContext;

    public OrderRequestRecyclerAdapter(ArrayList<Order> orders, Context mContext) {
        this.orderList = orders;
        this.mContext = mContext;
    }

    public class OrderRequestRecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        TextView price;
        Button screenshot,approve,reject;
        TextView address,status,transaction;
        String screenshotImage;
        LinearLayout container;
        OrderRequestRecyclerViewHolder(View itemView){
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.orderRequestImage);
            name=(TextView) itemView.findViewById(R.id.orderRequestName);
            price=(TextView) itemView.findViewById(R.id.orderRequestPrice);
            screenshot=(Button) itemView.findViewById(R.id.orderRequestSSButton);
            address=(TextView) itemView.findViewById(R.id.orderRequestAddress);
            status=(TextView) itemView.findViewById(R.id.orderRequestStatus);
            transaction=(TextView) itemView.findViewById(R.id.orderRequestTransaction);
            approve=(Button) itemView.findViewById(R.id.orderRequestApproveOrder);
            container=(LinearLayout) itemView.findViewById(R.id.orderRequestAcceptRejectC);
            reject=(Button) itemView.findViewById(R.id.orderRequestRejectOrder);
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
        public void setProductStatus(String Status){status.setText("Status:"+Status);
            if(Status.equals("Approved") || Status.equals("Rejected")){
                container.setVisibility(View.GONE);
            }
            else if(Status.equals("Pending")){
                container.setVisibility(View.VISIBLE);
            }
        }
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
            approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String,Object> map=new HashMap<>();
                    map.put("Status","Approved");
                    FirebaseFirestore.getInstance().collection("Orders").document(order.getTransactionNo()).update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(mContext, "Approved", Toast.LENGTH_SHORT).show();
                                setProductStatus("Approved");
                                FirebaseFirestore.getInstance().collection("Products").document(order.getProductId()).update("ProductSellCount", FieldValue.increment(1));
                            }
                        }
                    });
                }
            });
            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String,Object> map=new HashMap<>();
                    map.put("Status","Rejected");
                    FirebaseFirestore.getInstance().collection("Orders").document(order.getTransactionNo()).update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(mContext, "Rejected", Toast.LENGTH_SHORT).show();
                                setProductStatus("Rejected");
                            }
                        }
                    });
                }
            });
        }
    }

    @Override
    public OrderRequestRecyclerAdapter.OrderRequestRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.orderrequestrecycleritem, parent, false);
        return new OrderRequestRecyclerViewHolder(view);
    }
    @Override
    public int getItemCount(){
        return orderList==null?0:orderList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRequestRecyclerViewHolder holder, int position) {
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
