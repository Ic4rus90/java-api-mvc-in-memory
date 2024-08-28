package com.booleanuk.api.products;

public class ProductRequest {
    private String name;
    private String category;
    private int price;

    public ProductRequest(String productName, String productCategory, int productPrice){
        this.name = productName;
        this.category = productCategory;
        this.price = productPrice;
    }

    public String getName(){
        return this.name;
    }

    public String getCategory(){
        return this.category;
    }

    public int getPrice(){
        return this.price;
    }
}
