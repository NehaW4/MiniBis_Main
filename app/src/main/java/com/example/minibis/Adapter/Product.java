package com.example.minibis.Adapter;

import java.util.Date;

public class Product {
    String ProductName,ProductPrice,ProductDescription,ProductCategory,ProductImage,ProductSeller,ProductSellerLogo,ProductSellerUid;
    Date ProductAddedDate;
    int ProductSellCount;
    String DocumentId;

    Product(){}

    public Product(String productName, String productPrice, String productDescription, String productCategory, String productImage, String productSeller, String productSellerLogo, String productSellerUid, Date productAddedDate, int productSellCount) {
        ProductName = productName;
        ProductPrice = productPrice;
        ProductDescription = productDescription;
        ProductCategory = productCategory;
        ProductImage = productImage;
        ProductSeller = productSeller;
        ProductSellerLogo = productSellerLogo;
        ProductSellerUid = productSellerUid;
        ProductAddedDate = productAddedDate;
        ProductSellCount = productSellCount;
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

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getProductCategory() {
        return ProductCategory;
    }

    public void setProductCategory(String productCategory) {
        ProductCategory = productCategory;
    }

    public String getProductImage() {
        return ProductImage;
    }

    public void setProductImage(String productImage) {
        ProductImage = productImage;
    }

    public String getProductSeller() {
        return ProductSeller;
    }

    public void setProductSeller(String productSeller) {
        ProductSeller = productSeller;
    }

    public String getProductSellerLogo() {
        return ProductSellerLogo;
    }

    public void setProductSellerLogo(String productSellerLogo) {
        ProductSellerLogo = productSellerLogo;
    }

    public String getProductSellerUid() {
        return ProductSellerUid;
    }

    public void setProductSellerUid(String productSellerUid) {
        ProductSellerUid = productSellerUid;
    }

    public String getDocumentId() {
        return DocumentId;
    }

    public void setDocumentId(String documentId) {
        DocumentId = documentId;
    }

    public Date getProductAddedDate() {
        return ProductAddedDate;
    }

    public void setProductAddedDate(Date productAddedDate) {
        ProductAddedDate = productAddedDate;
    }

    public int getProductSellCount() {
        return ProductSellCount;
    }

    public void setProductSellCount(int productSellCount) {
        ProductSellCount = productSellCount;
    }
}
