package com.springboot.myblog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.myblog.model.Board;
import com.springboot.myblog.repository.BoardRepository;

@RestController
public class ReplyControllerTest {

    @Autowired
    private BoardRepository boardRepository;
    
    @GetMapping("/test/board/{id}")
    public Board getBoard(@PathVariable int id){
        System.out.println("id : " + id);
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 게시글이 존재하지 않습니다. ID: " + id));
    }
}
