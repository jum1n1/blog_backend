package com.sparta.blog_backend.user.dto;

import lombok.Getter;
import lombok.Setter;

// 로그인할 때 받은 정보
@Getter
@Setter
public class LoginRequestDto {
    private String username;
    private String password;
}
