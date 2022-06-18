package com.delivious.backend.domain.sale.repository;


import com.delivious.backend.domain.order.entity.Order;
import com.delivious.backend.domain.sale.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
    List<Sale> findAll();
    List<Sale> findSalesById(int id);
    Sale findBySellerId(int id);
}
