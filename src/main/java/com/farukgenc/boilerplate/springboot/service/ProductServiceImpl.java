package com.farukgenc.boilerplate.springboot.service;

import com.farukgenc.boilerplate.springboot.model.Product;
import com.farukgenc.boilerplate.springboot.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();

    }

    @Override
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).get();
    }

    @Override
    public Product findByProductNumber(Integer productNumber) {
        return productRepository.findByProductNumber(productNumber);

    }

    @Override
    public Product findByEan(Integer ean) {
        return productRepository.findByEAN(ean);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public int editProduct(Product product) {
        return productRepository.updateProductNumberAndPriceAndStockAndProductNameById(product.getProductNumber(), product.getPrice(), product.getStock(), product.getProductName(), product.getId());
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
