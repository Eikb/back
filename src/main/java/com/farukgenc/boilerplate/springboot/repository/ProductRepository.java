package com.farukgenc.boilerplate.springboot.repository;

import com.farukgenc.boilerplate.springboot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Transactional
    @Modifying
    @Query("update Product p set p.productNumber = ?1, p.price = ?2, p.stock = ?3, p.productName = ?4 where p.id = ?5")
    int updateProductNumberAndPriceAndStockAndProductNameById(Integer productNumber, Double price, Integer stock, String productName, Long id);
    Product findByEAN(Integer EAN);
    Product findByProductNumber(Integer productNumber);
}
