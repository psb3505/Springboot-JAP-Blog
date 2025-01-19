package com.springboot.myblog.model;

import lombok.Data;

@Data
public class KakaoProfile {
    public Long id;
    public String connected_at;
    public Properties properties;
    public KakaoAccount kakao_account;

    public static class Properties { // 🔥 static 추가
        public String nickname;
        public String profile_image;
        public String thumbnail_image;
    }

    public static class KakaoAccount { // 🔥 static 추가
        public Boolean profile_nickname_needs_agreement;
        public Boolean profile_image_needs_agreement;
        public Profile profile;
    }

    public static class Profile { // 🔥 static 추가
        public String nickname;
        public String thumbnail_image_url;
        public String profile_image_url;
        public Boolean is_default_image;
        public Boolean is_default_nickname;
    }
}
