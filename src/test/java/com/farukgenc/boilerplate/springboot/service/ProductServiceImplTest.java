package com.farukgenc.boilerplate.springboot.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.farukgenc.boilerplate.springboot.model.Product;
import com.farukgenc.boilerplate.springboot.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Ignore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(classes = {ProductServiceImpl.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductServiceImplTest {
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductServiceImpl productServiceImpl;

    /**
     * Method under test: {@link ProductServiceImpl#getAllProducts()}
     */
    @Test
    public void testGetAllProducts() {
        ArrayList<Product> productList = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(productList);
        List<Product> actualAllProducts = productServiceImpl.getAllProducts();
        assertSame(productList, actualAllProducts);
        assertTrue(actualAllProducts.isEmpty());
        verify(productRepository).findAll();
    }

    /**
     * Method under test: {@link ProductServiceImpl#getProductById(Long)}
     */
    @Test
    public void testGetProductById() {
        Product product = new Product();
        product.setEAN(1);
        product.setId(1L);
        product.setPrice(10.0d);
        product.setProductName("Product Name");
        product.setProductNumber(10);
        product.setStock(1);
        Optional<Product> ofResult = Optional.of(product);
        when(productRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(product, productServiceImpl.getProductById(1L));
        verify(productRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ProductServiceImpl#getProductById(Long)}
     */
    @Test
    @Ignore("TODO: Complete this test")
    public void testGetProductById2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.util.NoSuchElementException: No value present
        //       at java.util.Optional.get(Optional.java:135)
        //       at com.farukgenc.boilerplate.springboot.service.ProductServiceImpl.getProductById(ProductServiceImpl.java:26)
        //   See https://diff.blue/R013 to resolve this issue.

        when(productRepository.findById((Long) any())).thenReturn(Optional.empty());
        productServiceImpl.getProductById(1L);
    }

    /**
     * Method under test: {@link ProductServiceImpl#findByProductNumber(Integer)}
     */
    @Test
    public void testFindByProductNumber() {
        Product product = new Product();
        product.setEAN(1);
        product.setId(1L);
        product.setPrice(10.0d);
        product.setProductName("Product Name");
        product.setProductNumber(10);
        product.setStock(1);
        when(productRepository.findByProductNumber((Integer) any())).thenReturn(product);
        assertSame(product, productServiceImpl.findByProductNumber(10));
        verify(productRepository).findByProductNumber((Integer) any());
    }

    /**
     * Method under test: {@link ProductServiceImpl#findByEan(Integer)}
     */
    @Test
    public void testFindByEan() {
        Product product = new Product();
        product.setEAN(1);
        product.setId(1L);
        product.setPrice(10.0d);
        product.setProductName("Product Name");
        product.setProductNumber(10);
        product.setStock(1);
        when(productRepository.findByEAN((Integer) any())).thenReturn(product);
        assertSame(product, productServiceImpl.findByEan(1));
        verify(productRepository).findByEAN((Integer) any());
    }

    /**
     * Method under test: {@link ProductServiceImpl#addProduct(Product)}
     */
    @Test
    public void testAddProduct() {
        Product product = new Product();
        product.setEAN(1);
        product.setId(1L);
        product.setPrice(10.0d);
        product.setProductName("Product Name");
        product.setProductNumber(10);
        product.setStock(1);
        when(productRepository.save((Product) any())).thenReturn(product);

        Product product1 = new Product();
        product1.setEAN(1);
        product1.setId(1L);
        product1.setPrice(10.0d);
        product1.setProductName("Product Name");
        product1.setProductNumber(10);
        product1.setStock(1);
        assertSame(product, productServiceImpl.addProduct(product1));
        verify(productRepository).save((Product) any());
    }

    /**
     * Method under test: {@link ProductServiceImpl#editProduct(Product)}
     */
    @Test
    public void testEditProduct() {
        when(productRepository.updateProductNumberAndPriceAndStockAndProductNameById((Integer) any(), (Double) any(),
                (Integer) any(), (String) any(), (Long) any())).thenReturn(1);

        Product product = new Product();
        product.setEAN(1);
        product.setId(1L);
        product.setPrice(10.0d);
        product.setProductName("Product Name");
        product.setProductNumber(10);
        product.setStock(1);
        assertEquals(1, productServiceImpl.editProduct(product));
        verify(productRepository).updateProductNumberAndPriceAndStockAndProductNameById((Integer) any(), (Double) any(),
                (Integer) any(), (String) any(), (Long) any());
    }

    /**
     * Method under test: {@link ProductServiceImpl#deleteProduct(Long)}
     */
    @Test
    public void testDeleteProduct() {
        doNothing().when(productRepository).deleteById((Long) any());
        productServiceImpl.deleteProduct(1L);
        verify(productRepository).deleteById((Long) any());
    }
}

