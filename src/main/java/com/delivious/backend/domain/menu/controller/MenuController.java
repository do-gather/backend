package com.delivious.backend.domain.menu.controller;
import com.delivious.backend.domain.menu.dto.MenuResponseDto;
import com.delivious.backend.domain.menu.dto.MenuSaveDto;
import com.delivious.backend.domain.menu.dto.MenuUpdateDto;
import com.delivious.backend.domain.menu.entity.Menu;
import com.delivious.backend.domain.menu.service.MenuService;
import com.delivious.backend.global.utils.ErrorResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    //메뉴 추가
    @ResponseBody
    @PostMapping
    public MenuSaveDto save(@RequestBody MenuSaveDto menuSaveDto) {
        Menu menu = menuService.save(menuSaveDto);
        return MenuSaveDto.fromEntity(menu);
    }

    // 메뉴조회
    @ResponseBody
    @GetMapping("/{menu_id}")
    public ResponseEntity getDetail(@PathVariable UUID menu_id) {

        Optional<Menu> entity = menuService.findById(menu_id);
        return ResponseEntity
                .ok()
                .body(MenuSaveDto.fromEntity(entity.get()));
    }

    // 메뉴 수정
    @ResponseBody
    @PutMapping("/{menu_id}")
    public ResponseEntity<MenuResponseDto> updateMenu(@PathVariable UUID menu_id, @RequestBody MenuUpdateDto requestDto) {
        Menu entity = menuService.update(menu_id, requestDto);
        try {
            return new ResponseEntity(MenuUpdateDto.fromEntity(entity), HttpStatus.ACCEPTED);
        }
        catch (Exception e) {
            return new ResponseEntity(ErrorResponseDto.fromEntity("FORBIDDEN", "상품 수정에 오류가 발생하였습니다."), HttpStatus.BAD_REQUEST);
        }
    }

    // 카테고리별 목록조회

    // 메뉴 삭제
    @ResponseBody
    @DeleteMapping("/{menu_id}")
    public ResponseEntity<MenuResponseDto> deleteMenu(@PathVariable UUID menu_id) {
        menuService.delete(menu_id);
        return ResponseEntity
                .ok()
                .build();
    }

}
