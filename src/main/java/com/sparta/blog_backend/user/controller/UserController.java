package com.sparta.blog_backend.user.controller;

import com.sparta.blog_backend.user.dto.ApiResponseDto;
import com.sparta.blog_backend.user.dto.AuthRequestDto;
import com.sparta.blog_backend.user.jwt.JwtUtil;
import com.sparta.blog_backend.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    // 회원 가입
    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody AuthRequestDto requestDto){

        try{
            userService.signup(requestDto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new ApiResponseDto("중복된 username입니다", HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.status(201).body(new ApiResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody AuthRequestDto loginRequestDto, HttpServletResponse response){
        try{
            userService.login(loginRequestDto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new ApiResponseDto("회원을 찾을 수 없습니다",HttpStatus.BAD_REQUEST.value()));
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(loginRequestDto.getUsername(), loginRequestDto.getRole()));

        return ResponseEntity.ok().body(new ApiResponseDto("로그인 성공", HttpStatus.CREATED.value()));
    }



}
