package com.delivious.backend.domain.menu.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Builder
@Table(name = "img")

public class Img {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( nullable = false, length = 50)
    private Long img_id;

    @Column( nullable = false, length = 500)
    private String img_url;

    private Timestamp created_at;
    private Timestamp updated_at;

}
