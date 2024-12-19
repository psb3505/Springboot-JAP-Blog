package com.springboot.myblog.test;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



// 사용자가 요청 -> 응답(html 파일)
// @Controller

// 사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {

    private static final String TAG = "HttpControllerTest";

    @GetMapping("/http/lombok")
    public String lombokTest() {
        Member m = Member.builder().username("ssar").password("1234").email("ssar@naver.com").build();
        System.out.println(TAG + "getter : " + m.getUsername());
        m.setUsername("sb");
        System.out.println(TAG + "setter : " + m.getUsername());
        return "lombok test 완료";
    }
    
    // 인터넷 브라우저 요청은 무조건 get 요청밖에 할 수 없다.
    // get은 데이터를 보낼 때는 쿼리 스트링 방식 밖에 없다.
    // 쿼리 스트링에 있는 값들을 Member 클래스의 변수에 매핑해주는 건 MessageConverter (스프링부트) 가 해준다.
    @GetMapping("/http/get")
    public String getTest(Member m) {
        return "get 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword();
    }
    
    // post 요청은 @RequestBody 어노테이션을 통해서 데이터를 파싱해서 받을 수 있다.
    // text/plain -> text 그 자체 값을 가져온다. 요청을 이렇게 하고 json 처럼 하나하나 매핑하면 오류가 난다.
    // application/json -> json 형태의 데이터를 파싱해서 Member 클래스에 있는 변수에 매핑해준다.
    // 매핑을 MessageConverter (스프링부트) 가 해준다.
    @PostMapping("/http/post")
    public String postTest(@RequestBody Member m) {
        return "post 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
    }

    // put 요청은 @RequestBody 어노테이션을 통해서 데이터를 파싱해서 받을 수 있다.
    // 매핑을 MessageConverter (스프링부트) 가 해준다.
    @PutMapping("/http/put")
    public String putTest(@RequestBody Member m) {
        return "put 요청" + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
    }

    // delete 요청은 @RequestBody 어노테이션을 통해서 데이터를 파싱해서 받을 수 있다.
    // 매핑을 MessageConverter (스프링부트) 가 해준다.
    @DeleteMapping("/http/delete")
    public String deleteTest(@RequestBody Member m) {
        return "delete 요청" + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
    }
}
