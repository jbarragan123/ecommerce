package com.ecommerce.service;

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Value("${discount.start}")
    private String discountStartHour;

    @Value("${discount.end}")
    private String discountEndHour;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        UserRepository userRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Order createOrderWithItems(Long userId, List<OrderItem> items,
                                      boolean isRandomOrder,
                                      java.time.LocalDateTime orderDate) {

        double total = 0;

        for (OrderItem item : items) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProduct().getId()));

            item.setPrice(product.getPrice());
            total += product.getPrice() * item.getQuantity();
        }

        double discount = 0;

        LocalTime discountStart = LocalTime.parse(discountStartHour);
        LocalTime discountEnd = LocalTime.parse(discountEndHour);
        LocalTime orderTime = orderDate.toLocalTime();

        if (orderTime.isAfter(discountStart) && orderTime.isBefore(discountEnd)) {
            discount += 0.10;
        }

        if (isRandomOrder) {
            discount += 0.50;
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        if (user.isFrequentClient()) {
            discount += 0.05;
        }

        double discountApplied = total * discount;
        double finalTotal = total - discountApplied;

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(orderDate);
        order.setTotalAmount(finalTotal);
        order.setDiscountApplied(discountApplied);

        Order savedOrder = orderRepository.save(order);

        for (OrderItem item : items) {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }

        return savedOrder;
    }

    public void setDiscountHours(String start, String end) {
        this.discountStartHour = start;
        this.discountEndHour = end;
    }


}
