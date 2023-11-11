package com.krylovetskyi.demoinventorysystem.services;

import com.krylovetskyi.demoinventorysystem.exceptions.ProductNotFoundException;
import com.krylovetskyi.demoinventorysystem.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql({"/schema_test.sql", "/data_test.sql"})
class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;

    @Test
    void shouldGetAllProducts() {
        List<Product> products = productService.getAllProducts();
        assertEquals(2, products.size());
    }

    @Test
    void shouldGetProductById() {
        Optional<Product> product = productService.getProductById(4);
        assertTrue(product.isPresent());
        assertEquals("Test Product 4", product.get().getName());
    }

    @Test
    void shouldAddProduct() {
        Product newProduct = new Product();
        newProduct.setName("Test Product 15");
        newProduct.setDescription("Test Description 10");
        newProduct.setPrice(new BigDecimal("30.00"));
        newProduct.setQuantity(30);

        Product addedProduct = productService.addProduct(newProduct);

        assertNotNull(addedProduct.getId());
        assertEquals(newProduct.getName(), addedProduct.getName());
        assertEquals(newProduct.getDescription(), addedProduct.getDescription());
        assertEquals(newProduct.getPrice(), addedProduct.getPrice());
        assertEquals(newProduct.getQuantity(), addedProduct.getQuantity());
    }

    @Test
    void shouldUpdateProduct() {
        Product newProduct = new Product();
        newProduct.setName("Test Product 3");
        newProduct.setDescription("Test Description 3");
        newProduct.setPrice(new BigDecimal("30.00"));
        newProduct.setQuantity(30);

        Product updatedProduct = productService.addProduct(newProduct);

        updatedProduct.setName("Updated Test Product 3");
        updatedProduct.setDescription("Updated Test Description 3");
        updatedProduct.setPrice(new BigDecimal("11.00"));
        updatedProduct.setQuantity(11);

        Product result = productService.updateProduct(updatedProduct);
        productService.deleteProduct(updatedProduct.getId());

        assertEquals(updatedProduct.getId(), result.getId());
        assertEquals(updatedProduct.getName(), result.getName());
        assertEquals(updatedProduct.getDescription(), result.getDescription());
        assertEquals(updatedProduct.getPrice(), result.getPrice());
        assertEquals(updatedProduct.getQuantity(), result.getQuantity());
    }

    @Test
    void shouldDeleteProduct() {
        Product newProduct = new Product();
        newProduct.setName("Test Product 3");
        newProduct.setDescription("Test Description 3");
        newProduct.setPrice(new BigDecimal("30.00"));
        newProduct.setQuantity(30);

        Product addedProduct = productService.addProduct(newProduct);
        productService.deleteProduct(addedProduct.getId());
        int deletedProductId = addedProduct.getId();

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(deletedProductId));

        assertEquals(String.format("Product with ID %d not found", deletedProductId), exception.getMessage());
    }
}