package com.springboot.myblog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class TempControllerTest {
    
    // http://localhost:8000/blog/temp/home
    @GetMapping("/temp/home")
    public String tempHome() {
        System.out.println("tempHome()");
        // 파일리턴 기본경로 : src/main/resources/static
        // 리턴명 : /home.html
        // 풀경로 : src/main/resources/static/home.html
        // static 폴더 아래는 정적파일만 놓을 수 있다.
        // html, png, jpeg, css, javascript 등 가능
        // jsp, java 파일들은 안된다.
        return "/home.html";
    }
    
    @GetMapping("/temp/jsp")
    public String tempJsp() {
        // prefix : /WEB-INF/views/
        // suffix : .jsp
        // 풀네임 : /WEB-INF/views/test.jsp

        return "test";
    }
    
}
