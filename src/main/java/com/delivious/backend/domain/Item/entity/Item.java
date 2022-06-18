package com.delivious.backend.domain.Item.entity;



import com.delivious.backend.domain.cart.entity.CartItem;
import com.delivious.backend.domain.order.entity.OrderItem;
import com.delivious.backend.domain.sale.entity.SaleItem;
import com.delivious.backend.domain.user.User;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String text; // 물건에 대한 상세설명

    private int price; // 가격

    private int count; // 판매 개수

    private int stock; // 재고

    private int isSoldout; // 상품 상태 (0 : 판매중 / 1 : 품절)

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id")
    private User seller; // 판매자 아이디

    @OneToMany(mappedBy = "item")
    private List<CartItem>cartItems = new ArrayList<>();

    //@OneToMany(mappedBy = "item")
    //private List<OrderItem> orderItems = new ArrayList<>();

    //@OneToMany(mappedBy = "item")
    //private List<SaleItem> saleItems = new ArrayList<>();

    private String imgName; // 이미지 파일명

    private String imgPath; // 이미지 조회 경로

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate; // 상품 등록 날짜

    @PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
    public void createDate() {
        this.createDate = LocalDate.now();
    }

}



