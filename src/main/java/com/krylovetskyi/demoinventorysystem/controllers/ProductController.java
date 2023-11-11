package com.krylovetskyi.demoinventorysystem.controllers;

import com.krylovetskyi.demoinventorysystem.exceptions.ProductNotFoundException;
import com.krylovetskyi.demoinventorysystem.models.Product;
import com.krylovetskyi.demoinventorysystem.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private static final String REDIRECT_TO_INDEX_PAGE = "redirect:/api/products";
    private static final String PRODUCT_NOT_FOUND = "Product not found";
    private static final String NEW_PRODUCT = "newProduct";
    private static final String ABSENT_PRODUCT_WAS_CHOSEN = "In view was chosen product which is absent in database";
    private static final String EDIT_PRODUCT = "editProduct";
    private static final String ID = "id";
    private static final String ADD_PRODUCT = "addProduct";
    private static final String PRODUCTS = "products";

    private final ProductService productService;

    @GetMapping
    public ModelAndView getAllProducts() {
        List<Product> products = productService.getAllProducts();
        ModelAndView modelAndView = new ModelAndView(PRODUCTS);
        modelAndView.addObject(PRODUCTS, products);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable(ID) Integer id) {
        return productService.getProductById(id).orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
    }

    @GetMapping("/getAddProductView")
    public String getAddProductView(Model model) {
        model.addAttribute(NEW_PRODUCT, new Product());
        return ADD_PRODUCT;
    }

    @GetMapping("/getEditProductView/{id}")
    public String getEditProductView(@PathVariable(ID) Integer id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isEmpty()) {
            log.warn(ABSENT_PRODUCT_WAS_CHOSEN);
            return REDIRECT_TO_INDEX_PAGE;
        }
        model.addAttribute(EDIT_PRODUCT, product.get());
        return EDIT_PRODUCT;
    }

    @PostMapping
    public ModelAndView addProduct(@ModelAttribute Product product) {
        productService.addProduct(product);
        return new ModelAndView(REDIRECT_TO_INDEX_PAGE);
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable(ID) Integer id,
                                @ModelAttribute(EDIT_PRODUCT) Product product) {
        product.setId(id);
        productService.updateProduct(product);
        return REDIRECT_TO_INDEX_PAGE;
    }

    @PostMapping("/{id}")
    public String deleteProduct(@PathVariable(ID) Integer id) {
        productService.deleteProduct(id);
        return REDIRECT_TO_INDEX_PAGE;
    }
}