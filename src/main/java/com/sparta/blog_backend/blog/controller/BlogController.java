package com.sparta.blog_backend.blog.controller;

import com.sparta.blog_backend.blog.dto.BlogListResponseDto;
import com.sparta.blog_backend.blog.dto.BlogRequestDto;
import com.sparta.blog_backend.blog.dto.BlogResponseDto;
import com.sparta.blog_backend.blog.service.BlogService;
import com.sparta.blog_backend.user.dto.ApiResponseDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sparta.blog_backend.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.RejectedExecutionException;

@RestController // RESTful API 요청을 받을 수 있는 Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;


    // 1. 전체 목록 조회
    @GetMapping("/posts")
    public ResponseEntity<BlogListResponseDto> getBlogList() {
        BlogListResponseDto result = blogService.getBlogList();

        return ResponseEntity.ok().body(result);
    }

    // 2. 게시글 작성
    @PostMapping("/posts")
    public ResponseEntity<BlogResponseDto> createBlog(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody BlogRequestDto blogRequestDto) {
        BlogResponseDto result = blogService.createBlog(blogRequestDto, userDetails.getUser());

        return ResponseEntity.status(201).body(result);
    }

    // 3. 선택 게시글 조회
    @GetMapping("/posts/{id}") // {id} Path, 경로 변수 Id 값을 받아옴
    public ResponseEntity<BlogResponseDto> getBlog(@PathVariable Long id) {
        BlogResponseDto result = blogService.getBlog(id);

        return ResponseEntity.ok().body(result);
    }

    // 4. 게시글 수정
    @PutMapping("/posts/{id}")
    public ResponseEntity<ApiResponseDto> updateBlog(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        try {
            BlogResponseDto result = blogService.updateBlog(id, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(result);
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 수정 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 5. 게시글 삭제
    @DeleteMapping("/posts/{id}") // body 에 포함되어 있는 값을 매개변수로 가져옴
    public ResponseEntity<ApiResponseDto> deleteBlog(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        blogService.deleteBlog(id, userDetails.getUser());
        try {
            blogService.deleteBlog(id, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("게시글 삭제 성공", HttpStatus.BAD_REQUEST.value()));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 삭제 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 좋아요 누르기
    @PostMapping("/posts/{id}/like")
    public ResponseEntity<ApiResponseDto> upLike(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails ){
        blogService.upLike(id,userDetails.getUser().getId());

        return ResponseEntity.ok().body(new ApiResponseDto("게시글에 좋아요를 눌렀습니다!",HttpStatus.OK.value()));
    }

    // 종아요 조회
    @GetMapping("/posts/{id}/like")
    public boolean isLiked(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return blogService.isLiked(id, userDetails.getUser().getId());
    }
}
