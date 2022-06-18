package com.delivious.backend.domain.order.repository;


import com.delivious.backend.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findOrderItemsByUserId(int userId);
    List<OrderItem> findAll();
    OrderItem findOrderItemById(int orderItemId);
}
