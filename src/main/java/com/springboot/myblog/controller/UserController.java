package com.springboot.myblog.controller;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;



// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/** 허용
@Controller
public class UserController {

    @Value("${kakao.client-id}")    // 환경 변수에서 값 불러오기 
    private String client_id;

    @Value("${kakao.redirect-uri}")    // 환경 변수에서 값 불러오기 
    private String redirect_uri;
    
    @GetMapping("/auth/joinForm")
    public String joinForm() {

        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm(Model model) {
        // JSP에서 사용 가능하도록 모델에 값 전달
        model.addAttribute("kakaoClientId", client_id);
        model.addAttribute("kakaoRedirectUri", redirect_uri);

        return "user/loginForm";
    }

    @GetMapping("auth/kakao/callback")
    public @ResponseBody String kakaoCallback(String code) {   // Data를 리턴해주는 컨트롤러 함수
        
        // POST방식으로 key=value 데이터를 요청 (카카오쪽으로)
        // Retrofit2
        // OkHttp
        // RestTemplate
        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client_id);
        params.add("redirect_uri", redirect_uri);
        params.add("code", code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        //Http 요청하기 - POST방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response = rt.exchange(
            "https://kauth.kakao.com/oauth/token",
            HttpMethod.POST,
            kakaoTokenRequest,
            String.class
        );

        return "카카오 토큰 요청청 완료 : 토큰 요청에 대한 응답 : " + response;
    }

    @GetMapping("user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }
}
