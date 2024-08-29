package com.booleanuk.api.products;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductRepository {
    private final List<Product> productList;

    public ProductRepository(){
        this.productList = new ArrayList<>();
    }

    public Product create(ProductRequest productRequest){
        if (!nameIsInUse(productRequest.getName())){
            Product product = new Product(productRequest);
            this.productList.add(product);
            return product;
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Product with the provided name already exists.");
        }
    }

    public boolean nameIsInUse(String productName){
        return productList.stream()
                .anyMatch(p->p.getName().equalsIgnoreCase(productName));
    }

    public List<Product> getAll(){
        return this.productList;
    }

    public List<Product> getProductsFromCategory(String productCategory){
        List<Product> productsInCategory = productList.stream()
                .filter(p->p.getCategory().equalsIgnoreCase(productCategory))
                .collect(Collectors.toCollection(ArrayList::new));
        if (!productsInCategory.isEmpty()){
            return productsInCategory;
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "No products of the provided category were found");
        }
    }

    public Product getProductByID(int productID){
        // Highly unnecessary, but fun.
        if (productID == 418){
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT,  "<img src='https://media2.giphy.com/media/ARmZmMqobLtZKrJRrU/200w.gif?cid=6c09b95287okszcbz59ao584wd835y08z53ju0u1pwjpok20&ep=v1_gifs_search&rid=200w.gif&ct=g'>");
        }

        Optional<Product> product = productList.stream().filter(p->p.getId() == productID).findFirst();
        if (product.isPresent()){
            return product.get();
        }
        else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Product not found.");
        }
    }


    public Product update(int productID, ProductRequest productRequest){

       if (!nameIsInUse(productRequest.getName())) {
           Product product = getProductByID(productID);
           product.setName(productRequest.getName());
           product.setCategory(productRequest.getCategory());
           product.setPrice(productRequest.getPrice());
           return product;
       }
        else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(418), "Product with provided name already exists");
        }
    }

    public Product delete(int productID){
        Product product = getProductByID(productID);
        this.productList.remove(product);
        return product;
    }
}
