package com.example.minibis.Adapter;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    String ProductId,SellerId,Status,TransactionNo,TransactionScreenshot,DeliveryAddress,ProductName,ProductPrice,ProductImage;
    Date OrderDate;
    String CustomerId;

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public Order() {
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getSellerId() {
        return SellerId;
    }

    public void setSellerId(String sellerId) {
        SellerId = sellerId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTransactionNo() {
        return TransactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        TransactionNo = transactionNo;
    }

    public String getTransactionScreenshot() {
        return TransactionScreenshot;
    }

    public void setTransactionScreenshot(String transactionScreenshot) {
        TransactionScreenshot = transactionScreenshot;
    }

    public String getDeliveryAddress() {
        return DeliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        DeliveryAddress = deliveryAddress;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(String productPrice) {
        ProductPrice = productPrice;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date orderDate) {
        OrderDate = orderDate;
    }

    public Order(String productId, String sellerId, String status, String transactionNo, String transactionScreenshot, String deliveryAddress, String productName, String productPrice, String productImage, Date orderDate, String customerId) {
        ProductId = productId;
        SellerId = sellerId;
        Status = status;
        TransactionNo = transactionNo;
        TransactionScreenshot = transactionScreenshot;
        DeliveryAddress = deliveryAddress;
        ProductName = productName;
        ProductPrice = productPrice;
        ProductImage = productImage;
        OrderDate = orderDate;
        CustomerId = customerId;
    }
}
