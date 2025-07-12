package com.ecommerce.dto;

import com.ecommerce.model.OrderItem;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderWithItemsRequest {
    private Long userId;
    private LocalDateTime orderDate;
    private boolean random;
    private List<OrderItem> items;
}
