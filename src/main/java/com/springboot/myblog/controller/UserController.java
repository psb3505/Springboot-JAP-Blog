package com.springboot.myblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.myblog.config.auth.PrincipalDetail;
import com.springboot.myblog.model.KakaoProfile;
import com.springboot.myblog.model.OAuthToken;
import com.springboot.myblog.model.User;
import com.springboot.myblog.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;



// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/** 허용
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${cos.key}")
    private String cosKey;

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
    public String kakaoCallback(String code, HttpServletRequest request) {   // Data를 리턴해주는 컨트롤러 함수
        
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

        // Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;

        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("카카오 엑세스 토큰 : " + oauthToken.getAccess_token());

        RestTemplate rt2 = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token() );
        headers2.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

        //Http 요청하기 - POST방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response2 = rt2.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.POST,
            kakaoProfileRequest,
            String.class
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        User kakaoUser = User.builder()
            .username(kakaoProfile.getProperties().nickname + "_" + kakaoProfile.getId())
            .password(cosKey)
            .oauth("kakao")
            .build();

        // 가입자 혹은 비가입자 체크해서 처리
        User originUser =  userService.회원찾기(kakaoUser.getUsername());

        if(originUser.getUsername() == null) {
            userService.회원가입(kakaoUser);
        }

        // 로그인 처리
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 세션에 인증 객체 저장 (중요)
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        return "redirect:/";
    }

    // 로그인 잘 됬는지 테스트
    @GetMapping("/auth/principal")
    @ResponseBody
    public String testPrincipal(@AuthenticationPrincipal PrincipalDetail principal) {
        if (principal == null) {
            System.out.println("❌ 세션이 없습니다. (로그인되지 않음)");
            return "❌ 세션이 없습니다. 로그인되지 않음.";
        }

        System.out.println("✅ 세션 유지 확인: " + principal.getUsername());
        System.out.println("✅ 로그인된 사용자 정보: " + principal.getUser());

        return "✅ 현재 로그인된 사용자: " + principal.getUsername();
    }

    @GetMapping("user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }
}
