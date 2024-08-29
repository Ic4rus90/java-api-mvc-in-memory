package com.booleanuk.api.products;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
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
        Optional<Product> product = productList.stream().filter(p->p.getId() == productID).findFirst();
        if (product.isPresent()){
            return product.get();
        } else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Product not found.");
        }
    }

    public boolean productExists(int productID){
        return productList.stream().anyMatch(p->p.getId() == productID);
    }

    public Product update(int productID, ProductRequest productRequest){

       if (!nameIsInUse(productRequest.getName())) {
           // Gets product. Throws exception if does not exist.
           Product product = getProductByID(productID);
           product.setName(productRequest.getName());
           product.setCategory(productRequest.getCategory());
           product.setPrice(productRequest.getPrice());
           return product;
       }
        else {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Product with provided name already exists");
        }
    }

    public Product delete(int productID){
        Product product = getProductByID(productID);
        productList.remove(product);
        return product;
    }
}
