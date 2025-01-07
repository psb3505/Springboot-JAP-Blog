package com.springboot.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.myblog.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    
}
