package com.delivious.backend.domain.config.auth;


import com.delivious.backend.domain.cart.CartService;
import com.delivious.backend.domain.order.OrderService;
import com.delivious.backend.domain.sale.SaleService;
import com.delivious.backend.domain.user.User;
import com.delivious.backend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CartService cartService;
    private final OrderService orderService;
    private final SaleService saleService;

    @Transactional
    public User signup(User user) {

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole(user.getRole());

        User userEntity = userRepository.save(user);

        if (Objects.equals(userEntity.getRole(), "ROLE_SELLER")) {
            saleService.createSale(user);
        } else if (Objects.equals(user.getRole(), "ROLE_USER")){
            cartService.createCart(user);
            orderService.createOrder(user);
        }

        return userEntity;
    }
}