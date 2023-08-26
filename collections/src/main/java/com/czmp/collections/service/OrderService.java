package com.czmp.collections.service;

import com.czmp.collections.model.Order;
import com.czmp.collections.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderService;
    public Object getAll() {

        return orderService.findAll();
    }
    public Object save(Order order) {
        return orderService.save(order);
    }

}
