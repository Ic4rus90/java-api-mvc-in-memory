package com.booleanuk.api.products;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    ProductRepository repository;

    public ProductController(){
        this.repository = new ProductRepository();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody ProductRequest productRequest){
        return this.repository.create(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAll(){
        return this.repository.getAll();
    }

    @GetMapping("/{productID}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductByID(@PathVariable int productID){
        return this.repository.getProductByID(productID);
    }

    @PutMapping("/{productID}")
    @ResponseStatus(HttpStatus.CREATED)
    public Product updateProduct(@PathVariable int productID, @RequestBody ProductRequest productRequest){
        return this.repository.update(productID, productRequest);
    }

    @DeleteMapping("/{productID}")
    @ResponseStatus(HttpStatus.OK)
    public Product deleteProduct(@PathVariable int productID){
        return this.repository.delete(productID);
    }
}
