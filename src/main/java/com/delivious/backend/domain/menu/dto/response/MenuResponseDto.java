package com.delivious.backend.domain.menu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuResponseDto {
    private UUID menu_id;
    private UUID category_id;
    private String menu_name;
    private Long menu_price;
    private String temp;
    //private String size;
    private String description;
}
