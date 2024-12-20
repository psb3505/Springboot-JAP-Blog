package com.springboot.myblog.model;

import java.security.Timestamp;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// ORM -> Java(다른언어) Object -> 테이블로 매핑해주는 기술
// 즉, Object만 만들면 테이블로 매핑해서 수정된 것도 반영이 바로 된다.
// User 클래스가 MySQL에 테이블이 자동으로 생성이 된다.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다. (mysql일 경우 auto_increament를 사용하게 된다.)
    private int id; // 시퀀스, auto_increament

    @Column(nullable = false, length = 30)
    private String username;    // 아이디
    
    @Column(nullable = false, length = 100) // 길이를 100으로 넉넉하게 준 이유 : 123456 => 해쉬 (비밀번호 암호화)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @ColumnDefault("'user'")    // 'user' 로 문자라는 걸 알려줘야 함
    private String role;    // Enum을 쓰는게 좋다. // admin, user, manager 등 도메인 설정을 할 수 있다. (도메인 : 어떤 범위가 정해진 것)

    @CreationTimestamp  // 시간이 자동 입력
    private Timestamp createDate;
}
