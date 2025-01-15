package com.springboot.myblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.myblog.config.auth.PrincipalDetail;
import com.springboot.myblog.model.RoleType;
import com.springboot.myblog.model.User;
import com.springboot.myblog.repository.UserRepository;

import jakarta.transaction.Transactional;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
// 서비스는 
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @org.springframework.transaction.annotation.Transactional
    public void 회원가입(User user) {
        String rawPassword = user.getPassword();    // 1234 원문
        String encPassword = encoder.encode(rawPassword);   // 해쉬 암호화
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);

        // try-catch로 오류처리를 하지 않은 이유는
        // GlobalExceptionHandler.java 파일에서 전역적으로 오류를 처리하기 때문에 따로 작성하지 않음
        userRepository.save(user);
    }

    @org.springframework.transaction.annotation.Transactional
    public void 회원수정(User user, PrincipalDetail principalDetail) {
        // 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
        // select를 해서 User 오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기 위해서
        // 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려준다.
        User persistance = userRepository.findById(user.getId()).orElseThrow(() ->{
            return new IllegalArgumentException("회원 찾기 실패");
        }); 
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        persistance.setPassword(encPassword);
        persistance.setEmail(user.getEmail());

        // 세션 변경
        principalDetail.setUser(user);
    }
    
}
