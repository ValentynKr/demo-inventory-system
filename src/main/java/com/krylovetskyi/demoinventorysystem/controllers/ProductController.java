package com.krylovetskyi.demoinventorysystem.controllers;

import com.krylovetskyi.demoinventorysystem.exceptions.ProductNotFoundException;
import com.krylovetskyi.demoinventorysystem.models.Product;
import com.krylovetskyi.demoinventorysystem.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

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

    @PostMapping
    public ModelAndView addProduct(@ModelAttribute Product product) {
        productService.addProduct(product);
        return new ModelAndView("redirect:/api/products");
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Integer id, @RequestBody Product product) {
        product.setId(id);
        return productService.updateProduct(product);
    }

    @PostMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Integer id) {
        productService.deleteProduct(id);
        return "redirect:/api/products";
    }
}
