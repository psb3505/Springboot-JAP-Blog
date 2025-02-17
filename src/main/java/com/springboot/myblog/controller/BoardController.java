package com.springboot.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.springboot.myblog.config.auth.PrincipalDetail;
import com.springboot.myblog.service.BoardService;



@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;
    
    // http://localhost:8000/blog/
    // http://localhost:8000/blog
    // 둘 다 되도록 매핑함
    @GetMapping({"","/"})
    public String index(Model model, @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {   
        // /WEB-INF/views/index.jsp
        model.addAttribute("boards", boardService.글목록(pageable));
        return "index";
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable int id, Model model) {
        model.addAttribute("board", boardService.글상세보기(id));
        return "board/detail";
    }
    
    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model) {
        model.addAttribute("board", boardService.글상세보기(id));
        return "board/updateForm";
    }

    // USER 권한이 필요
    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }
}
