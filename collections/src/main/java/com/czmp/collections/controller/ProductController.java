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
        if(productDTO.getId() != 0){
            product.setId(productDTO.getId());
        }
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setSupply(productDTO.getSupply());
        return ResponseEntity.ok(productService.save(product));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            productService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
