package com.springboot.myblog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.myblog.dto.ResponseDto;
import com.springboot.myblog.model.RoleType;
import com.springboot.myblog.model.User;
import com.springboot.myblog.service.UserService;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;
    
    @PostMapping("/api/user")
    public ResponseDto save(@RequestBody User user) {
        System.out.println("UserApiController : save 호출됨");
        
        user.setRole(RoleType.USER);
        userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);        
    }
}