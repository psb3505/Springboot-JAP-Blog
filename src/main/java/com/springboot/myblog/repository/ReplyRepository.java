package com.springboot.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.springboot.myblog.model.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Integer>  {
    
    // @Modifying
    // @Query(value = "INSERT INTO reply(userId, boardId, content, createDate) VALUES(?1, ?2, ?3, now())", nativeQuery = true)
    // int nativeInsertReply(int userId, int boardId, String content);

    @Modifying
    @Query(value = "INSERT INTO reply(userId, boardId, content, createDate) VALUES(:userId, :boardId, :content, now())", nativeQuery = true)
    int nativeInsertReply(int userId, int boardId, String content);
}
