package com.delivious.backend.domain.sale.entity;


import com.delivious.backend.domain.sale.entity.SaleItem;
import com.delivious.backend.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="seller_id")
    private User seller; // 판매자

    @OneToMany(mappedBy = "sale")
    private List<SaleItem> saleItems = new ArrayList<>();

    private int totalCount; // 총 판매 개수

    public static Sale createSale(User seller) {
        Sale sale = new Sale();
        sale.setSeller(seller);
        sale.setTotalCount(0);
        return sale;
    }

}
