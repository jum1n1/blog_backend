package com.sparta.blog_backend.blog.controller;

import com.sparta.blog_backend.blog.dto.BlogRequestDto;
import com.sparta.blog_backend.blog.dto.BlogResponseDto;
import com.sparta.blog_backend.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 외부에 요청을 수신하는 controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;


    // 1. 전체 목록 조회
    @GetMapping("/posts")
    public List<BlogResponseDto> getBlogList() {
        return blogService.getBlogList();
    }

    // 2. 게시글 작성
    @PostMapping("/posts")
    public BlogResponseDto createBlog (@RequestBody BlogRequestDto blogRequestDto) {
        return blogService.createBlog(blogRequestDto);
    }

    // 3. 선택 게시글 조회
    @GetMapping("/posts/{id}") // {id} Path, 경로 변수 Id 값을 받아옴
    public BlogResponseDto getBlog(@PathVariable Long id) {
        return blogService.getBlog(id);
    }

    // 4. 게시글 수정
    @PutMapping("/posts/{id}")
    public BlogResponseDto updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto blogRequestDto){
        return blogService.updateBlog(id, blogRequestDto);
    }

    // 5. 게시글 삭제
    @DeleteMapping("/posts/{id}") // body 에 포함되어 있는 값을 매개변수로 가져옴
    public BlogResponseDto deleteBlog(@PathVariable Long id, @RequestBody BlogRequestDto blogRequestDto){
        blogService.deleteBlog(id, blogRequestDto.getPassword());
        return new BlogResponseDto(true);
    }
}
