package com.krylovetskyi.demoinventorysystem.controllers;

import com.krylovetskyi.demoinventorysystem.exceptions.ProductNotFoundException;
import com.krylovetskyi.demoinventorysystem.models.Product;
import com.krylovetskyi.demoinventorysystem.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    public static final String REDIRECT_TO_INDEX_PAGE = "redirect:/api/products";
    private final ProductService productService;

    @GetMapping
    public ModelAndView getAllProducts() {
        List<Product> products = productService.getAllProducts();
        ModelAndView modelAndView = new ModelAndView("products");
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Integer id) {
        return productService.getProductById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @GetMapping("/getAddProductView")
    public String getAddProductView(Model model) {
        model.addAttribute("newProduct", new Product());
        return "addProduct";
    }

    @GetMapping("/getEditProductView/{id}")
    public String getEditProductView(@PathVariable("id") Integer id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isEmpty()) {
            log.warn("In view was chosen product which is absent in database");
            return REDIRECT_TO_INDEX_PAGE;
        }
        model.addAttribute("editProduct", product.get());
        return "editProduct";
    }

    @PostMapping
    public ModelAndView addProduct(@ModelAttribute Product product) {
        productService.addProduct(product);
        return new ModelAndView(REDIRECT_TO_INDEX_PAGE);
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Integer id,
                                @ModelAttribute("updatedProduct") Product product) {
        product.setId(id);
        productService.updateProduct(product);
        return REDIRECT_TO_INDEX_PAGE;
    }

    @PostMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Integer id) {
        productService.deleteProduct(id);
        return REDIRECT_TO_INDEX_PAGE;
    }
}
