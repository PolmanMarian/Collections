package com.czmp.collections.service;

import com.czmp.collections.model.Order;
import com.czmp.collections.model.Status;
import com.czmp.collections.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    public Object getAll() {
        return orderRepository.findAll();
    }

    public void delete(Long id){
        orderRepository.deleteById(id);
    }
    public Object save(Order order) {
        return orderRepository.save(order);
    }

    public void updateStatus(Long id){
        Order order = orderRepository.getById(id);
        Status nextStatus = getNextStatus(order.getStatus());
        order.setStatus(nextStatus);
        orderRepository.save(order);
    }

    public Status getNextStatus(Status status) {
        switch (status) {
            case NEW:
                return Status.PREPARING;
            case PREPARING:
                return Status.DONE;
            default:
                return status;
        }
    }

}
