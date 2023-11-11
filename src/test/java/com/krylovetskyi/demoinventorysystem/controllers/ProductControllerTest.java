package com.krylovetskyi.demoinventorysystem.controllers;

import com.krylovetskyi.demoinventorysystem.models.Product;
import com.krylovetskyi.demoinventorysystem.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

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
        when(productService.getAllProducts()).thenReturn(Arrays.asList(product1, product2));

        ModelAndView modelAndView = productController.getAllProducts();
        List<Product> products = (List<Product>) modelAndView.getModel().get("products");

        assertEquals(2, products.size());
        assertEquals("products", modelAndView.getViewName());
    }

    @Test
    void shouldGetProductById() {
        when(productService.getProductById(1)).thenReturn(Optional.of(product1));

        Product product = productController.getProductById(1);

        assertEquals(1, product.getId());
    }

    @Test
    void shouldGetAddProductView() {
        String viewName = productController.getAddProductView(model);

        verify(model, times(1)).addAttribute(eq("newProduct"), any(Product.class));
        assertEquals("addProduct", viewName);
    }

    @Test
    void shouldGetEditProductView() {
        when(productService.getProductById(1)).thenReturn(Optional.of(product1));

        String viewName = productController.getEditProductView(1, model);

        verify(model, times(1)).addAttribute("editProduct", product1);
        assertEquals("editProduct", viewName);
    }

    @Test
    void shouldRedirectWhenGetEditProductView() {
        when(productService.getProductById(1)).thenReturn(Optional.empty());

        String viewName = productController.getEditProductView(1, model);

        assertEquals("redirect:/api/products", viewName);
    }

    @Test
    void shouldAddProduct() {
        ModelAndView modelAndView = productController.addProduct(product1);

        verify(productService, times(1)).addProduct(product1);
        assertEquals("redirect:/api/products", modelAndView.getViewName());
    }

    @Test
    void shouldUpdateProduct() {
        String viewName = productController.updateProduct(1, product1);

        verify(productService, times(1)).updateProduct(product1);
        assertEquals(1, product1.getId());
        assertEquals("redirect:/api/products", viewName);
    }

    @Test
    void shouldDeleteProduct() {
        String viewName = productController.deleteProduct(1);

        verify(productService, times(1)).deleteProduct(1);
        assertEquals("redirect:/api/products", viewName);
    }
}