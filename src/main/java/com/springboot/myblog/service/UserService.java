package com.springboot.myblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.myblog.model.User;
import com.springboot.myblog.repository.UserRepository;

import jakarta.transaction.Transactional;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
// 서비스는 
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @org.springframework.transaction.annotation.Transactional
    public void 회원가입(User user) {
        // try-catch로 오류처리를 하지 않은 이유는
        // GlobalExceptionHandler.java 파일에서 전역적으로 오류를 처리하기 때문에 따로 작성하지 않음
        userRepository.save(user);
    }
    
    @org.springframework.transaction.annotation.Transactional(readOnly = true) // Select 할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성 유지)
    public User 로그인(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
}
