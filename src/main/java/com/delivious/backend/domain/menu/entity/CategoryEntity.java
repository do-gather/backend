package com.delivious.backend.domain.menu.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;


@NoArgsConstructor
@Getter
@Entity
@Table(name = "category")

public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( nullable = false, length = 50)
    private UUID category_id;

    private Timestamp created_at;
    private Timestamp updated_at;


}
