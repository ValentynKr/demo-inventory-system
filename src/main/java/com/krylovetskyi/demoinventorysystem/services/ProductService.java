package com.krylovetskyi.demoinventorysystem.services;

import com.krylovetskyi.demoinventorysystem.models.Product;
import com.krylovetskyi.demoinventorysystem.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

}