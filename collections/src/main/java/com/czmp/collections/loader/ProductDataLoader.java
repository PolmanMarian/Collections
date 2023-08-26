package com.czmp.collections.loader;

import com.czmp.collections.model.Product;
import com.czmp.collections.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ProductDataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Autowired
    public ProductDataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create a list of mock products
        List<Product> products = Arrays.asList(
                new Product(1L, "Product 1", 10.99, 100L),
                new Product(2L, "Product 2", 15.99, 50L),
                new Product(3L, "Product 3", 8.99, 200L),
                new Product(4L, "Product 4", 12.99, 75L),
                new Product(5L, "Product 5", 6.99, 150L)
        );

        // Save the products to the database
        productRepository.saveAll(products);
    }
}
