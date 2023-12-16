package com.farukgenc.boilerplate.springboot.controller;

import com.farukgenc.boilerplate.springboot.model.Product;
import com.farukgenc.boilerplate.springboot.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Get All Products")
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = "Get Product By ID")
    @GetMapping("/id/{id}")
    public ResponseEntity<Product> getProductById( @PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Get Product By ProductNumber")
    @GetMapping("/number/{productNumber}")
    public ResponseEntity<Product> getProductByProductNumber(@PathVariable Integer productNumber){
        return ResponseEntity.ok(productService.findByProductNumber(productNumber));
    }

    @Operation(summary = "Get Product By EAN")
    @GetMapping("/ean/{ean}")
    public ResponseEntity<Product> getProductByEan(@PathVariable Integer ean){
        return ResponseEntity.ok(productService.findByEan(ean));
    }
    @Operation(summary = "Creates Product")
    @PostMapping("/create")
    public ResponseEntity<String> createProduct(@RequestBody Product product){
        try {
            productService.addProduct(product);
            return ResponseEntity.ok("Ürün Kayit edilmistir");
        }
        catch (Exception e)
        {
            return ResponseEntity.ok("Ürün kaydinda hata olustu: " + e.getMessage());
        }
    }

    @Operation(summary = "Edit Product")
    @PutMapping("/edit")
    public ResponseEntity<String> editProduct(@RequestBody Product product){
        try {
            productService.editProduct(product);
            return ResponseEntity.ok("Ürün Tazelendi yeni ürün: " + product);
        }catch (Exception e){
            return ResponseEntity.ok("Ürün Tazelemede hata olustu: " + e.getMessage());
        }
    }

    @Operation(summary = "Delete Product by Id")
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok("Ürün basariyla silinmistir");
        }
        catch (Exception e){
            return ResponseEntity.ok("Ürün Silinmede hata olustu: " + e.getMessage());
        }
    }
}
