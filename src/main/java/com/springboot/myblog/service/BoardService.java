package com.springboot.myblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.myblog.dto.ReplySaveRequestDto;
import com.springboot.myblog.model.Board;
import com.springboot.myblog.model.Reply;
import com.springboot.myblog.model.RoleType;
import com.springboot.myblog.model.User;
import com.springboot.myblog.repository.BoardRepository;
import com.springboot.myblog.repository.ReplyRepository;
import com.springboot.myblog.repository.UserRepository;

import jakarta.transaction.Transactional;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다.
// 서비스는 
@Service
public class BoardService {
    
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UserRepository userRepository;

    @org.springframework.transaction.annotation.Transactional
    public void 글쓰기(Board board, User user) {
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    @org.springframework.transaction.annotation.Transactional
    public Page<Board> 글목록(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @org.springframework.transaction.annotation.Transactional
    public Board 글상세보기(int id) {
        return boardRepository.findById(id)
            .orElseThrow(() -> {
                return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
            });
    }

    @Transactional
    public void 글삭제하기(int id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void 글수정하기(int id, Board requestBoard) {
        Board board = boardRepository.findById(id)
            .orElseThrow(() -> {
                return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
            }); // 영속화 완료
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        // 해당 함수로 종료시(Service가 종료될 때) 트랜잭션이 종료된다. 이때 더티체킹 - 자동 업데이트가 됨. DB flush
    }
    
    @org.springframework.transaction.annotation.Transactional
    public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
        replyRepository.nativeInsertReply(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
    }

    @org.springframework.transaction.annotation.Transactional
    public void 댓글삭제(int replyId) {
        replyRepository.deleteById(replyId);
    }
}
