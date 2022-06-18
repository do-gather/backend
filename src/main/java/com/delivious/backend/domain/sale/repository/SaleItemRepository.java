package com.delivious.backend.domain.sale.repository;


import com.delivious.backend.domain.sale.entity.SaleItem;
import com.delivious.backend.domain.cart.entity.CartItem;
import com.delivious.backend.domain.order.entity.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Integer> {
    List<SaleItem> findSaleItemsBySellerId(int sellerId);
    List<SaleItem> findAll();
}