package com.delivious.backend.domain.menu.entity;


import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Builder
@Table(name = "category")

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( nullable = false, length = 50)
    private UUID category_id;

    private String category_name;

    private Timestamp created_at;
    private Timestamp updated_at;

    /*Menu Entity에서 Category를 ManyToOne으로 매핑
        Category Entity에서는 OneToMany로 매핑 후
        MenuEntity의 Category categoryentity 필드에 의해 매핑
     */

//    @OneToMany(mappedBy = "categoryentity")
//    private List<Menu> menus = new ArrayList<>();
//


}
