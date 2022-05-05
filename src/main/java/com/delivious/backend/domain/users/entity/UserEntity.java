package com.delivious.backend.domain.users.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;
import java.util.UUID;


@NoArgsConstructor
@Getter
@Entity
@Table(name = "users")
public class UserEntity {
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

    @Column(name = "activated")
    private boolean activated;

    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;

    @Builder

    public UserEntity(UUID user_id, String password, String email, Long phone_num, String name, Date date_of_birth, String type, Timestamp created_at, boolean activated, Set<Authority> authorities) {
        this.user_id = user_id;
        this.password = password;
        this.email = email;
        this.phone_num = phone_num;
        this.name = name;
        this.date_of_birth = date_of_birth;
        this.type = type;
        this.created_at = created_at;
        this.activated = activated;
        this.authorities = authorities;
    }
}
