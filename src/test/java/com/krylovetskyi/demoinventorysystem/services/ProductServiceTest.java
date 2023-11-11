package com.krylovetskyi.demoinventorysystem.services;

import com.krylovetskyi.demoinventorysystem.models.Product;
import com.krylovetskyi.demoinventorysystem.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product1;
    private Product product2;

    @BeforeEach
    public void init() {
        product1 = new Product();
        product1.setId(1);
        product1.setName("A");

        product2 = new Product();
        product2.setId(2);
        product2.setName("B");
    }

    @Test
    void shouldGetAllProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        List<Product> products = productService.getAllProducts();

        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void shouldGetProductById() {
        when(productRepository.existsById(any())).thenReturn(true);
        when(productRepository.findById(1)).thenReturn(Optional.of(product1));
        Optional<Product> product = productService.getProductById(1);

        assertTrue(product.isPresent());
        assertEquals(product1.getId(), product.get().getId());
    }

    @Test
    void shouldAddProduct() {
        when(productRepository.save(any())).thenReturn(product1);
        Product product = productService.addProduct(product1);

        assertEquals(product1.getName(), product.getName());
    }

    @Test
    void shouldUpdateProduct() {
        when(productRepository.existsById(any())).thenReturn(true);
        when(productRepository.save(any())).thenReturn(product1);
        Product product = productService.updateProduct(product1);

        assertEquals(product1.getName(), product.getName());
    }

    @Test
    void shouldDeleteProduct() {
        when(productRepository.existsById(any())).thenReturn(true);
        doNothing().when(productRepository).deleteById(1);
        productService.deleteProduct(1);

        verify(productRepository, times(1)).deleteById(1);
    }
}