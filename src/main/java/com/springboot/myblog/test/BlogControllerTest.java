package com.springboot.myblog.test;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


// 스프링이 com.springboot.myblog 패키지 이하를 스캔해서 모든 파일을 메모리에 new 하는 것은 아니다.
// 특정 어노테이션이 붙어있는 클래스 파일들을 new 해서(IoC) 스프링 컨테이너에 관리한다.
@RestController
public class BlogControllerTest {

    
    @GetMapping("/test/hello")
    public String hello() {
        return "<h1>hello spring boot</h1>";
    }
}
