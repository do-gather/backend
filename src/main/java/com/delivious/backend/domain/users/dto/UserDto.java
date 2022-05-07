//package com.delivious.backend.domain.users.dto;
//
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.*;
//import com.delivious.backend.domain.users.entity.UserEntity;
//
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//import java.sql.Timestamp;
//import java.util.Date;
//import java.util.Set;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public class UserDto {
//
//    @NotNull
//    @Size(min = 3, max = 50)
//
//    private String email;
//
//    private String user_id;
//
//
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @NotNull
//    @Size(min = 3, max = 100)
//    private String password;
//
//    @Column( nullable = false)
//    private Long phone_num;
//
//    @Column( nullable = false, length = 10)
//    private String name;
//
//    private Date date_of_birth;
//
//    private Set<AuthorityDto> authorityDtoSet;
//
//    public static UserDto from(UserEntity user) {
//        if(user == null) return null;
//
//        return UserDto.builder()
//
//                .email(user.getEmail())
//
//                .user_id(user.getUser_id())
//                //.nickname(user.getNickname())     //우리 코드에 필요없음
//
//                .authorityDtoSet(user.getAuthorities().stream()
//                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
//                        .collect(Collectors.toSet()))
//                .build();
//    }
//}
