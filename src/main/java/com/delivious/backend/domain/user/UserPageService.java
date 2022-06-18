package com.delivious.backend.domain.user;


import com.delivious.backend.domain.user.User;
import com.delivious.backend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserPageService {

    private final UserRepository userRepository;

    // 유저 id로 User 찾기
    public User findUser(Integer id) {
        return userRepository.findById(id).get();
    }

    // 회원 정보 수정
    public void userModify(User user) {
        User update = userRepository.findById(user.getId());
        update.setUsername(user.getUsername());
        update.setEmail(user.getEmail());
        update.setAddress(user.getAddress());
        update.setPhone(user.getPhone());
        userRepository.save(update);
    }

    public void chargePoint(int id,int amount){
        User user = userRepository.findById(id);
        user.setCoin(user.getCoin() + amount);

        userRepository.save(user);
    }

}