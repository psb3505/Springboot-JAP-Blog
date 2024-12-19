package com.springboot.myblog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
// Getter
// Setter
@Data

// 모든 필드를 다 쓰는 생성자 생성
// @AllArgsConstructor

// 빈 생성자 생성
@NoArgsConstructor

// final이 붙은 모든 필드를 다 쓰는 생성자 생성
// @RequiredArgsConstructor
public class Member {
    private int id;
    private String username;
    private String password;
    private String email;

    @Builder
    public Member(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
