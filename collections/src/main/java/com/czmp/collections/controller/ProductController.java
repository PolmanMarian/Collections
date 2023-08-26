package com.czmp.collections.controller;

import com.czmp.collections.dto.ProductDTO;
import com.czmp.collections.model.Product;
import com.czmp.collections.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;
    @GetMapping("/get")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @PostMapping("/save")
    public ResponseEntity<?> saveProduct(@RequestBody ProductDTO productDTO) {
        Product product =  new Product();
        product.setId(product.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setSupply(productDTO.getSupply());
        return ResponseEntity.ok(productService.save(product));
    }

}
