package com.springboot.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.myblog.model.User;

// 해당 JpaRepository는 User 테이블을 관리하는 Repository이며, primary key는 integer 값이라는 것을 나타 냄.
// DAO
// 자동으로 bean 등록이 된다.
// @Repository 생략 가능하다.
public interface UserRepository extends JpaRepository<User, Integer> {
    
}
