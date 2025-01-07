package com.springboot.myblog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.springboot.myblog.model.User;

// Ctrl + . 를 통해 오버라이딩 메소드 추가
// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션저장소에 저장을 해준다.
public class PrincipalDetail implements UserDetails {
    private User user;  // 객체를 품고있는 걸 콤포지션이라고 한다.

    public PrincipalDetail(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료되지 않았는지 리턴한다. (true : 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있지 않았는지 리턴한다. (true : 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지 리턴한다. (true : 만료안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화(사용가능) 인지 리턴한다. (true : 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 계정이 갖고있는 권한 목록을 리턴한다. (권한이 여러개 있을 수 있어서 루프를 돌아야 하는데 우리는 한 개만)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        Collection<GrantedAuthority> collectors = new ArrayList<>();    // ArrayList는 상속을 보면 상속 끝 부분에 Collection을 상속하고 있어서 ArrayList도 Collection을 상속받고 있다고 보면 된다.
        // collectors.add(new GrantedAuthority() {
            
        //     @Override
        //     public String getAuthority() {
        //         return "ROLE_" + user.getRole();    // ROLE_USER (스프링에서 정해진 규칙이다.)
        //     }
        // });

        // 람다식
        collectors.add(() -> {return "ROLE_" + user.getRole();});

        return collectors;
    }
    
}
