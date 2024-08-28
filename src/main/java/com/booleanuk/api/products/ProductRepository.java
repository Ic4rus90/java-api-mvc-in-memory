package com.booleanuk.api.products;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private final List<Product> productList;

    public ProductRepository(){
        this.productList = new ArrayList<>();
    }

    public Product create(ProductRequest productRequest){
        Product product = new Product(productRequest);
        this.productList.add(product);
        return product;
    }

    public List<Product> getAll(){
        return this.productList;
    }

    public Product getProductByID(int productID){
        return productList.stream().
                filter(p -> p.getId() == productID).
                findFirst().
                orElseThrow();
    }

    public Product updateProduct(int productID, ProductRequest productRequest){
        Product product = getProductByID(productID);
        product.setName(productRequest.getName());
        product.setCategory(productRequest.getCategory());
        product.setPrice(productRequest.getPrice());
        return product;
    }

}
