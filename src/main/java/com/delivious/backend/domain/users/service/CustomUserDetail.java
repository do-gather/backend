//package com.delivious.backend.domain.users.service;
//
////신선영
////<<<<<<< feature/login-29:src/main/java/com/delivious/backend/domain/users/service/CustomUserDetail.java
//import com.delivious.backend.domain.users.entity.UserEntity;
//
////>>>>>>> feat: fix user domain:src/main/java/com/delivious/backend/domain/users/service/CustomUserDetailService.java
//import com.delivious.backend.domain.users.repository.UserRepository;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component("userDetailsService")
//public class CustomUserDetail implements UserDetailsService {
//    private final UserRepository userRepository;
//
//    public CustomUserDetail(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(final String username) {
//        return userRepository.findOneWithAuthoritiesByUsername(username)
//                .map(user -> createUser(username, user))
//                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
//    }
//
//    private org.springframework.security.core.userdetails.User createUser(String username, UserEntity user) {
//        if (!user.isActivated()) {
//            throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
//        }
//        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
//                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
//                .collect(Collectors.toList());
//        return new org.springframework.security.core.userdetails.User(user.getName(),
//                user.getPassword(),
//                grantedAuthorities);
//    }
//}
