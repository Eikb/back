package com.farukgenc.boilerplate.springboot.service;

import com.farukgenc.boilerplate.springboot.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();
    Product getProductById(Long productId);

    Product findByProductNumber(Integer productNumber);
    Product findByEan(Integer ean);
    Product addProduct(Product product);
    int editProduct(Product product);
    void deleteProduct(Long productId);


}
