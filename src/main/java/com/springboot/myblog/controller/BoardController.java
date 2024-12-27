package com.springboot.myblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
    
    // http://localhost:8000/blog/
    // http://localhost:8000/blog
    // 둘 다 되도록 매핑함
    @GetMapping({"","/"})
    public String index() {
        // /WEB-INF/views/index.jsp
        return "index";
    }
}
