package com.ecommerce.repository;

import com.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Listar órdenes por usuario
    List<Order> findByUserId(Long userId);

    @Query("SELECT o.user.username, COUNT(o) AS orderCount " +
            "FROM Order o " +
            "GROUP BY o.user " +
            "ORDER BY orderCount DESC")
    List<Object[]> findTopClients(Pageable pageable);
}
