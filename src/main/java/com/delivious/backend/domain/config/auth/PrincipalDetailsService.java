package com.delivious.backend.domain.config.auth;



import com.delivious.backend.domain.user.User;
import com.delivious.backend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // UserDetailsService 는 DB에서 회원 정보를 가져오는 인터페이스
    // -> loadUserByUsername() 메소드를 통해 회원정보 조회 -> UserDetails 인터페이스 반환
    // UserDetails 는 회원 정보를 담는 인터페이스
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userEntity = userRepository.findByUsername(username);

        if(userEntity == null) {
            return null;
        } else {
            return new PrincipalDetails(userEntity);
        }
    }
}