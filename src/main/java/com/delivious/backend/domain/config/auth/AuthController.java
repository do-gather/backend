package com.delivious.backend.domain.config.auth;

import com.delivious.backend.domain.user.User;
import com.delivious.backend.domain.config.auth.AuthService;
import com.delivious.backend.domain.cart.CartService;
import com.delivious.backend.domain.order.OrderService;
import com.delivious.backend.domain.sale.SaleService;
import com.delivious.backend.domain.user.SignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    @GetMapping("/signin")
    public String SigninForm() {
        return "signin";
    }

    @GetMapping("/signup")
    public String SignupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(SignupDto signupDto) {
        // User에 signupDto 넣음
        User user = signupDto.toEntity();

        User userEntity = authService.signup(user);
        System.out.println(userEntity);

        return "signin";
    }
}
