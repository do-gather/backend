package com.delivious.backend.domain.menu.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;


@NoArgsConstructor
@Getter
@Entity
@Table(name = "menu")
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( nullable = false, length = 50)
    private UUID menu_id;

    @Column( nullable = false, length = 50)
    private UUID catagory_id;

    private Long img_id;

    @Column( nullable = false, length = 50)
    private Long store_num;

    @Column( nullable = false, length = 50)
    private String menu_name;

    @Column( nullable = false)
    private Long menu_price;

    @Column( nullable = false, length = 50)
    private String description;

    private Timestamp created_at;
    private Timestamp updated_at;


    @Builder

    public MenuEntity(UUID menu_id, UUID catagory_id, Long img_id, Long store_num, String menu_name, Long menu_price, String description, Timestamp created_at, Timestamp updated_at) {
        this.menu_id = menu_id;
        this.catagory_id = catagory_id;
        this.img_id = img_id;
        this.store_num = store_num;
        this.menu_name = menu_name;
        this.menu_price = menu_price;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
