package com.ecommerce.controller;

import com.ecommerce.model.Order;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.service.AuditService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final AuditService auditService;

    // Constructor con inyección de dependencias
    public OrderController(OrderRepository orderRepository, AuditService auditService) {
        this.orderRepository = orderRepository;
        this.auditService = auditService;
    }

    @GetMapping
    public List<Order> getAllOrders(@RequestHeader("X-User-Id") Long userId) {
        List<Order> orders = orderRepository.findAll();
        auditService.logAction("User " + userId + " listed all orders", "Order", null);
        return orders;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order, @RequestHeader("X-User-Id") Long userId) {
        Order saved = orderRepository.save(order);
        auditService.logAction("User " + userId + " created order", "Order", saved.getId());
        return saved;
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        Order order = orderRepository.findById(id).orElse(null);
        auditService.logAction("User " + userId + " viewed order", "Order", id);
        return order;
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order, @RequestHeader("X-User-Id") Long userId) {
        order.setId(id);
        Order updated = orderRepository.save(order);
        auditService.logAction("User " + userId + " updated order", "Order", id);
        return updated;
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        orderRepository.deleteById(id);
        auditService.logAction("User " + userId + " deleted order", "Order", id);
    }
}