package com.springboot.myblog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.myblog.model.User;

// 해당 JpaRepository는 User 테이블을 관리하는 Repository이며, primary key는 integer 값이라는 것을 나타 냄.
// DAO
// 자동으로 bean 등록이 된다.
// @Repository 생략 가능하다.
public interface UserRepository extends JpaRepository<User, Integer> {
    // SELECT * FROM user WHERE username = 1?;
    Optional<User> findByUsername(String username);
}


//JPA Naming 쿼리
// SELECT * FROM user WHERE username = ? AND password = ?;
//User findByUsernameAndPassword(String username, String password);

// 또 다른 select 방법 (native 쿼리)
// @Query(value = "SELECT * FROM user WHERE username = ? AND password = ?", nativeQuery = true)
// User login(String username, String password);