package com.maveric.realtimedatapoc.service;

import com.maveric.realtimedatapoc.entity.Order;
import com.maveric.realtimedatapoc.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public String saveOrder(Order order) {
        orderRepository.save(order);
        return "saved successfully";
    }

    public String saveOrders(List<Order> orders) {
        orderRepository.saveAll(orders);
        return "saved successfully";
    }

    public Order getOrderById(Long id) {
        return orderRepository.getReferenceById(id);
    }
}
