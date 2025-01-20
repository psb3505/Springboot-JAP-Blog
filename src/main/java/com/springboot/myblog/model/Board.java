package com.springboot.myblog.model;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increament
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob    // 대용량 데이터 사용할 때 사용
    private String content; // 섬머노트 라이브러리 <html> 태그가 섞여서 디자인이 됨.

    private int count; // 조회수

    // Board = Many, User = one
    // 한 명의 유저는 여러개의 게시글을 쓸 수 있다.
    // 한 개의 게시글은 한 명의 유저만 쓸 수 있다.
    @ManyToOne(fetch = FetchType.EAGER)  
    @JoinColumn(name = "userId")
    private User user;    // DB는 오브젝트를 저장할 수 없다. FK를 사용해야 하는데, 자바는 오브젝트를 저장할 수 있다.

    // mappedBy는 연관관계의 주인이 아니다.
    // 즉, 나는 FK가 아니다. 그래서 DB에 칼럼을 만들지 말라는 뜻이다.
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"board"})
    @OrderBy("id desc")
    private List<Reply> replys;

    @CreationTimestamp
    private LocalDateTime createDate;

}