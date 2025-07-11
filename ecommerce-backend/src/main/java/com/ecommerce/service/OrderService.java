package com.ecommerce.service;

import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    // Parametrización desde application.properties
    @Value("${discount.start}")
    private String discountStartHour;

    @Value("${discount.end}")
    private String discountEndHour;

    public Order createOrder(Order order, boolean isRandomOrder) {

        double total = order.getTotalAmount();
        double discount = 0;

        LocalTime discountStart = LocalTime.parse(discountStartHour);
        LocalTime discountEnd = LocalTime.parse(discountEndHour);
        LocalTime orderTime = order.getOrderDate().toLocalTime();

        // 10% descuento si está en rango horario
        if (orderTime.isAfter(discountStart) && orderTime.isBefore(discountEnd)) {
            discount += 0.10;
        }

        // 50% descuento si es pedido aleatorio
        if (isRandomOrder) {
            discount += 0.50;
        }

        // 5% adicional si es cliente frecuente
        User user = userRepository.findById(order.getUser().getId()).orElse(null);
        if (user != null && user.isFrequentClient()) {
            discount += 0.05;
        }

        double discountApplied = total * discount;
        double finalTotal = total - discountApplied;

        order.setDiscountApplied(discountApplied);
        order.setTotalAmount(finalTotal);

        return orderRepository.save(order);
    }
}
