package com.czmp.collections.controller;

import com.czmp.collections.dto.OrderDTO;
import com.czmp.collections.dto.OrderProductDTO;
import com.czmp.collections.model.Order;
import com.czmp.collections.model.OrderProduct;
import com.czmp.collections.model.Product;
import com.czmp.collections.model.Status;
import com.czmp.collections.service.OrderService;
import com.czmp.collections.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping("/order")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @GetMapping("/get")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }
    @PostMapping("/addOrder")
    public ResponseEntity<?> addOrder(@RequestBody OrderDTO orders) {
        Order order = new Order();
        order.setOrderProducts(new ArrayList<>());
        order.setDate(new Date());
        order.setStatus(Status.PREPARING);
        Double price = 0.0;

        for(OrderProductDTO orderProductDTO : orders.getProductList()) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            try {
                Product product = productService.getById(orderProductDTO.getProductId());
                Long quantity= orderProductDTO.getQuantity();
                Long availableQuantity = productService.getAllowedQuantityAndSave(quantity, product);
                if (availableQuantity > 0) {
                    orderProduct.setQuantity(availableQuantity);
                    price = price + product.getPrice() * availableQuantity;
                    order.getOrderProducts().add(orderProduct);
                }
            } catch (Exception e) {
                return ResponseEntity.notFound().build();
            }
        }
        order.setPrice(price);
        return ResponseEntity.ok(orderService.save(order));
    }


}
