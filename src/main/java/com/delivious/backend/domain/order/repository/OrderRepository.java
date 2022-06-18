package com.delivious.backend.domain.order.repository;


import com.delivious.backend.domain.order.entity.Order;
import com.delivious.backend.domain.cart.entity.Cart;
import com.delivious.backend.domain.Item.entity.Item;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findOrdersByUserId(int id);
}
