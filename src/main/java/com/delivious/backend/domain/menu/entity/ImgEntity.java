package com.delivious.backend.domain.menu.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Timestamp;


@NoArgsConstructor
@Getter
@Entity
@Table(name = "img")

public class ImgEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( nullable = false, length = 50)
    private Long img_id;

    @Column( nullable = false, length = 500)
    private String img_url;

    private Timestamp created_at;
    private Timestamp updated_at;


    @Builder

    public ImgEntity(Long img_id, String img_url, Timestamp created_at, Timestamp updated_at) {
        this.img_id = img_id;
        this.img_url = img_url;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
