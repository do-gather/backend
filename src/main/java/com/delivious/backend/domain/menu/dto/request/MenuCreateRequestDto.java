package com.delivious.backend.domain.menu.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.UUID;

@Builder
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만듦
@NoArgsConstructor //파라미터가 없는 기본 생성자를 생성
@Data
public class MenuCreateRequestDto {

    private UUID menu_id;
    private UUID category_id;
    private String menu_name;
    private Long menu_price;
    private String temperature;
    //private Integer size;
    private String description;

}
