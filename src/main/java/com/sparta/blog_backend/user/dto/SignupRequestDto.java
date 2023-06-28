package com.sparta.blog_backend.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

// 로그인 정보 조건 걸기 => 회원가입 요청에서 필요한 정보를 담는 DTO
/*username : 자릿수 4 ~ 10,  알파벳 소문자(a~z), 숫자(0~9)로 구성
password:  자릿수 8 - 15, 알파벳 대소문자(a~z, A~Z), 숫자(0~9) 구성*/
@Getter
@Setter
public class SignupRequestDto {
    @NotBlank
    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-z0-9]]")
    private String username;
    @NotBlank
    @Size(min = 8, max = 15)
    @Pattern(regexp = "^[A-Za-z0-9]]")
    private String password;
    private String email;
    private boolean admin = false;
    private String adminToken = "";

}