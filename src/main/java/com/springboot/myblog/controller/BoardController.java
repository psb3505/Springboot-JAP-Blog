package com.springboot.myblog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.springboot.myblog.config.auth.PrincipalDetail;

@Controller
public class BoardController {
    
    // http://localhost:8000/blog/
    // http://localhost:8000/blog
    // 둘 다 되도록 매핑함
    @GetMapping({"","/"})
    public String index(@AuthenticationPrincipal PrincipalDetail principal) {   // 컨트롤러에서 세션을 어떻게 찾는지?
        // /WEB-INF/views/index.jsp
        System.out.println("로그인 사용자 아이디 : " + principal.getUsername());
        return "index";
    }
}
