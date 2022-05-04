package com.delivious.backend.domain.users.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;


@NoArgsConstructor
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( nullable = false, length = 50)
    private UUID user_id;

    @Column( nullable = false, length = 30)
    private String password;

    @Column( nullable = false, length = 50)
    private String email;

    @Column( nullable = false)
    private Long phone_num;

    @Column( nullable = false, length = 10)
    private String name;

    private Date date_of_birth;

    @Column( nullable = false )
    //@ColumnDefault("customer")  //customer 디폴트값으로
    private String type;

    private Timestamp created_at;



    @Builder
    public User(UUID user_id, String password, String email, Long phone_num, String name, Date date_of_birth, String type, Timestamp created_at) {
        this.user_id = user_id;
        this.password = password;
        this.email = email;
        this.phone_num = phone_num;
        this.name = name;
        this.date_of_birth = date_of_birth;
        this.type = type;
        this.created_at = created_at;
    }
}