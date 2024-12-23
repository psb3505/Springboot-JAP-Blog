package com.springboot.myblog.test;

import org.springframework.web.bind.annotation.RestController;

import com.springboot.myblog.model.RoleType;
import com.springboot.myblog.model.User;
import com.springboot.myblog.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
public class DummyControllerTest {

    // DummyControllerTest이게 메모리에 올려질 때 Autowired 이것도 같이 올려진다.
    // @Autowired는 UserRepository 타입으로 spring이 관리하고 있는 객체가 있으면 userRepository에 넣어달라는 의미이다.
    // 이미 UserRepository에서 컴포넌트 스캔을 통해 메모리에 올려져 있기 때문에 그냥 가져다가 사용만 하면 된다.
    // 이게 의존성 주입(DI)이다.
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/dummy/join")
    public String join(User user) {    // 매개변수에 String username, String password 이런식으로 작성하면 key = value 형식으로 받는다. (약속된 규칙)
        System.out.println("Id : " + user.getId());
        System.out.println("username : " + user.getUsername());
        System.out.println("password : " + user.getPassword());
        System.out.println("email : " + user.getEmail());
        System.out.println("Role : " + user.getRole());
        System.out.println("CreateDate : " + user.getCreateDate());

        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}
