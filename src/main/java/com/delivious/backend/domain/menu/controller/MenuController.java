package com.delivious.backend.domain.menu.controller;
import com.delivious.backend.domain.menu.repository.MenuRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuRepository menuRepository;

    public MenuController(MenuRepository menuRepository) {this.menuRepository = menuRepository;}

    @GetMapping
    public  ResponseEntity getAllMenus() {

        return ResponseEntity.ok(this.menuRepository.findAll());
    }

}

//메뉴2