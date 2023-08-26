package com.czmp.collections.loader;

import com.czmp.collections.dto.UserRegisterDTO;
import com.czmp.collections.model.Product;
import com.czmp.collections.model.Role;
import com.czmp.collections.model.User;
import com.czmp.collections.repository.ProductRepository;
import com.czmp.collections.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ProductDataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProductDataLoader(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
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

        List<User> users = Arrays.asList(
                new User(0L,"Marian","$2a$10$gmtrs1G4TrYSz5N2OESlmuMcAukgAS1KIPMfX3/P49GE8l6jQsXOK", Role.ADMIN),
                new User(0L,"Andrei","$2a$10$gmtrs1G4TrYSz5N2OESlmuMcAukgAS1KIPMfX3/P49GE8l6jQsXOK",Role.ADMIN),
                new User(0L,"Cezar","$2a$10$gmtrs1G4TrYSz5N2OESlmuMcAukgAS1KIPMfX3/P49GE8l6jQsXOK",Role.USER)
        );

        userRepository.saveAll(users);

    }
}
