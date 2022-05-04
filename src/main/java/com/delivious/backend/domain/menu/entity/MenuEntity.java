package com.delivious.backend.domain.menu.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;


@NoArgsConstructor
@Builder
@Getter
@AllArgsConstructor
@Entity
@Table(name = "menu")
public class MenuEntity {

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( nullable = false, length = 50)
    private UUID menu_id;

    @ManyToOne(fetch = FetchType.LAZY)
    private CategoryEntity catagory_id;

    // 여기서 img_id or img_url
//    @OneToOne(fetch = FetchType.LAZY)
//    private Long img_id;
    // private String img_url;

    // 지워도 되지 않을까요? figma에는 없어서요!
//    @Column( nullable = false, length = 50)
//    private Long store_num;

    @Column( nullable = false, length = 50)
    private String menu_name;

    @Column( nullable = false)
    private Long menu_price;

    @Column(nullable = false)
    private String temperature;

    // size를 db에 어떤식으로 넣어야 할지
    // 라떼 tall, grande, venti 3가지가 다 따로 들어가는지
//    @Column(nullable = false)
//    private Integer size;

    @Column( nullable = false, length = 50)
    private String description;

    private Timestamp created_at;
    private Timestamp updated_at;

    // 메뉴 변경 함수 -> img_url, size 이후에 추가
    public void modify(String menu_name, Long menu_price, String description) {
        this.menu_name = menu_name;
        this.menu_price = menu_price;
        this.description = description;
    }
}
