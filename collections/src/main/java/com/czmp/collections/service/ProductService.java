package com.czmp.collections.service;

import com.czmp.collections.model.Product;
import com.czmp.collections.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    public Object save(Product product) {
        return productRepository.save(product);
    }
    public Product getById(Long id) throws Exception {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            return product.get();
        }
        throw new Exception();
    }

    public Long getAllowedQuantityAndSave(Long quantity, Product product){
        Long supply = product.getSupply();
        if(supply < quantity) {
            product.setSupply(0L);
            productRepository.save(product);
            return product.getSupply();
        }
        else {
            product.setSupply(supply - quantity);
            productRepository.save(product);
            return quantity;
        }
    }

}
