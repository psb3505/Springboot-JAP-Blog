package com.springboot.myblog.test;

import org.springframework.web.bind.annotation.RestController;

import com.springboot.myblog.model.RoleType;
import com.springboot.myblog.model.User;
import com.springboot.myblog.repository.UserRepository;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;







@RestController
public class DummyControllerTest {

    // DummyControllerTest이게 메모리에 올려질 때 Autowired 이것도 같이 올려진다.
    // @Autowired는 UserRepository 타입으로 spring이 관리하고 있는 객체가 있으면 userRepository에 넣어달라는 의미이다.
    // 이미 UserRepository에서 컴포넌트 스캔을 통해 메모리에 올려져 있기 때문에 그냥 가져다가 사용만 하면 된다.
    // 이게 의존성 주입(DI)이다.
    @Autowired
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id) {
        if (!userRepository.existsById(id)) {
            return("삭제에 실패하였습니다. 해당 id는 DB에 없습니다.");
        }
        userRepository.deleteById(id);
        return "삭제되었습니다. id : " + id;
    }

    // /dummy/user/{id} URL이 같은 Mapping이 있어도 GET, PUT, DELETE를 Spring이 자동으로 구별해서 매핑해준다.
    // save 함수는 id를 전달하지 않으면 insert를 해주고
    // save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
    // save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해준다.
    @Transactional
    @PutMapping("/dummy/user/{id}") 
    public User updateUser(@PathVariable int id, @RequestBody User requestUser) {   // json 데이터를 요청(@RequestBody로 데이터를 받는다.) => Java Object(MessageConverter의 Jackson 라이브러리가 변환해서 받아준다.)
        System.out.println("id : " + id);
        System.out.println("password : " + requestUser.getPassword());
        System.out.println("email : " + requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("수정에 실패하였습니다.");
        });

        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

        // userRepository.save(user);

        return user;
    }
    

    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<User> pagingUser = userRepository.findAll(pageable);

        List<User> users = pagingUser.getContent();
        return users;
    }
    

    // {id} 주소로 파라메터를 전달 받을 수 있음
    // http://localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {
        // user/4를 찾으면 내가 데이터베이스에서 못 찾아오게 되면 user가 null이 될 거다.
        // 그럼 return null이 리턴이 되기 때문에 프로그램에 문제가 생긴다.
        // Optional로 User 객체를 감싸 가져와서 null 인지 아닌지 판단해서 return 해야 함
        // User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
        //     @Override
        //     public IllegalArgumentException get() {
        //         // TODO Auto-generated method stub
        //         return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
        //     }
        // });

        // 람다식
        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
        });

        // 요청 : 웹브라우저
        // user 객체 = 자바 오브젝트
        // 변환 (웹브라우저가 이해할 수 있는 데이터) -> json(Gson 라이브러리)
        // 스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
        // 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jacson 라이브러리를 호출해서
        // user 오브젝트를 json으로 변환해서 브라우저에게 던져준다.
        return user;
    }
    

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
