package com.ecommerce.service;

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    private String discountStartHour;
    private String discountEndHour;

    @Test
    void testCreateOrderWithItems() {

        orderService.setDiscountHours("18:00", "23:59");

        // Arrange (preparar datos de prueba)
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setFrequentClient(true);

        Product product = new Product();
        product.setId(1L);
        product.setPrice(100.0);

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(2);

        List<OrderItem> items = List.of(item);

        LocalDateTime orderDate = LocalDateTime.of(2025, 7, 11, 10, 0);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order o = invocation.getArgument(0);
            o.setId(1L);
            return o;
        });

        Order result = orderService.createOrderWithItems(userId, items, true, orderDate);

        assertNotNull(result);
        assertEquals(userId, result.getUser().getId());
        assertEquals(1L, result.getId());
        assertEquals(orderDate, result.getOrderDate());
        assertTrue(result.isActive());

        double expectedTotal = 200;

        // Descuentos:
        // +10% por rango horario
        // +50% por pedido aleatorio
        // +5% por cliente frecuente
        // Total descuento: 65%
        double expectedDiscount = expectedTotal * 0.65;
        double expectedFinalAmount = expectedTotal - expectedDiscount;

        assertEquals(expectedFinalAmount, result.getTotalAmount(), 0.01);
        assertEquals(expectedDiscount, result.getDiscountApplied(), 0.01);

        verify(userRepository).findById(userId);
        verify(productRepository).findById(1L);
        verify(orderRepository).save(any(Order.class));
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
    }

}
