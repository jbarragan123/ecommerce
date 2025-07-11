package com.ecommerce.controller;

import com.ecommerce.model.Order;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.service.AuditService;
import com.ecommerce.service.OrderService;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final AuditService auditService;
    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderRepository orderRepository,
                           AuditService auditService,
                           OrderItemRepository orderItemRepository,
                           OrderService orderService) {
        this.orderRepository = orderRepository;
        this.auditService = auditService;
        this.orderItemRepository = orderItemRepository;
        this.orderService = orderService;
    }

    // Obtener todas las órdenes
    @GetMapping
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        auditService.logAction("listed all orders", "Order", null);
        return orders;
    }

    // Crear nueva orden con descuentos especiales
    @PostMapping
    public Order createOrder(@RequestBody Order order,
                             @RequestParam(defaultValue = "false") boolean random) {
        Order saved = orderService.createOrder(order, random);
        auditService.logAction("created order", "Order", saved.getId());
        return saved;
    }

    // Obtener orden por id
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
        auditService.logAction("viewed order", "Order", id);
        return order;
    }

    // Actualizar orden por id
    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        order.setId(id);
        Order updated = orderRepository.save(order);
        auditService.logAction("updated order", "Order", id);
        return updated;
    }

    // Eliminar orden por id
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
        auditService.logAction("deleted order", "Order", id);
    }

    // Reporte: Top 5 productos más vendidos
    @GetMapping("/reports/top-products")
    public List<Object[]> getTopProducts() {
        Pageable topFive = PageRequest.of(0, 5);
        return orderItemRepository.findTopProducts(topFive);
    }
}
